/**
 * 	@file 		uart.c
 * 	@brief		UART source file
 * 	@author		Christian Matar
 */

#include "uart.h"


void UART_init(LPC_UART_TypeDef *uart)
{
	uint32_t dll, Fdiv;

	// UART pin initialization
	LPC_IOCON->PIO1_6 &= ~(1<<1 | 1<<2);
	LPC_IOCON->PIO1_6 |= 1<<0;
	LPC_IOCON->PIO1_7 &= ~(1<<1 | 1<<2);
	LPC_IOCON->PIO1_7 |= 1<<0;

	// enable UART
	LPC_SYSCON->SYSAHBCLKCTRL |= 1<<12;
	LPC_SYSCON->UARTCLKDIV |= 1<<0;

	Fdiv = LPC_SYSCON->UARTCLKDIV;
	dll = (((SystemCoreClock/LPC_SYSCON->SYSAHBCLKDIV)/Fdiv)/16)/BAUDRATE ;

	uart->FDR |= 1<<4;

	uart->LCR = 0x80 | 0x3;
	uart->DLL = dll;
	uart->DLM = dll >> 8;
	uart->LCR = 0x00 | 0x3;

	uart->FCR |= 1<<0;
	uart->FCR |= 1<<1 | 1<<2; 			// reset RX and TX FIFO
	uart->FCR &= ~(1<<6 | 1<<7);

	LPC_UART->IER |= 1<<0 | 1<<1;
	NVIC_EnableIRQ(UART_IRQn);
}

void UART_putchar(uint8_t c)
{
	LPC_UART->THR = c;
}

void UART_putstr(uint8_t *str, uint8_t size)
{
	int i;
	for(i=0; i<size; i++)
		UART_putchar(str[i]);
}

void UART_getchar(uint8_t *c)
{
#if LOOPBACK
	UART_putchar(*c);
#endif

	static uint8_t buf[MAX_BUF_SIZE], i=0;

	if(i<MAX_BUF_SIZE)
	{
		buf[i] = *c;
		i++;
	}
	else
		i=0;

	if(*c == '\n' || *c == '\r')
	{
		parseCmd(buf);
		memset(buf, 0, MAX_BUF_SIZE);
		i=0;
	}
}

void UART_getstr(char *str)
{
	int i;
	for(i=0; i<strlen(str); i++)
		UART_getchar(&str[i]);
}

/**
 * 	@brief	Function UART_IRQHandler() implements the UART interrupt handler.
 * 			Data is here processed.
 */
void UART_IRQHandler()
{
	// check if data is available
	if((LPC_UART->IIR & 0x4) || (LPC_UART->IIR & 0XC))
	{
		uint8_t c;
		while(LPC_UART->LSR & 0x01)
		{
			c = LPC_UART->RBR;
			UART_getchar(&c);
		}
	}
}
