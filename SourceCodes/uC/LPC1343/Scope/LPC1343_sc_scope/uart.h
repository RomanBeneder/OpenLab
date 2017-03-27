/**
 * 	@file 		uart.h
 * 	@brief		UART source file
 * 	@author		Christian Matar
 */

#ifndef UART_H_
#define UART_H_

#include "LPC13xx.h"
#include <stdint.h>
#include <string.h>

#include "commands.h"

#define BAUDRATE 		9600
#define MAX_BUF_SIZE	32

/**
 * 	@brief	Set to 1 loopback from RX to TX is desired.
 */
#define LOOPBACK 0

/**
 * 	@brief	Function UART_init() initializes the UART.
 */
void UART_init(LPC_UART_TypeDef *uart);

/**
 * 	@brief	Function UART_putchar() puts a character on the UART.
 * 	@param	c the character, which shall be transmitted.
 */
void UART_putchar(uint8_t c);

/**
 * 	@brief	Function UART_putstr() puts a string on the UART.
 * 	@param	str the string, which shall be transmitted.
 */
void UART_putstr(uint8_t *str, uint8_t size);

/**
 * 	@brief	Function UART_getchar() reads a character from the UART.
 * 	@param	c the character, which was received.
 */
void UART_getchar(uint8_t *c);

/**
 * 	@brief	Function UART_getstring() reads a string from the UART.
 *	@param	str the received str
 */
void UART_getstr(char *str);


#endif /* UART_H_ */
