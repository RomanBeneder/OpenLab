/*
 * OpenLabSigGenFirmware.c
 *
 * Created: 27.10.2016 23:40:06
 * Author : Jakob
 */ 

#include <avr/io.h>				// io ports
#include <avr/wdt.h>			// watchdog
#include <avr/interrupt.h>		// global interrupts
#include <avr/power.h>			// clock prescaler

#include "LUFA/Drivers/USB/USB.h"
#include "USBDescriptors.h"
#include "protocol.h"
#include "crc.h"
#include "spi.h"
#include "util.h"
#include "LUFA/Common/Endianness.h"

// LUFA USB Eventhandler
void EVENT_USB_Device_Connect();
void EVENT_USB_Device_Disconnect();
void EVENT_USB_Device_ConfigurationChanged();
void EVENT_USB_Device_ControlRequest();

void serial_handler();
void send_reply(reply_codes reply);
void send_version(reply_codes reply, uint8_t major, uint8_t minor, uint8_t build, uint8_t reserved);
float map(float a_value, float a_begin, float a_end, float b_begin, float b_end);

/** LUFA CDC Class driver interface configuration and state information. This structure is
 *  passed to all CDC Class driver functions, so that multiple instances of the same class
 *  within a device can be differentiated from one another.
 */
USB_ClassInfo_CDC_Device_t VirtualSerial_CDC_Interface =
{
	.Config =
	{
		.ControlInterfaceNumber   = INTERFACE_ID_CDC_CCI,
		.DataINEndpoint           =
		{
			.Address          = CDC_TX_EPADDR,
			.Size             = CDC_TXRX_EPSIZE,
			.Banks            = 1,
		},
		.DataOUTEndpoint =
		{
			.Address          = CDC_RX_EPADDR,
			.Size             = CDC_TXRX_EPSIZE,
			.Banks            = 1,
		},
		.NotificationEndpoint =
		{
			.Address          = CDC_NOTIFICATION_EPADDR,
			.Size             = CDC_NOTIFICATION_EPSIZE,
			.Banks            = 1,
		},
	},
};

static FILE USBSerialStream;
static char recvbuffer[32];

void io_init()
{
	DDRB	= 0b11110111;	// Data Direction Register, all pins as output, except PINB3 (MISO) 12.2.1
	PORTB	= 0b00001001;	// Activate the pullup for PINB3 (MISO) and set PINB0 (CS) high = inactive.
		
	DDRD	= 0b01111111;	// Data Direction Register, all pins as output, except PIND7 (HWB)
	PORTD	= 0b00000000;	// Outputs Low, no pullup as we have an external one
}


int main()
{
	MCUSR &= ~(1 << WDRF);
	wdt_disable();
	clock_prescale_set(clock_div_1);
	
	USB_Init();	
	io_init();
	spi_init();
	
	CDC_Device_CreateStream(&VirtualSerial_CDC_Interface, &USBSerialStream);
	GlobalInterruptEnable();

	// 0V offset
	set_dac(0,128);
	set_dac(1,128);
	
    while (1) 
    {
		serial_handler();
		
		CDC_Device_ReceiveByte(&VirtualSerial_CDC_Interface);
		CDC_Device_USBTask(&VirtualSerial_CDC_Interface);
		CDC_Device_Flush(&VirtualSerial_CDC_Interface);
		USB_USBTask();
    }
}


/**
 * Called periodically in the mainloop. Handles the serial communication
 **/
void serial_handler()
{
	int blocksRead = fread((uint8_t*)&recvbuffer, sizeof(ol_header), 1, &USBSerialStream);
	if(blocksRead == 1)
	{
		ol_header* recvhdr = (ol_header*)&recvbuffer;
		recvhdr->payload_size = be16_to_cpu(recvhdr->payload_size);
		recvhdr->checksum = be16_to_cpu(recvhdr->payload_size);
		
		switch(recvhdr->command)
		{
			case GET_SOFTWARE_VERSION:
			{
				send_version(SOFTWARE_VERSION, 0,1,0,0x4A);
			}
			break;
			case GET_HARDWARE_VERSION:
			{
				send_version(HARDWARE_VERSION, 0,2,0,0x4A);
			}
			break;
			case SET_OFFSET:
			{
				sb(PORTD,PIND2);
				if(recvhdr->payload_size != sizeof(ol_set_offset_req)-sizeof(uint16_t)) // payload size does not include the checksum!
				{
					sb(PORTD,PIND3);
					send_reply(NACK);
					return;
				}
				
				char* payload = (char*)&recvbuffer+sizeof(ol_header);
				blocksRead = fread(payload, recvhdr->payload_size, 1, &USBSerialStream);
				if(blocksRead == 1)
				{
					sb(PORTD,PIND4);
					send_reply(ACK);
					float offset = ((ol_set_offset_req*)payload)->offset;
					int level = map(offset, -5.0, 5.0, 0, 255);
					set_dac(recvhdr->channel_num,level);
				}
				else
				{
					sb(PORTD,PIND5);
					send_reply(NACK);	
				}
			}
			
			break;
			default:
			{
				send_reply(NACK);
				break;
			}
		}
	}
	
};


/**
 * Sends a simple reply code, no payload
 **/
void send_reply(reply_codes reply)
{
	ol_header hdr;
	hdr.command = reply;
	hdr.device_type = 2;
	hdr.payload_size = 0;
	hdr.checksum = cpu_to_be16(crc_custom((uint8_t*)&hdr, 5));
	
	fwrite(&hdr, sizeof(ol_header), 1, &USBSerialStream);
}


/**
 * Creates and sends a version response packet and sends it on the
 * USB serial stream
 **/
void send_version(reply_codes reply, uint8_t major, uint8_t minor, uint8_t build, uint8_t reserved)
{
	ol_header hdr;
	hdr.command = reply;
	hdr.device_type = 2;
	hdr.channel_num = 0;
	hdr.payload_size = cpu_to_be16(sizeof(ol_version_resp));
	hdr.checksum = cpu_to_be16(crc_custom((uint8_t*)&hdr, 5));
	
	ol_version_resp resp;
	resp.major = major;
	resp.minor = minor;
	resp.build = build;
	resp.reserved = reserved;
	resp.checksum = cpu_to_be16(crc_custom((uint8_t*)&resp, 4));
	
	fwrite(&hdr, sizeof(ol_header), 1, &USBSerialStream);
	fwrite(&resp, sizeof(ol_version_resp), 1, &USBSerialStream);
}


/**
 * Maps a value between a_begin and a_end to the range b_begin and b_end
 **/
float map(float a_value, float a_begin, float a_end, float b_begin, float b_end) {
	float a_range = a_end - a_begin;
	float b_range = b_end - b_begin;
	return b_begin + (b_range / a_range) * (a_value - a_begin);
}


void EVENT_USB_Device_Connect()
{
}


void EVENT_USB_Device_Disconnect()
{
}


void EVENT_USB_Device_ConfigurationChanged()
{
	/* Setup CDC Data Endpoints */
	CDC_Device_ConfigureEndpoints(&VirtualSerial_CDC_Interface);
}


void EVENT_USB_Device_ControlRequest()
{
	CDC_Device_ProcessControlRequest(&VirtualSerial_CDC_Interface);
}