/*
 * Protokoll.c
 *
 *  Created on: 01.09.2016
 *      Author: HP
 */

#define PROTOKOLL
#include "Protokoll.h"

struct values flags;

struct values receive_package(uint8_t data)
{
	static int i = 0;
	sampleData1 = sampleData2 = NOT_TRANSMIT;
	channel1 = channel2 = OFF;

	flags.package[i] = data;
	flags.size = HEADER;

	if(flags.package[0] == STS) {
		if(i == SIZE_STS) {
			process();
			setSTS();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == SCS) {
		if(i == SIZE_SCS) {
			process();
			setSCS();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == STB) {
		if(i == SIZE_STB) {
			process();
			setSTB();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == GHWV) {
		if(i == SIZE_GHWV) {
			process();
			setHWV();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == GSWV) {
		if(i == SIZE_GSWV) {
			process();
			setSWV();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == SSM) {
		if(i == SIZE_SSM) {
			process();
			setSSM();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == ACK) {
		if(i == SIZE_ACK) {
			process();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	} else if(flags.package[0] == NACK) {
		if(i == SIZE_NACK) {
			process();
			i=0;
			flags.transmit = TRANSMIT;
		} else {
			i++;
			flags.transmit = NOT_TRANSMIT;
		}
	}

	return flags;
}

void enableFunction(void)
{
	if(sample_mode == RTS) {
		if(flags.channel1 == ON) {
			if(edge1 == DISABLE) {
				TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER0_BASE, TIMER_A);
			} else {
				TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER0_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			}

		}

		if(flags.channel2 == ON) {
			if(edge2 == DISABLE) {
				TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER1_BASE, TIMER_A);
			} else {
				TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER1_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			}
		}
	} else if(sample_mode == ETS) {
		if(flags.channel1 == ON) {
			actualRounds = 0;
			sampleCH1 = 0;
			TimerEnable(TIMER0_BASE, TIMER_A);
			if(edge1 == DISABLE) {
				GPIOstate1 = ON;
				TimerEnable(TIMER0_BASE, TIMER_A);
				TimerEnable(TIMER2_BASE, TIMER_A);
			} else {
				TimerEnable(TIMER0_BASE, TIMER_A);
				TimerEnable(TIMER2_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			}
		}

		if(flags.channel2 == ON) {
			actualRounds = 0;
			sampleCH2 = 0;
			TimerEnable(TIMER1_BASE, TIMER_A);
			if(edge2 == DISABLE) {
				TimerEnable(TIMER3_BASE, TIMER_A);
				TimerEnable(TIMER1_BASE, TIMER_A);

			} else {
				TimerEnable(TIMER1_BASE, TIMER_A);
				TimerEnable(TIMER3_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			}
		}
	}
}

void disableFunction(void)
{
	if(sample_mode == RTS) {
		TimerDisable(TIMER0_BASE, TIMER_A);
		GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
		TimerDisable(TIMER1_BASE, TIMER_A);
		GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
	} else if(sample_mode == ETS) {
		TimerDisable(TIMER0_BASE, TIMER_A);
		TimerDisable(TIMER1_BASE, TIMER_A);
		TimerDisable(TIMER2_BASE, TIMER_A);
		TimerDisable(TIMER3_BASE, TIMER_A);
		GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
		GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
//		uDMAChannelDisable(UDMA_SEC_CHANNEL_ADC13);
//		ADCIntDisableEx(ADC1_BASE, ADC_INT_DMA_SS3);
//		IntDisable(INT_ADC1SS3);
	}
}
void setEDS1(void)
{
	int j = 18;
	int n;
	int8_t low = 0;
	int8_t high = 0;
	uint16_t payload_size;
	static uint8_t ready = 0;

	flags.replyESD[0] = ESD;
	flags.replyESD[1] = OSCILLOSCOPE;
	flags.replyESD[2] = CH1;
	payload_size = 11 + SETSsamples;
	low = *((uint8_t*)(&payload_size));
	high = *((uint8_t*)(&payload_size) + 1);
	flags.replyESD[3] = high;
	flags.replyESD[4] = low;
	flags = HeaderCrcESD(&flags);
	flags = crc16(&flags);
	flags.replyESD[5] = flags.crc_high;
	flags.replyESD[6] = flags.crc_low;
	flags.replyESD[7] = ZERO;
	flags.replyESD[8] = ZERO;
	flags.replyESD[9] = ZERO;

	flags.replyESD[10] = rounds1;
	if(rounds1 == SETSrounds) {
		ready = 1;
		rounds1 = 1;
	} else
		rounds1++;
	if(actualRounds == (SETSrounds - 1)) {
		actualRounds = 0;
	} else {
		actualRounds++;
	}
	flags.replyESD[11] = ZERO;
	flags.replyESD[12] = ZERO;
	flags.replyESD[13] = ZERO;
	flags.replyESD[14] = ZERO;
	flags.replyESD[15] = BITNUMBER;
	flags.replyESD[16] = flags.etsSampleHigh;
	flags.replyESD[17] = flags.etsSampleLow;
	payload_size = SETSsamples + 2;
	for(n = 2; n<payload_size; n++) {
		flags.replyESD[j] = adc1[n];
		j++;
	}
	flags = PayloadCrcESD(&flags);
	flags = crc16(&flags);
	flags.replyESD[j] = flags.crc_high;
	j++;
	flags.replyESD[j] = flags.crc_low;
	flags.transmit_sizeESD = j;
	SendESD(&flags);

	packet1ETS = NOT_TRANSMIT;
	sampleCH1 = 0;
	flags = ETS_Packages(&flags);

	if(ready == 1) {
		ready = 0;
		if(flags.channel2 == ON) {
			actualRounds = 0;
			rounds2 = 1;
			if(edge2 == DISABLE) {
				if(actualRounds != 0) {
					TimerEnable(TIMER1_BASE, TIMER_A);
					TimerEnable(TIMER3_BASE, TIMER_A);
				}
			} else {
				TimerEnable(TIMER1_BASE, TIMER_A);
				TimerEnable(TIMER3_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			}
		} else if(flags.channel1 == ON) {
			if(edge1 == DISABLE) {
				if(actualRounds != 0) {
					GPIOstate1 = ON;
					TimerEnable(TIMER0_BASE, TIMER_A);
					TimerEnable(TIMER2_BASE, TIMER_A);
				} else
					TimerEnable(TIMER0_BASE, TIMER_A);
			} else {
				TimerEnable(TIMER0_BASE, TIMER_A);
				TimerEnable(TIMER2_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			}
		}
	} else if(flags.channel1 == ON) {
		if(edge1 == DISABLE) {
			if(actualRounds != 0) {
				GPIOstate1 = ON;
				TimerEnable(TIMER0_BASE, TIMER_A);
				TimerEnable(TIMER2_BASE, TIMER_A);
			} else
				TimerEnable(TIMER0_BASE, TIMER_A);

		} else {
			TimerEnable(TIMER0_BASE, TIMER_A);
			TimerEnable(TIMER2_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
		}
	}
}

void setEDS2(void)
{
	int j = 18;
	int n;
	int8_t low = 0;
	int8_t high = 0;
	uint16_t payload_size;
	static uint8_t rounds = 1;
	static uint8_t ready = 0;

	flags.replyESD[0] = ESD;
	flags.replyESD[1] = OSCILLOSCOPE;
	flags.replyESD[2] = CH2;
	payload_size = 11 + SETSsamples;
	low = *((uint8_t*)(&payload_size));
	high = *((uint8_t*)(&payload_size) + 1);
	flags.replyESD[3] = high;
	flags.replyESD[4] = low;
	flags = HeaderCrcESD(&flags);
	flags = crc16(&flags);
	flags.replyESD[5] = flags.crc_high;
	flags.replyESD[6] = flags.crc_low;
	flags.replyESD[7] = ZERO;
	flags.replyESD[8] = ZERO;
	flags.replyESD[9] = ZERO;

	flags.replyESD[10] = rounds2;
	if(rounds2 == SETSrounds) {
		rounds2 = 1;
		ready = 1;
	} else
		rounds2++;
	if(actualRounds == (SETSrounds - 1)) {
		actualRounds = 0;
	} else {
		actualRounds++;
	}
	flags.replyESD[11] = ZERO;
	flags.replyESD[12] = ZERO;
	flags.replyESD[13] = ZERO;
	flags.replyESD[14] = ZERO;
	flags.replyESD[15] = BITNUMBER;
	flags.replyESD[16] = flags.etsSampleHigh;
	flags.replyESD[17] = flags.etsSampleLow;
	payload_size = SETSsamples + 2;
	for(n = 2; n<payload_size; n++) {
		flags.replyESD[j] = adc2[n];
		j++;
	}
	flags = PayloadCrcESD(&flags);
	flags = crc16(&flags);
	flags.replyESD[j] = flags.crc_high;
	j++;
	flags.replyESD[j] = flags.crc_low;
	flags.transmit_sizeESD = j;
	SendESD(&flags);

	packet2ETS = NOT_TRANSMIT;
	sampleCH2 = 0;
	flags = ETS_Packages(&flags);

	if(ready == 1) {
		ready = 0;
		if(flags.channel1 == ON) {
			actualRounds = 0;
			rounds1 = 1;
			if(edge1 == DISABLE) {
				if(actualRounds != 0) {
					TimerEnable(TIMER0_BASE, TIMER_A);
					TimerEnable(TIMER2_BASE, TIMER_A);
				}
			} else {
				TimerEnable(TIMER0_BASE, TIMER_A);
				TimerEnable(TIMER2_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			}
		} else if(flags.channel2 == ON) {
			if(edge2 == DISABLE) {
				if(actualRounds != 0) {
					TimerEnable(TIMER1_BASE, TIMER_A);
					TimerEnable(TIMER3_BASE, TIMER_A);
				}
			} else {
				TimerEnable(TIMER1_BASE, TIMER_A);
				TimerEnable(TIMER3_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			}
		}
	} else if(flags.channel2 == ON) {
		if(edge2 == DISABLE) {
			if(actualRounds != 0) {
				TimerEnable(TIMER1_BASE, TIMER_A);
				TimerEnable(TIMER3_BASE, TIMER_A);
			}
		} else {
			TimerEnable(TIMER1_BASE, TIMER_A);
			TimerEnable(TIMER3_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
		}
	}
}

void setSD1(void)
{
	static int i = 0;
	int j = 7;

	flags.replySD[0] = SD;
	flags.replySD[1] = OSCILLOSCOPE;
	flags.replySD[2] = CH1;
	flags.replySD[3] = PAYLOAD_SD_HIGH;
	flags.replySD[4] = ZERO;
	flags = HeaderCrcSD(&flags);
	flags = crc16(&flags);
	flags.replySD[5] = flags.crc_high;
	flags.replySD[6] = flags.crc_low;
	for(i = 2; i < 550; i++) {
		flags.replySD[j] = adc1[i];
		j++;
	}
	flags = PayloadCrcSD(&flags);
	flags = crc16(&flags);
	flags.replySD[519] = flags.crc_high;
	flags.replySD[520] = flags.crc_low;
	flags.transmit_sizeSD = SIZE_SD;
	SendSD(&flags);

	packet1 = NOT_TRANSMIT;
	sampleCH1 = 0;

	if(flags.channel2 == ON) {
			if(edge2 == DISABLE) {
				TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER1_BASE, TIMER_A);
			} else {
				TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER1_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			}

	} else if(flags.channel1 == ON) {
				if(edge1 == DISABLE) {
					TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
					SysCtlDelay(50);
					TimerEnable(TIMER0_BASE, TIMER_A);
				} else {
					TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
					SysCtlDelay(50);
					TimerEnable(TIMER0_BASE, TIMER_A);
					GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
				}
	}
}

void setSD2(void)
{
	static int i = 0;
	int j = 7;

	flags.replySD[0] = SD;
	flags.replySD[1] = OSCILLOSCOPE;
	flags.replySD[2] = CH2;
	flags.replySD[3] = PAYLOAD_SD_HIGH;
	flags.replySD[4] = ZERO;
	flags = HeaderCrcSD(&flags);
	flags = crc16(&flags);
	flags.replySD[5] = flags.crc_high;
	flags.replySD[6] = flags.crc_low;
	for(i = 2; i < 550; i++) {
		flags.replySD[j] = adc2[i];
		j++;
	}
	flags = PayloadCrcSD(&flags);
	flags = crc16(&flags);
	flags.replySD[519] = flags.crc_high;
	flags.replySD[520] = flags.crc_low;
	flags.transmit_sizeSD = SIZE_SD;
	SendSD(&flags);

	packet2 = NOT_TRANSMIT;
	sampleCH2 = 0;

	if(flags.channel1 == ON) {
			if(edge1 == DISABLE) {
				TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER0_BASE, TIMER_A);
			} else {
				TimerLoadSet(TIMER0_BASE, TIMER_A, flags.loadValue);
				SysCtlDelay(50);
				TimerEnable(TIMER0_BASE, TIMER_A);
				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			}


	} else if(flags.channel2 == ON) {
		if(edge2 == DISABLE) {
			TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
			SysCtlDelay(50);
			TimerEnable(TIMER1_BASE, TIMER_A);
		} else {
			TimerLoadSet(TIMER1_BASE, TIMER_A, flags.loadValue);
			SysCtlDelay(50);
			TimerEnable(TIMER1_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
		}

	}
}

void setSTS(void)
{
	triggerChannel = flags.package[2];
	flags = SetTriggerType_Mode(&flags);
	if(flags.error == SUCCESS) {
		flags = SetPWMValue(&flags);
		if(flags.error == SUCCESS)
			setACK();
		else
			setNACK();
	} else
		setNACK();
//	setACK();
	if(markChannel1 == ON && markChannel2 == ON)
		flags.channel = BOTH;
	else {
		if(markChannel1 == ON)
			flags.channel = CHANNEL0;
		else if(markChannel2 == ON)
			flags.channel = CHANNEL1;
	}

}

void setSCS(void)
{
	flags = SetChannelSettings(&flags);
	if(flags.error == SUCCESS) {
		flags = GainSetting(&flags);
		if(flags.error == SUCCESS) {
			setACK();
			flags.coupling = flags.package[8];
			if(flags.coupling != 0) {
				if(flags.channel == CHANNEL0)
					markChannel1 = ON;
				else
					markChannel2 = ON;
			} else {
				if(flags.channel == CHANNEL0)
					markChannel1 = OFF;
				else
					markChannel2 = OFF;
			}

			if(markChannel1 == ON && markChannel2 == ON)
				flags.channel = BOTH;
		} else
			setNACK();
	} else
		setNACK();
}

void setSTB(void)
{
	if(flags.error == SUCCESS) {
		flags = PayloadCrcSTB(&flags);
		flags = crc16(&flags);
		flags = compare_crc(&flags);
		if(flags.error == SUCCESS) {
			flags = SetTimeBaseSettings(&flags);
			if(flags.error == SUCCESS)
				setACK();
			else
				setNACK();
		} else
			setNACK();
	}
	if(markChannel1 == ON && markChannel2 == ON)
		flags.channel = BOTH;
	else {
		if(markChannel1 == ON)
			flags.channel = CHANNEL0;
		else if(markChannel2 == ON)
			flags.channel = CHANNEL1;
	}
}

void setSSM(void)
{
	flags = PayloadCrcSSM(&flags);
	flags = crc16(&flags);
	flags = compare_crc(&flags);
	if(flags.error == SUCCESS) {
		flags = SetSamplingMode(&flags);
		if(flags.error == SUCCESS) {
				setACK();
		} else
			setNACK();
	} else
		setNACK();

	if(markChannel1 == ON && markChannel2 == ON)
		flags.channel = BOTH;
	else {
		if(markChannel1 == ON)
			flags.channel = CHANNEL0;
		else if(markChannel2 == ON)
			flags.channel = CHANNEL1;
	}
}

void setHWV(void)
{
	flags.reply[0] = HWV;
	flags.reply[1] = flags.device;
	flags.reply[2] = flags.channel;
	flags.reply[3] = ZERO;
	flags.reply[4] = PAYLOAD_HWV;
	flags = HeaderCrcHWV(&flags);
	flags.size = HEADER;
	flags = crc16(&flags);
	flags.reply[5] = flags.crc_high;
	flags.reply[6] = flags.crc_low;
	flags.reply[7] = MAJOR_VERSION;
	flags.reply[8] = MINOR_VERSION;
	flags.reply[9] = SUB_VERSION;
	flags.reply[10] = ZERO;
	flags = PayloadCrcHWV(&flags);
	flags = crc16(&flags);
	flags.reply[11] = flags.crc_high;
	flags.reply[12] = flags.crc_low;
	flags.transmit_size = SIZE_HWV;
}

void setSWV(void)
{
	flags.reply[0] = SWV;
	flags.reply[1] = flags.device;
	flags.reply[2] = flags.channel;
	flags.reply[3] = ZERO;
	flags.reply[4] = PAYLOAD_SWV;
	flags = HeaderCrcSWV(&flags);
	flags.size = HEADER;
	flags = crc16(&flags);
	flags.reply[5] = flags.crc_high;
	flags.reply[6] = flags.crc_low;
	flags.reply[7] = MAJOR_VERSION;
	flags.reply[8] = MINOR_VERSION;
	flags.reply[9] = SUB_VERSION;
	flags.reply[10] = ZERO;
	flags = PayloadCrcSWV(&flags);
	flags = crc16(&flags);
	flags.reply[11] = flags.crc_high;
	flags.reply[12] = flags.crc_low;
	flags.transmit_size = SIZE_SWV;
}


void setNACK(void)
{
	flags.reply[0] = NACK;
	flags.reply[1] = flags.device;
	flags.reply[2] = flags.channel;
	flags.reply[3] = ZERO;
	flags.reply[4] = PAYLOAD_NACK;
	flags = HeaderCrcNACK(&flags);
	flags = crc16(&flags);
	flags.reply[5] = flags.crc_high;
	flags.reply[6] = flags.crc_low;
	flags.reply[7] = flags.error;
	flags = PayloadCrcNACK(&flags);
	flags = crc16(&flags);
	flags.reply[8] = flags.crc_high;
	flags.reply[9] = flags.crc_low;
	flags.transmit_size = SIZE_NACK;
}

void setACK(void)
{
	flags.reply[0] = ACK;
	flags.reply[1] = flags.device;
	flags.reply[2] = flags.channel;
	flags.reply[3] = ZERO;
	flags.reply[4] = PAYLOAD_ACK;
	flags = HeaderCrcACK(&flags);
	flags = crc16(&flags);
	flags.reply[5] = flags.crc_high;
	flags.reply[6] = flags.crc_low;
	flags.transmit_size = SIZE_ACK;
}

void process(void)
{
	sort_package();
	flags = error_request(&flags);
	if(flags.error == SUCCESS) {
		HeaderCrcReceive();
		flags = crc16(&flags);
		flags = compare_crc(&flags);
		if(flags.error != SUCCESS)
			setNACK();
		else
			setACK();
	} else
		setNACK();
}

void sort_package(void)
{
	flags.command = (uint8_t)flags.package[0];
	flags.device = (uint8_t)flags.package[1];
	flags.channel = (uint8_t)flags.package[2];
	flags.payload_high = (uint8_t)flags.package[3];
	flags.payload_low = (uint8_t)flags.package[4];
	flags.crc_receive_high = (int8_t)flags.package[5];
	flags.crc_receive_low = (int8_t)flags.package[6];
}


void HeaderCrcReceive(void)
{
	flags.crc_header[0] = (uint8_t)flags.command;
	flags.crc_header[1] = (uint8_t)flags.device;
	flags.crc_header[2] = (uint8_t)flags.channel;
	flags.crc_header[3] = (uint8_t)flags.payload_high;
	flags.crc_header[4] = (uint8_t)flags.payload_low;
}

