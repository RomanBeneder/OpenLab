/*
 * main.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

#ifndef OPENLAB_EMBSYS_SCOPE_H_
#define OPENLAB_EMBSYS_SCOPE_H_
/***************************************************************************** LIBRARIES */
//#include "TIVA_UART_lib.h"
//#include "TIVA_ADC_driver.h"
//#include "TIVA_USB_lib.h"
//#include "TIVA_uDMA_lib.h"
#include "TIVA_ADC_driver.h"
#include "TIVA_USB_lib.h"
#include "TIVA_GPIO_driver.h"
#include "TIVA_PWM_driver.h"
#include "TIVA_Timer_driver.h"
/***************************************************************************** MACROS */
#define PIN_ASSIGNMENT_FOR_ANALYSES (1UL)
/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_Oscilloscope_Configuration(void);

#endif /* OPENLAB_EMBSYS_SCOPE_H_ */

/* EOF */
