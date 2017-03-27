/*
 * TIVA_PWM_driver.h
 *
 *  Created on: 23.09.2016
 *      Author: HP
 */

#ifndef TIVA_PWM_DRIVER_H_
#define TIVA_PWM_DRIVER_H_


#include <stdbool.h>
#include <stdint.h>
#include "inc/hw_types.h"
#include "inc/hw_gpio.h"
#include "inc/hw_memmap.h"
#include "driverlib/gpio.h"
#include "driverlib/pin_map.h"
#include "driverlib/pwm.h"
#include "driverlib/sysctl.h"
#include "driverlib/uart.h"
#include "utils/uartstdio.h"
#include "drivers/pinout.h"
#include "driverlib/rom_map.h"

#define SUCCESS 0

uint16_t _TIVA_PWM_Configuration(void);



#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PWM_INC_TIVA_PWM_DRIVER_H_ */
