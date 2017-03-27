//****************************************************************************
//
// usb_dev_cserial.c - Main routines for the USB CDC composite serial example.
//
// Copyright (c) 2010-2014 Texas Instruments Incorporated.  All rights reserved.
// Software License Agreement
//
// Texas Instruments (TI) is supplying this software for use solely and
// exclusively on TI's microcontroller products. The software is owned by
// TI and/or its suppliers, and is protected under applicable copyright
// laws. You may not combine this software with "viral" open-source
// software in order to form a larger program.
//
// THIS SOFTWARE IS PROVIDED "AS IS" AND WITH ALL FAULTS.
// NO WARRANTIES, WHETHER EXPRESS, IMPLIED OR STATUTORY, INCLUDING, BUT
// NOT LIMITED TO, IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR
// A PARTICULAR PURPOSE APPLY TO THIS SOFTWARE. TI SHALL NOT, UNDER ANY
// CIRCUMSTANCES, BE LIABLE FOR SPECIAL, INCIDENTAL, OR CONSEQUENTIAL
// DAMAGES, FOR ANY REASON WHATSOEVER.
//
// This is part of revision 2.1.0.12573 of the EK-TM4C1294XL Firmware Package.
//
//****************************************************************************

#include "TIVA_USB_lib.h"
#include "Protokoll.h"

//****************************************************************************
//
//! \addtogroup example_list
//! <h1>USB Composite Serial Device (usb_dev_cserial)</h1>
//!
//! This example application turns the evaluation kit into a multiple virtual
//! serial ports when connected to the USB host system.  The application
//! supports the USB Communication Device Class, Abstract Control Model to
//! redirect UART0 traffic to and from the USB host system.  For this example,
//! the evaluation kit will enumerate as a composite device with two virtual
//! serial ports. Including the physical UART0 connection with the ICDI, this
//! means that three independent virtual serial ports will be visible to the
//! USB host.
//!
//! The first virtual serial port will echo data to the physical UART0 port on
//! the device which is connected to the virtual serial port on the ICDI device
//! on this board. The physical UART0 will also echo onto the first virtual
//! serial device provided by the Stellaris controller.
//!
//! The second Stellaris virtual serial port will provide a console that can
//! echo data to both the ICDI virtual serial port and the first Stellaris
//! virtual serial port.  It will also allow turning on, off or toggling the
//! boards led status.  Typing a "?" and pressing return should echo a list of
//! commands to the terminal, since this board can show up as possibly three
//! individual virtual serial devices.
//!
//! Assuming you installed TivaWare in the default directory, a driver
//! information (INF) file for use with Windows XP, Windows Vista and Windows7
//! can be found in C:/TivaWare_C_Series-x.x/windows_drivers. For Windows 2000,
//! the required INF file is in C:/TivaWare_C_Series-x.x/windows_drivers/win2K.
//
//*****************************************************************************

//****************************************************************************
//
// Note:
//
// This example is intended to run on Stellaris evaluation kit hardware
// where the UARTs are wired solely for TX and RX, and do not have GPIOs
// connected to act as handshake signals.  As a result, this example mimics
// the case where communication is always possible.  It reports DSR, DCD
// and CTS as high to ensure that the USB host recognizes that data can be
// sent and merely ignores the host's requested DTR and RTS states.  "TODO"
// comments in the code indicate where code would be required to add support
// for real handshakes.
//
//****************************************************************************


//****************************************************************************
//
// The system tick rate expressed both as ticks per second and a millisecond
// period.
//
//****************************************************************************
#define SYSTICKS_PER_SECOND 100
#define SYSTICK_PERIOD_MS (1000 / SYSTICKS_PER_SECOND)

//*****************************************************************************
//
// Variable to remember our clock frequency
//
//*****************************************************************************
uint32_t g_ui32SysClock = 0;

//****************************************************************************
//
// Variables tracking transmit and receive counts.
//
//****************************************************************************
volatile uint32_t g_ui32UARTTxCount = 0;
volatile uint32_t g_ui32UARTRxCount = 0;

//****************************************************************************
//
// Default line coding settings for the redirected UART.
//
//****************************************************************************
#define DEFAULT_BIT_RATE        2000000
#define DEFAULT_UART_CONFIG     (UART_CONFIG_WLEN_8 | UART_CONFIG_PAR_NONE | \
		UART_CONFIG_STOP_ONE)

