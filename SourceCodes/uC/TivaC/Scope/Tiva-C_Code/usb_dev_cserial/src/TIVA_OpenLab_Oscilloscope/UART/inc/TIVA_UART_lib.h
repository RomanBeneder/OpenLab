/*
 * TIVA_UART_lib.h
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

#ifndef TIVA_UART_LIB_H_
#define TIVA_UART_LIB_H_
/***************************************************************************** LIBRARIES */
#include <stdint.h>
#include <stdbool.h>
#include <string.h>
#include <stdio.h>
#include "inc/hw_memmap.h"
#include "driverlib/gpio.h"
#include "driverlib/pin_map.h"
#include "driverlib/sysctl.h"
#include "driverlib/uart.h"
#include "driverlib/interrupt.h"
#include "tm4c1294ncpdt.h"
/***************************************************************************** DEFINES */
#define UART2 2
#define UART3 3
#define SUCCESS 0

#define MAX_RX_ENTRIES	1023

#define ERR_INVALID_UART_NUMBER 300
#define ERR_OUT_OF_MEMORY 301
/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_UART_Configuration(int UART_num);
uint16_t _TIVA_UART_PIN_Configuration(void);
uint16_t _TIVA_UART_Settings(void);
uint16_t _TIVA_UART_FIFO_Configuration(void);
uint16_t _TIVA_UART_Enable_uDMA(void);
uint16_t _TIVA_UART_Interrupt_Configuration(void);
uint16_t _TIVA_UART_printf(const char *fmt,...);
uint16_t _TIVA_UART_Send(const char *send_string, ...);

#endif /* TIVA_UART_LIB_H_ */

/* EOF */
