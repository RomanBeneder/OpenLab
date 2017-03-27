/*
 * TIVA_ADC_driver.c
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */


#include "TIVA_ADC_driver.h"

uint32_t ADC3Value[8];
uint32_t ADC2Value[8];

uint16_t _TIVA_ADC_Configuration(void)
{
	_TIVA_ADC_PIN_Configuration();
	_TIVA_ADC_Sequence_Configuration();
	_TIVA_ADC_RTM_Sequence_Configuration();
//	_TIVA_ADC_RTM_Sequence_Configuration(1);
//	_TIVA_ADC_Interrupt_Configuration();
//	_TIVA_ADC_ETS_Interrupt_Configuration();
	_TIVA_ADC_Timer_Configuration();

	return SUCCESS;
}

uint16_t _TIVA_ADC_PIN_Configuration(void)
{
	// *** Peripheral Enable
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOE);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOD);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_ADC0);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_ADC1);

	SysCtlDelay(10);
	// *** GPIO
	GPIOPinTypeADC(GPIO_PORTE_BASE, GPIO_PIN_0 | GPIO_PIN_4);
	GPIOPinTypeADC(GPIO_PORTD_BASE, GPIO_PIN_0 | GPIO_PIN_1);

	ADCClockConfigSet(ADC0_BASE, ADC_CLOCK_SRC_PLL | ADC_CLOCK_RATE_FULL, 15);
//	ADCClockConfigSet(ADC1_BASE, ADC_CLOCK_SRC_PLL | ADC_CLOCK_RATE_FULL, 15);

	return SUCCESS;
}

uint16_t _TIVA_ADC_Sequence_Configuration(void)
{
	ADCSequenceStepConfigure(ADC0_BASE,0,0,ADC_CTL_CH3 | ADC_CTL_END);
	ADCSequenceStepConfigure(ADC1_BASE,0,0,ADC_CTL_CH9 | ADC_CTL_END);

	ADCSequenceStepConfigure(ADC0_BASE, 3, 0, ADC_CTL_CH15); // | ADC_CTL_END);
	ADCSequenceDisable(ADC0_BASE,3);
	ADCSequenceConfigure(ADC0_BASE,3,ADC_TRIGGER_TIMER,0);
	ADCSequenceEnable(ADC0_BASE,3);



//	ADCSequenceStepConfigure(ADC0_BASE,2,0,ADC_CTL_CH14 | ADC_CTL_IE | ADC_CTL_END);
//	ADCSequenceStepConfigure(ADC1_BASE,1,0,ADC_CTL_CH3 | ADC_CTL_IE | ADC_CTL_END);
//
//	ADCSequenceDisable(ADC0_BASE,2);
//	ADCSequenceConfigure(ADC0_BASE,2,ADC_TRIGGER_PROCESSOR,0);
//	ADCSequenceEnable(ADC0_BASE,2);
//
//	ADCSequenceDisable(ADC1_BASE,1);
//	ADCSequenceConfigure(ADC1_BASE,1,ADC_TRIGGER_PROCESSOR,0);
//	ADCSequenceEnable(ADC1_BASE,1);

	return SUCCESS;
}

uint16_t _TIVA_ADC_Interrupt_Configuration(void)
{
//	ADCIntRegister(ADC0_BASE,2,&ADC0SS2IntHandler);
//	IntEnable(INT_ADC0SS2);
//	ADCIntEnable(ADC0_BASE,2);

//	ADCIntRegister(ADC1_BASE,1,&ADC1SS1IntHandler);
//	IntEnable(INT_ADC1SS1);
//	ADCIntEnable(ADC1_BASE,1);

//	IntMasterEnable();

	return SUCCESS;
}

void ADC0SS2IntHandler(void)
{
//	static uint16_t j = 0;
//	ADCIntClear(ADC0_BASE,2);
//
//	ADCSequenceDataGet(ADC0_BASE,2,ADC3Value); // Get Data from ADC and store it in ADC0Value
//	adc1[j] = ADC3Value[0]/16;

}

//void ADC1SS1IntHandler(void)
//{
//	static double yOld = 0, yNew = 0;
//
//    ADCSequenceDataGet(ADC1_BASE,1,ADC2Value);
////    adcLow2 = ADC2Value[0]/16;
//    yNew = 0.875*yOld + 0.125*ADC2Value[0];
//    if((yNew > yOld) && yNew > 3276) {
//    	if(state3 == 3) {
////			ADCIntDisable(ADC1_BASE,1);
//			yNew = 0;
//			yOld = 0;
//			TimerEnable(TIMER1_BASE, TIMER_A);
//			state3 = 2;
//    	}
//    }
//    yOld = yNew;
//
//    ADCIntClear(ADC1_BASE,1);
//}
