///*
// * TIVA_ADC_ETS_driver.c
// *
// *  Created on: 01.12.2016
// *      Author: HP
// */
//
//#include "TIVA_ADC_driver.h"
//
//void ADC1SS1IntHandler(void);
//
//uint32_t ADC3Value[8];
//uint32_t ADC2Value[8];
//
////uint16_t _TIVA_ADC_ETS_Configuration(void)
////{
////	_TIVA_ADC_ETS_Sequence_Configuration();
//////	_TIVA_ADC_Interrupt_Configuration();
////
////	return SUCCESS;
////}
//
//uint8_t _TIVA_ADC_ETS_Sequence_Configuration(uint8_t channelNum)
//{
////	if(channelNum == CH1) {
////		ADCSequenceDisable(ADC1_BASE,1);
////		ADCSequenceConfigure(ADC1_BASE,1,ADC_TRIGGER_PROCESSOR,0);
////		//	ADCSequenceStepConfigure(ADC0_BASE,1,0,ADC_CTL_CH3 | ADC_CTL_END);
////		ADCSequenceEnable(ADC1_BASE,1);
////	} else if(channelNum == CH2) {
////		ADCSequenceDisable(ADC1_BASE,2);
////		ADCSequenceConfigure(ADC1_BASE,2,ADC_TRIGGER_PROCESSOR,0);
////		//	ADCSequenceStepConfigure(ADC1_BASE,2,0,ADC_CTL_CH9 | ADC_CTL_END);
////		ADCSequenceEnable(ADC1_BASE,2);
////	}
//
//	return SUCCESS;
//}
//
//uint16_t _TIVA_ADC_ETS_Interrupt_Configuration(void)
//{
////	ADCIntRegister(ADC1_BASE,1,&ADC1SS1IntHandler);
////	IntEnable(INT_ADC1SS1);
////	ADCIntEnable(ADC1_BASE,1);
//
////	ADCIntRegister(ADC0_BASE,2,&ADC1SS2IntHandler);
////	IntEnable(INT_ADC0SS2);
////	ADCIntEnable(ADC1_BASE,2);
//
//	IntMasterEnable();
//
//	return SUCCESS;
//}
//
//void ADC1SS1IntHandler(void)
//{
////	static uint8_t samples = 0, rounds = 0;
////
////	if(state1 == ADC_ON) {
////		ADCIntClear(ADC0_BASE,1);
////		ADCSequenceDataGet(ADC0_BASE,1,ADC3Value); // Get Data from ADC and store it in ADC0Value
////		adc1ETS[samples] = ADC3Value[0]/16;
////
////		if(samples == SETSsamples) {
////			samples = 0;
////			packet1ETS = TRANSMIT;
////			ADCIntDisable(ADC1_BASE,1);
////		} else {
////			samples++;
////
////		}
////
////		if(rounds == SETSrounds) {
////			rounds = 0;
////		} else {
////			rounds++;
////		}
////		state1 = TIMER_ON;
////	}
//
//
//}
//
////void ADC0SS2IntHandler(void)
////{
////	static uint8_t samples = 0, rounds = 0;
////
////    ADCIntClear(ADC1_BASE,2);
////    ADCSequenceDataGet(ADC1_BASE,2,ADC2Value);
////    adc1[samples] = ADC2Value[0]/16;
////
////    if(samples == SETSsamples) {
////    	samples = 0;
////    } else
////    	samples++;
////
////    if(rounds == SETSrounds) {
////    	rounds = 0;
////	} else
////	rounds++;
////}



/*
 * TIVA_UART_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */
/***************************************************************************** LIBRARIES*/
#include "TIVA_ADC_driver.h"
/***************************************************************************** FUNCTIONS */

uint16_t _TIVA_ADC_ETS_Configuration(void)
{
	_TIVA_ADC_ETS_PIN_Configuration();
	_TIVA_ADC_ETS_CLOCK_Configuration();
	_TIVA_ADC_ETS_Sequence_Configuration();
	_TIVA_ADC_ETS_Interrupt_Configuration();

	return SUCCESS;
}

uint16_t _TIVA_ADC_ETS_PIN_Configuration(void)
{
	SysCtlPeripheralReset(SYSCTL_PERIPH_ADC0);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_ADC0);

	SysCtlDelay(10);

	SysCtlPeripheralReset(SYSCTL_PERIPH_ADC1);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_ADC1);

	SysCtlDelay(10);

//	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOK);
//	GPIOPinTypeADC(GPIO_PORTK_BASE, GPIO_PIN_2 | GPIO_PIN_3);

	return SUCCESS;
}

uint16_t _TIVA_ADC_ETS_CLOCK_Configuration(void)
{
	//The main PLL output is used
	//All samples are provoked to the application
	//Clock Divider: 15 -> 480/15 = 32MHz (max. frq.)
	ADCClockConfigSet(ADC0_BASE, ADC_CLOCK_SRC_PLL | ADC_CLOCK_RATE_FULL, 15);
	SysCtlDelay(10);

	return SUCCESS;
}

uint16_t _TIVA_ADC_ETS_Sequence_Configuration(void)
{
	ADCSequenceDisable(ADC0_BASE, 0);
	ADCSequenceDisable(ADC1_BASE, 0);
	ADCSequenceDisable(ADC1_BASE, 2);

	//Sequencer 2, ADC_TRIGGER_ALWAYS, Priority: 0 (Highest)
	ADCSequenceConfigure(ADC1_BASE, 3, ADC_TRIGGER_ALWAYS, 0);

	//Sequencer 2, Selected channel of sequencer 2: 0 and 1 of 4
	//Used channel 19 & 18
    ADCSequenceStepConfigure(ADC1_BASE, 3, 0, ADC_CTL_CH3 | ADC_CTL_END);
//    ADCSequenceStepConfigure(ADC1_BASE, 2, 1, (ADC_CTL_CH18 | ADC_CTL_END) );

    //Enable DMA transfer
    ADCSequenceDMAEnable(ADC1_BASE, 3);

    ADCSequenceEnable(ADC1_BASE, 3);

	return SUCCESS;
}

uint16_t _TIVA_ADC_ETS_Interrupt_Configuration(void)
{
	ADCIntDisableEx(ADC1_BASE, ADC_INT_DMA_SS3);

	//ISR is generated whenever the DMA transfer is finished
//	ADCIntEnableEx(ADC1_BASE, ADC_INT_DMA_SS2);
//
//	IntEnable(INT_ADC1SS2);


	//ADCIntEnableEx(ADC1_BASE, ADC_INT_DMA_SS2);
	//ADCIntEnable(ADC1_BASE, 2);

	return SUCCESS;
}

//void ADC1_SEQ2_ISR(void)
//{
//	uint32_t pui32ADC0_2Value[4];
//
//	GPIO_PORTN_DATA_R |= 0x01;
//
//	ADCIntClear(ADC1_BASE, 2);
//
//	ADCSequenceDataGet(ADC1_BASE, 2, pui32ADC0_2Value);
//
//	GPIO_PORTN_DATA_R &= ~(0x01);
//
//	return;
//}


/* EOF */
