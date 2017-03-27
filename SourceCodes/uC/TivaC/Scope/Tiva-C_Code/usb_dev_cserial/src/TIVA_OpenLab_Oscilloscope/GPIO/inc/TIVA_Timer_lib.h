/*
 * TIVA_Timer_lib.h
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#ifndef TIVA_TIMER_LIB_H_
#define TIVA_TIMER_LIB_H_

#include <stdint.h>
#include <stdbool.h>
#include <stdio.h>
#include "inc/hw_memmap.h"
#include "driverlib/interrupt.h"
#include "driverlib/timer.h"
#include "Protokoll.h"

#define SUCCESS 		0
#define CHANNEL0		0
#define CHANNEL1		1
#define TIMER 			60000000

struct values ETS_Packages(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_TIMER_INC_TIVA_TIMER_LIB_H_ */
