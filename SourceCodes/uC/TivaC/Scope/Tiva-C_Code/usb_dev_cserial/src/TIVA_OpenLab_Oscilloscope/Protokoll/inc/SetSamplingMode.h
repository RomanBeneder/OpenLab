/*
 * SetSamplingMode.h
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#ifndef SETSAMPLINGMODE_H_
#define SETSAMPLINGMODE_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <stdbool.h>
#include "driverlib/adc.h"
#include "driverlib/interrupt.h"
#include "driverlib/timer.h"
#include "Protokoll.h"
#include "CRC16.h"
#include "TIVA_uDMA_lib.h"
#include "TIVA_ADC_driver.h"


#define RTS 0
#define SETS 1
#define RETS 2

struct values SetSamplingMode(struct values *ptr);
struct values PayloadCrcSSM(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SETSAMPLINGMODE_H_ */
