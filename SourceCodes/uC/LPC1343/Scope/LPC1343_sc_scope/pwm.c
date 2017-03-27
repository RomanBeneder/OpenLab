/**
 * 	@file 		pwm.c
 * 	@brief		16-bit PWM source file
 * 	@author		Christian Matar
 */

#include "pwm.h"

void initPWM(LPC_TMR_TypeDef *timer, uint32_t prescaler_val, uint32_t match_val, uint8_t match_reg, double duty_cycle)
{
	if(timer!=LPC_TMR16B1 || !timer)
			return;

	LPC_SYSCON->SYSAHBCLKCTRL |= 1<<8;
	LPC_IOCON->PIO1_9 |= 1<<0 | 1<<4;
	LPC_IOCON->PIO1_9 &= ~(1<<3);
	GPIO_SetDirection(LPC_GPIO1, 9, GPIO_DIR_OUTPUT);
	LPC_IOCON->PIO1_10 |= 1<<1 | 1<<4;
	LPC_IOCON->PIO1_10 &= ~(1<<3);
	GPIO_SetDirection(LPC_GPIO1, 10, GPIO_DIR_OUTPUT);

	// reset timer
	timer->TCR |= 1<<1;
	timer->TCR &= ~(1<<1);

	double percentage = (double) duty_cycle / 100;
	percentage = 1 - percentage;

	switch(match_reg)
	{
	case 0: timer->MR0 = (uint32_t) match_val * percentage; break;
	case 1: timer->MR1 = (uint32_t) match_val * percentage; break;
	case 2: timer->MR2 = (uint32_t) match_val * percentage; break;
	default: return;
	}

	timer->MR3 = match_val;

	timer->MCR |= 1<<9 | 1<<10;

	timer->PWMC |= 1<<match_reg;

	// connect interrupts
	NVIC_EnableIRQ(TIMER_16_1_IRQn);
	NVIC_SetPriority(TIMER_16_1_IRQn, PWM_INT_PRIO);
}

void startPWM(LPC_TMR_TypeDef *timer)
{
	if(timer!=LPC_TMR16B1 || !timer)
			return;

	timer->TCR |= 1<<0;
}

void stopPWM(LPC_TMR_TypeDef *timer)
{
	if(timer!=LPC_TMR16B1 || !timer)
			return;

	timer->TCR &= ~(1<<0);
}

void updateDutyCycle(LPC_TMR_TypeDef *timer, uint8_t match_reg, double duty_cycle)
{
	if(timer!=LPC_TMR16B1 || !timer)
			return;

	double percentage = (double) duty_cycle / 100;
	percentage = 1 - percentage;

	switch(match_reg)
	{
	case 0: timer->MR0 = (uint32_t) timer->MR3 * percentage; break;
	case 1: timer->MR1 = (uint32_t) timer->MR3 * percentage; break;
	case 2: timer->MR2 = (uint32_t) timer->MR3 * percentage; break;
	default: break;
	}
}

/**
 * 	@brief	Function TIMER16_1_IRQHandler() implements
 * 			the interrupt handler for the 16-bit timer_1.
 */
void TIMER16_1_IRQHandler()
{
	NVIC_ClearPendingIRQ(TIMER_16_1_IRQn);
	LPC_TMR16B1->IR |= 1<<0 | 1<<1;
}
