/*
 * TIVA_PWM_lib.h
 *
 *  Created on: 23.09.2016
 *      Author: HP
 */

#ifndef TIVA_PWM_LIB_H_
#define TIVA_PWM_LIB_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <stdbool.h>
#include "driverlib/pwm.h"
#include "Protokoll.h"

#define ADCRANGE	255
#define TICKS	2000
#define CHANNEL0		0
#define CHANNEL1		1

//uint16_t SetPWMValue(struct values *ptr);
struct values SetPWMValue(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PWM_INC_TIVA_PWM_LIB_H_ */
