/*
 * TIVA_UART_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

#ifndef TIVA_ADC_DRIVER_H_
#define TIVA_ADC_DRIVER_H_
/***************************************************************************** LIBRARIES */
#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include "inc/hw_memmap.h"
#include "driverlib/gpio.h"
#include "driverlib/sysctl.h"
#include "driverlib/adc.h"
#include "driverlib/interrupt.h"
#include "driverlib/timer.h"
#include "tm4c1294ncpdt.h"
#include "global.h"
#include "Protokoll.h"
/***************************************************************************** DEFINES */
#define SUCCESS 0

#define ADC_ON 0
#define TIMER_ON 1
/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_ADC_Configuration(void);
uint16_t _TIVA_ADC_RTM_Configuration(void);
uint16_t _TIVA_ADC_ETS_Configuration(void);
uint16_t _TIVA_ADC_PIN_Configuration(void);
uint16_t _TIVA_ADC_CLOCK_Configuration(void);
uint16_t _TIVA_ADC_Sequence_Configuration(void);
uint8_t _TIVA_ADC_RTM_Sequence_Configuration(void);
//uint8_t _TIVA_ADC_ETS_Sequence_Configuration(uint8_t channelNum);
uint16_t _TIVA_ADC_Interrupt_Configuration(void);
//uint16_t _TIVA_ADC_ETS_Interrupt_Configuration(void);
uint16_t _TIVA_ADC_Timer_Configuration(void);

uint16_t _TIVA_ADC_ETS_Configuration(void);
uint16_t _TIVA_ADC_ETS_PIN_Configuration(void);
uint16_t _TIVA_ADC_ETS_CLOCK_Configuration(void);
uint16_t _TIVA_ADC_ETS_Sequence_Configuration(void);
uint16_t _TIVA_ADC_ETS_Interrupt_Configuration(void);
void _master_function(void);

void ADC0SS1IntHandler(void);
void ADC1SS2IntHandler(void);
void ADC0SS2IntHandler(void);
//void ADC1SS1IntHandler(void);

void Timer0IntHandler(void);
void Timer1IntHandler(void);

//extern uint8_t adc1[65535];
//extern uint8_t adc2[65535];

#endif /* TIVA_ADC_LIB_H_ */


/* EOF */




