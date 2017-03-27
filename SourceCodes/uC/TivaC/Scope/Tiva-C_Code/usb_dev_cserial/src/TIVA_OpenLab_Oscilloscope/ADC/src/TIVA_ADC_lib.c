#include "TIVA_ADC_lib.h"

struct values SetTimeBase(struct values *ptr)
{
//	uint32_t loadValue;
	if(ptr->RTMSsamples >= 500 && ptr->RTMSsamples <= 20000000) {
		ptr->loadValue = TIMER/ptr->RTMSsamples * 2;
		TimerLoadSet(TIMER0_BASE, TIMER_A, ptr->loadValue);
		TimerLoadSet(TIMER1_BASE, TIMER_A, ptr->loadValue);
		ptr->error = SUCCESS;
	} else
		ptr-> error = NACK_NOT_SUPPORTED;

	return *ptr;
}

struct values SetChannelSettings(struct values *ptr)
{
	if(ptr->channel == triggerChannel) {
		if(ptr->package[8] == 0) {
			switch(ptr->channel)
			{
			case CHANNEL0:
				ptr->channel1 = OFF;
				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
				TimerDisable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
				ptr->channel2 = OFF;
				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
				TimerDisable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else if(ptr->package[8] == 1) {
			switch(ptr->channel)
			{
			case CHANNEL0:
				ptr->channel1 = ON;
//				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
				TimerEnable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
				ptr->channel2 = ON;
//				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
				TimerEnable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else if(ptr->package[8] == 2) {
			switch(ptr->channel)
			{
			case CHANNEL0:
//				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
//				TimerEnable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
//				GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
	//			TimerEnable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else
			ptr->error = NACK_NOT_SUPPORTED;
	} else {
		if(ptr->package[8] == 0) {
			switch(ptr->channel)
			{
			case CHANNEL0:
				ptr->channel1 = OFF;
				TimerDisable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
				ptr->channel2 = OFF;
				TimerDisable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else if(ptr->package[8] == 1) {
			switch(ptr->channel)
			{
			case CHANNEL0:
				ptr->channel1 = ON;
				TimerEnable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
				ptr->channel2 = ON;
				TimerEnable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else if(ptr->package[8] == 2) {
			switch(ptr->channel)
			{
			case CHANNEL0:
//				TimerEnable(TIMER0_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			case CHANNEL1:
//				TimerEnable(TIMER1_BASE, TIMER_A);
				ptr->error = SUCCESS;
				break;

			default:
				ptr->error = NACK_NOT_SUPPORTED;
			}
		} else
			ptr->error = NACK_NOT_SUPPORTED;
	}

	return *ptr;
}
