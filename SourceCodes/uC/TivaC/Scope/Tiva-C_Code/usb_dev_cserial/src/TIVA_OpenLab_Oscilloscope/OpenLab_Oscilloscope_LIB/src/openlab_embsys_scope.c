/*
 * main.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */
/***************************************************************************** LIBRARIES */
#include "openlab_embsys_scope.h"
/***************************************************************************** FUNCTION */
uint16_t _TIVA_Oscilloscope_Configuration(void)
{
	IntMasterDisable();

#if(PIN_ASSIGNMENT_FOR_ANALYSES)
	SYSCTL_RCGCGPIO_R = SYSCTL_RCGCGPIO_R12;
    GPIO_PORTN_DIR_R = 0x01;
    GPIO_PORTN_DEN_R = 0x01;
#endif
	_TIVA_USB_Configuration();
	_TIVA_GPIO_Configuration();
	_TIVA_PWM_Configuration();
	_TIVA_Timer_Configuration();
	_TIVA_ADC_Configuration();

	//Configure UART settings
	//_TIVA_UART_Configuration(UART2);

	//Configure uDMA settings
	//_TIVA_uDMA_Configuration();

	//Configure ADC settings
//	_TIVA_ADC_Configuration();

	//Configure USB settings
//	_TIVA_USB_Configuration();

//	IntMasterEnable();

	return SUCCESS;
}

/* EOF */