//****************************************************************************
//
// GPIO peripherals and pins muxed with the redirected UART.  These will
// depend upon the IC in use and the UART selected in UART0_BASE.  Be careful
// that these settings all agree with the hardware you are using.
//
//****************************************************************************
#define TX_GPIO_BASE            GPIO_PORTA_BASE
#define TX_GPIO_PERIPH          SYSCTL_PERIPH_GPIOA
#define TX_GPIO_PIN             GPIO_PIN_1

#define RX_GPIO_BASE            GPIO_PORTA_BASE
#define RX_GPIO_PERIPH          SYSCTL_PERIPH_GPIOA
#define RX_GPIO_PIN             GPIO_PIN_0
//****************************************************************************
//
// Defines the size of the buffer that holds the command line.
//
//****************************************************************************
#define CMD_BUF_SIZE            256
//****************************************************************************
//
// The buffer that holds the command line.
//
//****************************************************************************

static uint8_t g_pcCmdBuf[CMD_BUF_SIZE];
//****************************************************************************
//
// The memory allocated to hold the composite descriptor that is created by
// the call to USBDCompositeInit().
//
//****************************************************************************
uint8_t g_pucDescriptorData[DESCRIPTOR_DATA_SIZE];
//****************************************************************************
//
// Flags used to pass commands from interrupt context to the main loop.
//
//****************************************************************************
#define COMMAND_PACKET_RECEIVED 0x00000001
#define COMMAND_STATUS_UPDATE   0x00000002
#define COMMAND_RECEIVED        0x00000004

volatile uint32_t g_ui32Flags = 0;
char *g_pcStatus;

/***************************************************************************************************************+*************/
struct values struc;


uint32_t SizeSend = 0;
//****************************************************************************
//
// Global flag indicating that a USB configuration has been set.
//
//****************************************************************************
static volatile bool g_bUSBConfigured = false;
//****************************************************************************
//
// The error routine that is called if the driver library encounters an error.
//
//****************************************************************************
#ifdef DEBUG
void
__error__(char *pcFilename, uint32_t ui32Line)
{
	while(1)
	{
	}
}
#endif

//****************************************************************************
//
// Set the state of the RS232 RTS and DTR signals.  Handshaking is not
// supported so this request will be ignored.
//
//****************************************************************************
static void
SetControlLineState(unsigned short usState)
{
	return;
}

//****************************************************************************
//
// Set the communication parameters to use on the UART.
//
//****************************************************************************
static bool
SetLineCoding(tLineCoding *psLineCoding)
{
	return false;
}

//****************************************************************************
//
// Get the communication parameters in use on the UART.
//
//****************************************************************************
static void
GetLineCoding(tLineCoding *psLineCoding)
{
	psLineCoding->ui32Rate = DEFAULT_BIT_RATE;
	psLineCoding->ui8Databits = 8;
	psLineCoding->ui8Parity = USB_CDC_PARITY_NONE;
	psLineCoding->ui8Stop = USB_CDC_STOP_BITS_1;

	return;
}

//****************************************************************************
//
// This function sets or clears a break condition on the redirected UART RX
// line.  A break is started when the function is called with \e bSend set to
// \b true and persists until the function is called again with \e bSend set
// to \b false.
//
//****************************************************************************
static void
SendBreak(bool bSend)
{
	return;
}

