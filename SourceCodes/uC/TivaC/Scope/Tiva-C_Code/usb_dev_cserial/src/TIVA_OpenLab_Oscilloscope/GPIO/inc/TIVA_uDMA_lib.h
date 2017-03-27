/*
 * TIVA_uDMA_lib.h
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

#ifndef TIVA_UDMA_LIB_H_
#define TIVA_UDMA_LIB_H_
/***************************************************************************** LIBRARIES*/
#include <stdint.h>
#include <stdbool.h>
#include "inc/hw_memmap.h"
#include "driverlib/sysctl.h"
#include "driverlib/udma.h"
#include "inc/hw_adc.h"
#include "driverlib/adc.h"
#include "driverlib/interrupt.h"
#include "tm4c1294ncpdt.h"

//#include "TIVA_UART_lib.h"
/***************************************************************************** DEFINES*/
#define SUCCESS 0

#define TRANSFER_SIZE_1024 1024
/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_uDMA_Configuration(void);
uint16_t _TIVA_uDMA_Peripheral_Configuration(void);
uint16_t _TIVA_uDMA_Control_Configuration(void);
uint16_t _TIVA_uDMA_ADC_Channel_Configuration(void);
uint16_t _TIVA_uDMA_Interrupt_Configuration(void);

void _TIVA_Reboot_Microcontroller(void);

#endif /* TIVA_UDMA_LIB_H_ */
