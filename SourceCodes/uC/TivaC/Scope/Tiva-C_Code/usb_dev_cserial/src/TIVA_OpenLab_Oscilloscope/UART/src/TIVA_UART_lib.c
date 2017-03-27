/*
 * TIVA_UART_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */
/***************************************************************************** LIBRARIES*/
#include "TIVA_UART_lib.h"
/***************************************************************************** RxTx BUFFER */
/*static*/ unsigned char UART2_RX_buffer[MAX_RX_ENTRIES] = {};
/***************************************************************************** FUNCTIONS */

uint16_t _TIVA_UART_Configuration(int UART_num)
{
	switch(UART_num)
	{
	case UART2:
		_TIVA_UART_PIN_Configuration();
		_TIVA_UART_Settings();
		_TIVA_UART_FIFO_Configuration();
		_TIVA_UART_Enable_uDMA();
		_TIVA_UART_Interrupt_Configuration();
		break;

	default:
		return ERR_INVALID_UART_NUMBER;
	}
	return SUCCESS;
}

uint16_t _TIVA_UART_PIN_Configuration(void)
{
	SysCtlPeripheralEnable(SYSCTL_PERIPH_UART2);
	SysCtlDelay(3);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_GPIOA);
	SysCtlDelay(3);

	//Configure the GPIO pin muxing for the UART function.
	GPIOPinConfigure(GPIO_PA6_U2RX);
	GPIOPinConfigure(GPIO_PA7_U2TX);

	//Configured for use as a peripheral function
	GPIOPinTypeUART(GPIO_PORTA_BASE, GPIO_PIN_6 | GPIO_PIN_7);

	return SUCCESS;
}

uint16_t _TIVA_UART_Settings(void)
{
	//Clock for UART: 120MHz
	//Configure the UART for 115,200, 8-N-1 operation.
	UARTConfigSetExpClk(UART2_BASE, 120000000, 2000000,
					   (UART_CONFIG_WLEN_8 |
						UART_CONFIG_STOP_ONE |
					    UART_CONFIG_PAR_NONE));

	return SUCCESS;
}

uint16_t _TIVA_UART_FIFO_Configuration(void)
{
	UARTFIFOEnable(UART2_BASE);
	UARTFIFOLevelSet(UART2_BASE, UART_FIFO_TX1_8, UART_FIFO_RX1_8);

	return SUCCESS;
}

uint16_t _TIVA_UART_Enable_uDMA(void)
{
	UARTEnable(UART2_BASE);
	UARTDMAEnable(UART2_BASE, UART_DMA_TX);

	return SUCCESS;
}


uint16_t _TIVA_UART_Interrupt_Configuration(void)
{
	UARTIntClear(UART2_BASE, UART_INT_RX);
	UARTIntEnable(UART2_BASE, UART_INT_RX);
	IntEnable(INT_UART2);

	return SUCCESS;
}

uint16_t _TIVA_UART_printf(const char *fmt,...)
{
	va_list arg_ptr;
	char cache_buffer[256] = {};

	if(fmt == NULL)
		return ERR_OUT_OF_MEMORY;

	va_start(arg_ptr, fmt);
	vsprintf(cache_buffer, fmt, arg_ptr);
	va_end(arg_ptr);

	_TIVA_UART_Send(cache_buffer);

	return SUCCESS;
}

uint16_t _TIVA_UART_Send(const char *send_string, ...)
{
	//bool iret = false;
	uint32_t string_length = 0;

	if(send_string == NULL)
		return ERR_OUT_OF_MEMORY;

	string_length = strlen(send_string);

    //Loop until all characters are sent.
    while(string_length--)
    {
    	//if(!(iret = UARTBusy(UART2_BASE)) )
    	//{
            // Write the next character to the UART
            UARTCharPut(UART2_BASE, *send_string++);
    	//}
    }

    return SUCCESS;
}

void UART2_RxTx_ISR (void)
{
	static uint16_t i = 0;
	uint32_t UART2_status = 0;

	UARTIntClear(UART2_BASE, UART2_status);

	UART2_status = UARTIntStatus(UART2_BASE, true);

	switch(UART2_status)
	{
	case UART_INT_RX:

		while(UARTCharsAvail(UART2_BASE) )
		{
			UART2_RX_buffer[i] = UARTCharGet(UART2_BASE);

			if(i == MAX_RX_ENTRIES)
				i = 0;

			else
				i++;
		}

		break;

	default:
		break;
	}

	return;
}

/* EOF */