//****************************************************************************
//
// Handles CDC driver notifications related to control and setup of the
// device.
//
// \param pvCBData is the client-supplied callback pointer for this channel.
// \param ui32Event identifies the event we are being notified about.
// \param ui32MsgValue is an event-specific value.
// \param pvMsgData is an event-specific pointer.
//
// This function is called by the CDC driver to perform control-related
// operations on behalf of the USB host.  These functions include setting
// and querying the serial communication parameters, setting handshake line
// states and sending break conditions.
//
// \return The return value is event-specific.
//
//****************************************************************************
uint32_t
ControlHandler(void *pvCBData, uint32_t ui32Event,
		uint32_t ui32MsgValue, void *pvMsgData)
{
	uint32_t ui32IntsOff;

	//
	// Which event are we being asked to process?
	//
	switch(ui32Event)
	{
	//
	// We are connected to a host and communication is now possible.
	//
	case USB_EVENT_CONNECTED:
	{
		g_bUSBConfigured = true;

		//
		// Flush our buffers.
		//
		USBBufferFlush(&g_psTxBuffer);
		USBBufferFlush(&g_psRxBuffer);

		//
		// Tell the main loop to update the display.
		//
		ui32IntsOff = IntMasterDisable();
		g_pcStatus = "Host connected.";
		g_ui32Flags |= COMMAND_STATUS_UPDATE;
		if(!ui32IntsOff)
		{
			IntMasterEnable();
		}
		break;
	}

	//
	// The host has disconnected.
	//
	case USB_EVENT_DISCONNECTED:
	{
		g_bUSBConfigured = false;
		ui32IntsOff = IntMasterDisable();
		g_pcStatus = "Host disconnected.";
		g_ui32Flags |= COMMAND_STATUS_UPDATE;
		if(!ui32IntsOff)
		{
			IntMasterEnable();
		}
		break;
	}

	//
	// Return the current serial communication parameters.
	//
	case USBD_CDC_EVENT_GET_LINE_CODING:
	{
		GetLineCoding(pvMsgData);
		break;
	}

	//
	// Set the current serial communication parameters.
	//
	case USBD_CDC_EVENT_SET_LINE_CODING:
	{
		SetLineCoding(pvMsgData);
		break;
	}

	//
	// Set the current serial communication parameters.
	//
	case USBD_CDC_EVENT_SET_CONTROL_LINE_STATE:
	{
		SetControlLineState((unsigned short)ui32MsgValue);
		break;
	}

	//
	// Send a break condition on the serial line.
	//
	case USBD_CDC_EVENT_SEND_BREAK:
	{
		SendBreak(true);
		break;
	}

	//
	// Clear the break condition on the serial line.
	//
	case USBD_CDC_EVENT_CLEAR_BREAK:
	{
		SendBreak(false);
		break;
	}

	//
	// Ignore SUSPEND and RESUME for now.
	//
	case USB_EVENT_SUSPEND:
	case USB_EVENT_RESUME:
	{
		break;
	}

	//
	// We don't expect to receive any other events.  Ignore any that show
	// up in a release build or hang in a debug build.
	//
	default:
	{
		break;
	}
	}

	return(0);
}

//****************************************************************************
//
// Handles CDC driver notifications related to the transmit channel (data to
// the USB host).
//
// \param ui32CBData is the client-supplied callback pointer for this channel.
// \param ui32Event identifies the event we are being notified about.
// \param ui32MsgValue is an event-specific value.
// \param pvMsgData is an event-specific pointer.
//
// This function is called by the CDC driver to notify us of any events
// related to operation of the transmit data channel (the IN channel carrying
// data to the USB host).
//
// \return The return value is event-specific.
//
//****************************************************************************
uint32_t
TxHandlerEcho(void *pvCBData, uint32_t ui32Event, uint32_t ui32MsgValue,
		void *pvMsgData)
{
	//
	// Which event have we been sent?
	//
	switch(ui32Event)
	{
	case USB_EVENT_TX_COMPLETE:
	{
		//
		// Since we are using the USBBuffer, we don't need to do anything
		// here.
		//
		break;
	}

	//
	// We don't expect to receive any other events.  Ignore any that show
	// up in a release build or hang in a debug build.
	//
	default:
	{
		break;
	}
	}
	return(0);
}

