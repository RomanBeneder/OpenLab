/*
 * TIVA_Timer_driver.c
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#ifndef _TIVA_TIMER_DRIVER_C_
#define _TIVA_TIMER_DRIVER_C_

#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include "inc/hw_memmap.h"
#include "driverlib/gpio.h"
#include "driverlib/sysctl.h"
#include "driverlib/interrupt.h"
#include "driverlib/timer.h"
#include "driverlib/adc.h"
#include "driverlib/udma.h"
#include "tm4c1294ncpdt.h"
#include "global.h"
#include "Protokoll.h"

#define SUCCESS 0

#define ADC_ON 0
#define TIMER_ON 1

uint16_t _TIVA_Timer_Configuration(void);
void Timer2IntHandler(void);
void Timer3IntHandler(void);
void Timer4IntHandler(void);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_TIMER_INC_TIVA_TIMER_DRIVER_C_ */
