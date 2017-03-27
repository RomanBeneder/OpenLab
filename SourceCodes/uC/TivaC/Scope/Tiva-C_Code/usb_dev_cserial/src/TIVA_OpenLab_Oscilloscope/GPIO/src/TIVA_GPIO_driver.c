/*
 * TIVA_GPIO_lib.c
 *
 *  Created on: 29.04.2016
 *      Author: Susanne Schierer
 */

/***************************************************************************** LIBRARIES*/
#include "TIVA_GPIO_driver.h"
/***************************************************************************** FUNCTIONS */
//uint32_t ADC3Value[8];
//uint32_t ADC2Value[8];

uint16_t _TIVA_GPIO_Configuration(void)
{
	_TIVA_GPIO_PIN_Configuration();
	_TIVA_GPIO_Interrupt_Configuration();
	return SUCCESS;
}

uint16_t _TIVA_GPIO_PIN_Configuration(void)
{
	//
	// Enable the GPIO module.
	//
//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPION);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPION);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOJ);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOJ);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOF);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOF);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOM);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOM);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOE);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOE);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOH);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOH);
	SysCtlDelay(1);

//	SysCtlPeripheralReset(SYSCTL_PERIPH_GPIOL);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOL);
	SysCtlDelay(1);

	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOK);
	SysCtlDelay(1);

	//
	// Configure PN1/PN0 (LED1/LED2) as an output.
	// Configure PF4/PF0 (LED3/LED4) as an output.
	// Configure PJ0/PJ1 (Button1/Button2) as an output.
	// Configure PM4/PM5 (ADC0/ADC1 testen) as an output.
	// Configure PE0/PE1 (ADC0/ADC1) as an input.
	// Configure PL0/PL1/PL2 (AMPL CH0) as an output.
	// Configure PL1/PH2/PH3 (AMPL CH1) as an output.
	//
	GPIOPinTypeGPIOOutput(GPIO_PORTN_BASE, GPIO_PIN_1);
	GPIOPinTypeGPIOOutput(GPIO_PORTN_BASE, GPIO_PIN_0);
	GPIOPinTypeGPIOOutput(GPIO_PORTF_BASE, GPIO_PIN_4);
	GPIOPinTypeGPIOOutput(GPIO_PORTF_BASE, GPIO_PIN_0);
	GPIOPinTypeGPIOInput(GPIO_PORTJ_BASE, GPIO_PIN_0);
	GPIOPinTypeGPIOInput(GPIO_PORTJ_BASE, GPIO_PIN_1);
	GPIOPinTypeGPIOInput(GPIO_PORTL_BASE, GPIO_PIN_4);
	GPIOPinTypeGPIOInput(GPIO_PORTL_BASE, GPIO_PIN_5);
	GPIOPinTypeGPIOOutput(GPIO_PORTM_BASE, GPIO_PIN_4);
	GPIOPinTypeGPIOOutput(GPIO_PORTM_BASE, GPIO_PIN_5);
	GPIOPinTypeGPIOInput(GPIO_PORTE_BASE, GPIO_PIN_0);
	GPIOPinTypeGPIOInput(GPIO_PORTE_BASE, GPIO_PIN_1);
	GPIOPinTypeGPIOOutput(GPIO_PORTL_BASE, GPIO_PIN_0);
	GPIOPinTypeGPIOOutput(GPIO_PORTL_BASE, GPIO_PIN_1);
	GPIOPinTypeGPIOOutput(GPIO_PORTL_BASE, GPIO_PIN_2);
	GPIOPinTypeGPIOOutput(GPIO_PORTL_BASE, GPIO_PIN_3);
	GPIOPinTypeGPIOOutput(GPIO_PORTH_BASE, GPIO_PIN_2);
	GPIOPinTypeGPIOOutput(GPIO_PORTH_BASE, GPIO_PIN_3);
	GPIOPinTypeGPIOOutput(GPIO_PORTK_BASE, GPIO_PIN_2);
	GPIOPinTypeGPIOOutput(GPIO_PORTK_BASE, GPIO_PIN_3);
	return SUCCESS;
}

uint16_t _TIVA_GPIO_Interrupt_Configuration(void)
{
	SysCtlPeripheralDisable(SYSCTL_PERIPH_GPIOL);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOL);
	SysCtlDelay(1);

	GPIOPadConfigSet(GPIO_PORTL_BASE, GPIO_PIN_4, GPIO_STRENGTH_2MA,GPIO_PIN_TYPE_STD_WPU);
	GPIOPadConfigSet(GPIO_PORTL_BASE, GPIO_PIN_5, GPIO_STRENGTH_2MA,GPIO_PIN_TYPE_STD_WPU);
	GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_4, GPIO_RISING_EDGE);
	GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_5, GPIO_RISING_EDGE);
	GPIOIntRegister(GPIO_PORTL_BASE, PortLIntHandler);
