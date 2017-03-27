/*
 * TIVA_UART_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */
/***************************************************************************** LIBRARIES*/
#include "TIVA_ADC_driver.h"
/***************************************************************************** FUNCTIONS */
// ****** Variables ******
uint32_t ADC3Value[8];
uint32_t ADC2Value[8];

uint8_t _TIVA_ADC_RTM_Sequence_Configuration(void)
{
	ADCSequenceDisable(ADC1_BASE, 2);
//	if(channelNum == CH1) {
		ADCSequenceDisable(ADC0_BASE,0);
		ADCSequenceConfigure(ADC0_BASE,0,ADC_TRIGGER_TIMER,0);
		ADCSequenceEnable(ADC0_BASE,0);
//	} else if(channelNum == CH2) {
		ADCSequenceDisable(ADC1_BASE,0);
		ADCSequenceConfigure(ADC1_BASE,0,ADC_TRIGGER_TIMER,0);
		ADCSequenceEnable(ADC1_BASE,0);
//	}

	return SUCCESS;
}

uint16_t _TIVA_ADC_Timer_Configuration(void)
{
	unsigned long sampleRate3 = 60000000;
	unsigned long sampleRate2 = 60000000;

	SysCtlPeripheralReset(SYSCTL_PERIPH_TIMER0);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER0);
	SysCtlPeripheralReset(SYSCTL_PERIPH_TIMER1);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_TIMER1);
	TimerConfigure(TIMER0_BASE, TIMER_CFG_PERIODIC);
	TimerConfigure(TIMER1_BASE, TIMER_CFG_PERIODIC);
	sampleRate3=sampleRate3/50000;
	sampleRate2=sampleRate2/50000;
	TimerLoadSet(TIMER0_BASE, TIMER_A, sampleRate3);
	TimerLoadSet(TIMER1_BASE, TIMER_A, sampleRate2);
	TimerIntRegister(TIMER0_BASE, TIMER_A, &Timer0IntHandler);
	TimerIntRegister(TIMER1_BASE, TIMER_A, &Timer1IntHandler);
	IntEnable(INT_TIMER0A);
	IntEnable(INT_TIMER1A);
	TimerIntEnable(TIMER0_BASE, TIMER_TIMA_TIMEOUT);
	TimerIntEnable(TIMER1_BASE, TIMER_TIMA_TIMEOUT);
	IntMasterEnable();
//	TimerEnable(TIMER0_BASE, TIMER_A);
//	TimerEnable(TIMER1_BASE, TIMER_A);

	return SUCCESS;
}

void Timer0IntHandler(void)
{
	TimerIntClear(TIMER0_BASE, TIMER_TIMA_TIMEOUT);

	if(sample_mode == RTS) {
		if(edge1 == DISABLE || trigger1 == OUTOFRANGE) {
			ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
			adc1[sampleCH1] = ADC3Value[0]/16;
			sampleCH1++;

			if(sampleCH1 == SETSsamples) {
				packet1 = TRANSMIT;
				TimerDisable(TIMER0_BASE, TIMER_A);
			}
		} else if(trigger1 != OUTOFRANGE) {
			if(Intstate1 == ON) {
				ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
				adc1[sampleCH1] = ADC3Value[0]/16;
				sampleCH1++;
				if(sampleCH1 == 514) {
					Intstate1 = OFF;
					packet1 = TRANSMIT;
					TimerDisable(TIMER0_BASE, TIMER_A);
				}
			}
		}
	} else if(sample_mode == ETS) {
		if(Timerstate1 == ON) {
			ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
			adc1[sampleCH1] = ADC3Value[0]/16;
			sampleCH1++;
			if(sampleCH1 == 514) {
				Timerstate1 = OFF;
				packet1ETS = TRANSMIT;
				TimerDisable(TIMER0_BASE, TIMER_A);
			}
		}
	}
}


void Timer1IntHandler(void)
{
	TimerIntClear(TIMER1_BASE, TIMER_TIMA_TIMEOUT);

	if(sample_mode == RTS) {
		if(edge2 == DISABLE || trigger2 == OUTOFRANGE) {
			ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
			adc2[sampleCH2] = ADC2Value[0]/16;
			sampleCH2++;

			if(sampleCH2 == 514) {
				packet2 = TRANSMIT;
				TimerDisable(TIMER1_BASE, TIMER_A);
			}
		} else if(trigger2 != OUTOFRANGE) {
			if(Intstate2 == ON) {
				ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
				adc2[sampleCH2] = ADC2Value[0]/16;
				sampleCH2++;
				if(sampleCH2 == 514) {
					Intstate2 = OFF;
					packet2 = TRANSMIT;
					TimerDisable(TIMER1_BASE, TIMER_A);
				}
			}
		}
	} else if(sample_mode == ETS) {
		if(Timerstate2 == ON) {
			ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
			adc2[sampleCH2] = ADC2Value[0]/16;
			sampleCH2++;
			if(sampleCH2 == 514) {
				Timerstate2 = OFF;
				packet2ETS = TRANSMIT;
				TimerDisable(TIMER1_BASE, TIMER_A);
			}
		}
	}
}
