/*
 * TIVA_PWM_driver.c
 *
 *  Created on: 23.09.2016
 *      Author: HP
 */

#include "TIVA_PWM_driver.h"

uint16_t _TIVA_PWM_Configuration(void)
{

	uint32_t g_ui32SysClock = MAP_SysCtlClockFreqSet((SYSCTL_XTAL_25MHZ |
				SYSCTL_OSC_MAIN |
				SYSCTL_USE_PLL |
				SYSCTL_CFG_VCO_480), 120000000);


	//2. Enable the PWM Module in the System Control using
	//enable peripheral (port F)
//	SysCtlPeripheralReset(SYSCTL_PERIPH_PWM0);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_PWM0);
	SysCtlDelay(1);

	//configure pin as a PWM pin
	GPIOPinTypePWM(GPIO_PORTF_BASE, GPIO_PIN_0);
	GPIOPinConfigure(GPIO_PF0_M0PWM0);
	GPIOPinTypePWM(GPIO_PORTF_BASE, GPIO_PIN_1);
	GPIOPinConfigure(GPIO_PF1_M0PWM1);
	GPIOPinTypePWM(GPIO_PORTF_BASE, GPIO_PIN_2);
	GPIOPinConfigure(GPIO_PF2_M0PWM2);

	PWMClockSet(PWM0_BASE, PWM_SYSCLK_DIV_64);

	// Configure the PWM generator for count down mode with immediate updates
	// to the parameters.
	//
	PWMGenConfigure(PWM0_BASE, PWM_GEN_0, PWM_GEN_MODE_DOWN | PWM_GEN_MODE_NO_SYNC);
	PWMGenConfigure(PWM0_BASE, PWM_GEN_1, PWM_GEN_MODE_DOWN | PWM_GEN_MODE_NO_SYNC);
	PWMGenConfigure(PWM0_BASE, PWM_GEN_2, PWM_GEN_MODE_DOWN | PWM_GEN_MODE_NO_SYNC);

	//
	// Set the period. For a 7 KHz frequency, the period = 1/7,000, or 143
	// microseconds. For a 20 MHz clock, this translates to 200 clock ticks.
	// Use this value to set the period.
	//
	PWMGenPeriodSet(PWM0_BASE, PWM_GEN_0, 2000);
	PWMGenPeriodSet(PWM0_BASE, PWM_GEN_1, 2000);
	PWMGenPeriodSet(PWM0_BASE, PWM_GEN_2, 2000);

	//
	// Set the pulse width of PWM0 for a 25% duty cycle.
	//
	PWMPulseWidthSet(PWM0_BASE, PWM_OUT_0, 0);
	PWMPulseWidthSet(PWM0_BASE, PWM_OUT_1, 666);
	PWMPulseWidthSet(PWM0_BASE, PWM_OUT_2, 0);

	//
	// Enable the outputs.
	//
	PWMOutputState(PWM0_BASE, PWM_OUT_0_BIT, true);
	PWMOutputState(PWM0_BASE, PWM_OUT_1_BIT, true);
	PWMOutputState(PWM0_BASE, PWM_OUT_2_BIT, true);

	//
	// Start the timers in generator 0.
	//
	PWMGenEnable(PWM0_BASE, PWM_GEN_0);
	PWMGenEnable(PWM0_BASE, PWM_GEN_1);
	PWMGenEnable(PWM0_BASE, PWM_GEN_2);

	return SUCCESS;
}
