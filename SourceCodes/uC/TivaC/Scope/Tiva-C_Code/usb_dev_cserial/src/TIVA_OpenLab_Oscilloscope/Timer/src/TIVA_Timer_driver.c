/*
 * TIVA_Timer_driver.c
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#include "TIVA_Timer_driver.h"

uint16_t _TIVA_Timer_Configuration(void)
{
	unsigned long sampleRate1 = 60000000;
	unsigned long sampleRate2 = 60000000;

	SysCtlPeripheralReset(SYSCTL_PERIPH_TIMER2);
	SysCtlPeripheralReset(SYSCTL_PERIPH_TIMER3);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER2);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER3);
	TimerConfigure(TIMER2_BASE, TIMER_CFG_PERIODIC);
	TimerConfigure(TIMER3_BASE, TIMER_CFG_PERIODIC);
	TimerLoadSet(TIMER2_BASE, TIMER_A, sampleRate1);
	TimerLoadSet(TIMER3_BASE, TIMER_A, sampleRate2);
	TimerIntRegister(TIMER2_BASE, TIMER_A, &Timer2IntHandler);
	TimerIntRegister(TIMER3_BASE, TIMER_A, &Timer3IntHandler);
	IntEnable(INT_TIMER2A);
	IntEnable(INT_TIMER3A);
	TimerIntEnable(TIMER2_BASE, TIMER_TIMA_TIMEOUT);
	TimerIntEnable(TIMER3_BASE, TIMER_TIMA_TIMEOUT);
	IntMasterEnable();

	return SUCCESS;
}

void Timer2IntHandler(void)
{
	uint32_t ADC3Value[8];

	TimerIntClear(TIMER2_BASE, TIMER_TIMA_TIMEOUT);
	if(GPIOstate1 == ON) {
		TimerDisable(TIMER2_BASE, TIMER_A);
		ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
		adc1[0] = ADC3Value[0]/16;
		sampleCH1++;
		Timerstate1 = ON;
		GPIOstate1 = OFF;
	}
}

void Timer3IntHandler(void)
{
	uint32_t ADC2Value[8];

	TimerIntClear(TIMER3_BASE, TIMER_TIMA_TIMEOUT);
	if(GPIOstate2 == ON) {
		TimerDisable(TIMER3_BASE, TIMER_A);
		ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
		adc2[0] = ADC2Value[0]/16;
		sampleCH2++;
		Timerstate2 = ON;
		GPIOstate2 = OFF;
	}

}