//	GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
//	GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
	IntEnable(INT_GPIOL);

	return SUCCESS;
}

void PortLIntHandler(void)
{
	uint32_t ADC3Value[8];
	uint32_t ADC2Value[8];
	uint8_t read;
	unsigned long Light_sensor_status = 0;

	Light_sensor_status = GPIOIntStatus(GPIO_PORTL_BASE, true);

	if(Light_sensor_status & GPIO_PIN_4) {
		if(edge1 != BOTH_EDGES) {
			read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
			if(read == edge1) {
				read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
				if(read == edge1) {
					read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
					if(read == edge1) {
						read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
						if(read == edge1)  {
							read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
							if(read == edge1) {
								read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
								if(read == edge1) {
									read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
									if(read == edge1) {
//										read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_4);
//										if(read == edge1) {
											sampleCH1 = 0;
											if(sample_mode == RTS) {
												Intstate1 = ON;
												ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
												adc1[0] = ADC3Value[0]/16;
												sampleCH1++;
												GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
												GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);
											} else if(sample_mode == ETS) {
												if(actualRounds == 0) {
													Timerstate1 = ON;
	//												TimerEnable(TIMER2_BASE, TIMER_A);
	//												uDMAChannelEnable(UDMA_SEC_CHANNEL_ADC13);
	//												ADCIntEnableEx(ADC1_BASE, ADC_INT_DMA_SS3);
	//												IntEnable(INT_ADC1SS3);
													ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
													adc1[0] = ADC3Value[0]/16;
													sampleCH1++;
												} else
													GPIOstate1 = ON;
	//												TimerEnable(TIMER2_BASE, TIMER_A);

												GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
												GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);

											}
//										}
									}
								}
							}
						}
					}
				}
			}
		} else {
			sampleCH1 = 0;
			if(sample_mode == RTS) {
				Intstate1 = ON;
				ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
				adc1[0] = ADC3Value[0]/16;
				sampleCH1++;
				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
				GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);
			} else if(sample_mode == ETS) {
				if(actualRounds == 0) {
					Timerstate1 = ON;
					ADCSequenceDataGet(ADC0_BASE,0,ADC3Value);
					adc1[0] = ADC3Value[0]/16;
					sampleCH1++;
				} else
					GPIOstate1 = ON;

				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
				GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);

			}
		}
	} else if(Light_sensor_status & GPIO_INT_PIN_5) {
		if(edge2 != BOTH_EDGES) {
	//		read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
	//		//		if(read != 0) {
	//				if(read == edge2) {
						read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
				//		if(read != 0) {
						if(read == edge2) {
							read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
					//		if(read != 0) {
							if(read == edge2) {
								read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
						//		if(read != 0) {
								if(read == edge2)  {
			read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
			if(read == edge2) {
				read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
				if(read == edge2) {
					read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
					if(read == edge2) {
						read = GPIOPinRead(GPIO_PORTL_BASE, GPIO_PIN_5);
						if(read == edge2) {
							sampleCH2 = 0;
							if(sample_mode == RTS) {
								Intstate2 = ON;
								ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
								adc2[0] = ADC2Value[0]/16;
								sampleCH2++;
								GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
								GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);
							} else if(sample_mode == ETS) {
								if(actualRounds == 0) {
									Timerstate2 = ON;
									ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
									adc2[0] = ADC2Value[0]/16;
									sampleCH2++;
								} else
									GPIOstate2 = ON;

								GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
								GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);

							}
						}
					}
				}
			}
								}
							}
						}
	//				}
		} else {
			sampleCH2 = 0;
			if(sample_mode == RTS) {
				Intstate2 = ON;
				ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
				adc2[0] = ADC2Value[0]/16;
				sampleCH2++;
				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
				GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);
			} else if(sample_mode == ETS) {
				if(actualRounds == 0) {
					Timerstate2 = ON;
					ADCSequenceDataGet(ADC1_BASE,0,ADC2Value);
					adc2[0] = ADC2Value[0]/16;
					sampleCH2++;
				} else
					GPIOstate2 = ON;

				GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
				GPIOIntClear(GPIO_PORTL_BASE, GPIO_INT_PIN_4 | GPIO_INT_PIN_5);

			}
		}
	}
}
