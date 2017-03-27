/*
 * TIVA_UART_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

#ifndef TIVA_ADC_LIB_H_
#define TIVA_ADC_LIB_H_
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
#include "Protokoll.h"
#include "global.h"
/***************************************************************************** DEFINES */
#define SUCCESS 		0
#define ERROR_CHANNEL	1
#define ERROR_SAMPLE	2
#define ERROR_COUPLING	3
#define CHANNEL0		0
#define CHANNEL1		1
#define TIMER 			60000000
/***************************************************************************** FUNCTION PROTOTYPES */
struct values SetTimeBase(struct values *ptr);
struct values SetChannelSettings(struct values *ptr);

#endif /* TIVA_ADC_LIB_H_ */


/* EOF */
