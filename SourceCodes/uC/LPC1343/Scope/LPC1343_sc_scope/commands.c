/**
 * 	@file 	commands.c
 * 	@date 	3. Jän. 2016
 * 	@author	Christian Matar
 */

#include "commands.h"


void hardwareVersion(uint8_t channel)
{
	uint8_t hw_msg[HW_SIZE];
	hw_msg[0] = HW_CMD_NR;
	hw_msg[1] = 0;
	hw_msg[2] = channel;
	hw_msg[3] = 4;

	hw_msg[5] = gen_crc16(hw_msg, HEADER_SIZE);
	hw_msg[7] = 4;
	hw_msg[8] = 0;
	hw_msg[9] = 0;
	hw_msg[10] = 0;
	hw_msg[11] = gen_crc16(hw_msg, HW_SIZE);

#if USB_COM

#else
	UART_putstr(hw_msg, HW_SIZE);
#endif
}

void nack(uint8_t channel, uint8_t error)
{
	uint8_t nack_msg[NACK_SIZE];
	nack_msg[0] = NACK_CMD_NR;
	nack_msg[1] = 0;
	nack_msg[2] = channel;
	nack_msg[3] = 1;
	nack_msg[5] = gen_crc16(nack_msg, HEADER_SIZE);
	nack_msg[7] = error;
	nack_msg[8] = gen_crc16(nack_msg, NACK_SIZE);

#if USB_COM

#else
	UART_putstr(nack_msg, NACK_SIZE);
#endif
}

void ack(uint8_t channel)
{
	uint8_t ack_msg[ACK_SIZE];
	ack_msg[0] = ACK_CMD_NR;
	ack_msg[1] = 0;
	ack_msg[2] = channel;
	ack_msg[3] = 0;
	ack_msg[4] = gen_crc16(ack_msg, ACK_SIZE);

#if USB_COM

#else
	UART_putstr(ack_msg, ACK_SIZE);
#endif
}

void syncMsg(uint8_t channel)
{
	uint8_t sync_msg[SYNCHRO_SIZE];
	sync_msg[0] = SYNC_CMD_NR;
	sync_msg[1] = 3;
	sync_msg[2] = channel;
	sync_msg[3] = 7;
	sync_msg[5] = gen_crc16(sync_msg, HEADER_SIZE);
	sync_msg[7] = 126;
	sync_msg[8] = 0;
	sync_msg[12] = gen_crc16(sync_msg, SYNCHRO_SIZE);

#if USB_COM

#else
	UART_putstr(sync_msg, SYNCHRO_SIZE);
#endif
}

void triggerNotify(uint8_t channel, uint32_t holdoff)
{
	uint8_t trigger_msg[TRIGGER_SIZE];
	trigger_msg[0] = TRIGGER_CMD_NR;
	trigger_msg[1] = 0;
	trigger_msg[2] = channel;
	trigger_msg[3] = 7;
	trigger_msg[5] = gen_crc16(trigger_msg, HEADER_SIZE);
	trigger_msg[7] = 127;
	trigger_msg[8] = holdoff;
	trigger_msg[12] = gen_crc16(trigger_msg, TRIGGER_SIZE);

#if USB_COM

#else
	UART_putstr(trigger_msg, TRIGGER_SIZE);
#endif
}

void frequencyInfo(uint8_t channel, uint32_t holdoff, uint32_t freq)
{
	uint8_t freq_msg[FREQ_SIZE];
	freq_msg[0] = FREQ_CMD_NR;
	freq_msg[1] = 0;
	freq_msg[2] = channel;
	freq_msg[3] = 7;
	freq_msg[5] = gen_crc16(freq_msg, HEADER_SIZE);
	freq_msg[7] = freq;

	freq_msg[11] = holdoff;
	freq_msg[15] = gen_crc16(freq_msg, FREQ_SIZE);

#if USB_COM

#else
	UART_putstr(freq_msg, FREQ_SIZE);
#endif
}

void parseCmd(uint8_t *data)
{
	if(!data)
	{
		nack(0, 0);
		return;
	}

	uint8_t channel = data[2];

	if(channel!='1' && channel!='2')
	{
		nack(channel, WRONG_CHANNEL);
		return;
	}

	switch(data[0])
	{
	// Get HW received
	case '4':
#if USE_CRC
		if(check_crc16(data, SW_SIZE))
#endif
			hardwareVersion(channel);
#if USE_CRC
		else
			nack(0, 0);
#endif
		break;

	// Start SYNC received
	case 8:
#if USE_CRC
		if(check_crc16(data, SYNC_SIZE))
#endif
			syncMsg(channel);

#if USE_CRC
		else
			nack(0, 0);
#endif
		break;

	// Set Trigger settings received
	case '1':
#if USE_CRC
		if(check_crc16(data, TRIGGER_SIZE))
		{
#endif
			if(channel=='1')
			{
				updateDutyCycle(LPC_TMR16B1, PWM_MATCH_REG_0, (double) data[8]);
				ack(channel);
			}
			else if(channel=='2')
			{
				updateDutyCycle(LPC_TMR16B1, PWM_MATCH_REG_1, (double) data[8]);
				ack(channel);
			}
			else
				nack(channel, WRONG_CHANNEL);
#if USE_CRC
		}
		else
			nack(channel, 0);
#endif
		break;

	// Get Parameter received
	case '9':
#if USE_CRC
		if(check_crc16(data, FREQ_SIZE))
		{
#endif
			if(channel=='1')
			{
				startTimer(LPC_TMR32B0);
				startPWM(LPC_TMR16B1);
			}
			else
			{
				startTimer(LPC_TMR32B1);
				startPWM(LPC_TMR16B1);
			}
			ack(channel);
		break;

	default:
		nack(channel, UNKNOWN);
		break;
	}
}