//****************************************************************************
//
// Handles CDC driver notifications related to the receive channel (data from
// the USB host).
//
// \param ui32CBData is the client-supplied callback data value for this
// channel.
// \param ui32Event identifies the event we are being notified about.
// \param ui32MsgValue is an event-specific value.
// \param pvMsgData is an event-specific pointer.
//
// This function is called by the CDC driver to notify us of any events
// related to operation of the receive data channel (the OUT channel carrying
// data from the USB host).
//
// \return The return value is event-specific.
//
//****************************************************************************
uint32_t
RxHandlerCmd(void *pvCBData, uint32_t ui32Event, uint32_t ui32MsgValue,
		void *pvMsgData)
{
	const tUSBDCDCDevice *psCDCDevice;
	const tUSBBuffer *pBufferRx;
	const tUSBBuffer *pBufferTx;
	uint32_t packetSize = 0;
	static int i = 2;
	//char nullterm[3] = {'\0'};

	//
	// Which event are we being sent?
	//
	switch(ui32Event)
	{
	//
	// A new packet has been received.
	//
	case USB_EVENT_RX_AVAILABLE:
	{
		//
		// Create a device pointer.
		//
		psCDCDevice = (const tUSBDCDCDevice *)pvCBData;
		pBufferRx = (const tUSBBuffer *)psCDCDevice->pvRxCBData;
		pBufferTx = (const tUSBBuffer *)psCDCDevice->pvTxCBData;

		packetSize = USBBufferDataAvailable(pBufferRx);
		SizeSend = packetSize;
		pBufferSend = pBufferTx;

		disableFunction();

		USBBufferRead(pBufferRx, g_pcCmdBuf, packetSize);
		struc = receive_package(g_pcCmdBuf[0]);
		USBBufferFlush(pBufferRx);
		if(struc.transmit == TRANSMIT) {
			for(i = 0; i<=struc.transmit_size; i++)
				USBBufferWrite(pBufferTx, &struc.reply[i], packetSize);

			if(markChannel1 == OFF) {
				sampleData1 = NOT_TRANSMIT;
				channel1 = OFF;
			} else {
				sampleData1 = TRANSMIT;
				channel1 = ON;
			}
			if(markChannel2 == OFF) {
				sampleData2 = NOT_TRANSMIT;
				channel2 = OFF;
			} else {
				sampleData2 = TRANSMIT;
				channel2 = ON;
			}

		}

		enableFunction();

		break;
	}

	//
	// We are being asked how much unprocessed data we have still to
	// process. We return 0 if the UART is currently idle or 1 if it is
	// in the process of transmitting something. The actual number of
	// bytes in the UART FIFO is not important here, merely whether or
	// not everything previously sent to us has been transmitted.
	//
	case USB_EVENT_DATA_REMAINING:
	{
		//
		// Get the number of bytes in the buffer and add 1 if some data
		// still has to clear the transmitter.
		//
		return(0);
	}

	//
	// We are being asked to provide a buffer into which the next packet
	// can be read. We do not support this mode of receiving data so let
	// the driver know by returning 0. The CDC driver should not be
	// sending this message but this is included just for illustration and
	// completeness.
	//
	case USB_EVENT_REQUEST_BUFFER:
	{
		return(0);
	}

	//
	// We don't expect to receive any other events.  Ignore any that show
	// up in a release build or hang in a debug build.
	//
	default:
	{
		break;
	}
	}

	return(0);
}

void SendESD(struct values *struc)
{
	uint16_t i;

	for(i = 0; i<=struc->transmit_sizeESD; i++)
		USBBufferWrite(pBufferSend, &struc->replyESD[i], SizeSend);
}

void SendSD(struct values *struc)
{
	uint16_t i;

	for(i = 0; i<=struc->transmit_sizeSD; i++)
		USBBufferWrite(pBufferSend, &struc->replySD[i], SizeSend);
}


//****************************************************************************
//
// This is the main application entry function.
//
//****************************************************************************
uint16_t _TIVA_USB_Configuration(void)
{
	//
	// Run from the PLL at 120 MHz.
	//
	g_ui32SysClock = MAP_SysCtlClockFreqSet((SYSCTL_XTAL_25MHZ |
			SYSCTL_OSC_MAIN |
			SYSCTL_USE_PLL |
			SYSCTL_CFG_VCO_480), 120000000);

	//
	// Not configured initially.
	//
	g_bUSBConfigured = false;

	//
	// Enable the peripherals used in this example.
	//
	ROM_SysCtlPeripheralEnable(SYSCTL_PERIPH_USB0);

	//
	// Configure the device pins.
	//
	PinoutSet(false, true);

	//
	// Initialize the transmit and receive buffers for first serial device.	//
	USBBufferInit(&g_psTxBuffer);
	USBBufferInit(&g_psRxBuffer);

	//
	// Initialize the first serial port instances that is part of this
	// composite device.
	//
	g_sCompDevice.psDevices[0].pvInstance =
			USBDCDCCompositeInit(0, &g_psCDCDevice, &g_psCompEntries);

	//
	// Pass the device information to the USB library and place the device
	// on the bus.
	//
	USBDCompositeInit(0, &g_sCompDevice, DESCRIPTOR_DATA_SIZE,
			g_pucDescriptorData);

	//
	// Main application loop.
	//
//	while(1)
//	{
//		SysCtlSleep();
//	}
	return SUCCESS;
}
