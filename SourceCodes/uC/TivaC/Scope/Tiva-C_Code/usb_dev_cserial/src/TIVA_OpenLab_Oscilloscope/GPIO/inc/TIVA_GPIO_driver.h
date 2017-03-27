/*
 * TIVA_GPIO_lib.h
 *
 *  Created on: 29.04.2016
 *      Author: HP
 */

#ifndef TIVA_GPIO_DRIVER_H_
#define TIVA_GPIO_DRIVER_H_

/***************************************************************************** LIBRARIES */
#include <stdbool.h>
#include <stdint.h>
#include <stdio.h>
#include "inc/hw_memmap.h"
#include "inc/hw_types.h"
#include "driverlib/pin_map.h"
#include "driverlib/debug.h"
#include "driverlib/gpio.h"
#include "driverlib/sysctl.h"
#include "driverlib/interrupt.h"
#include "driverlib/timer.h"
#include "tm4c1294ncpdt.h"
#include "Protokoll.h"

/***************************************************************************** DEFINES */
#define SUCCESS 0

/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_GPIO_Configuration(void);
uint16_t _TIVA_GPIO_PIN_Configuration(void);
uint16_t _TIVA_GPIO_Interrupt_Configuration(void);

void PortJIntHandler(void);
void PortLIntHandler(void);
void PortFIntHandler(void);

#endif /* TIVA_GPIO_LIB_H_ */
