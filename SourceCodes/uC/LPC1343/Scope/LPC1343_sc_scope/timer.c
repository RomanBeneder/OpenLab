/**
 * 	@file 		timer.c
 * 	@brief		32-bit Timer source file
 * 	@author		Christian Matar
 */

#include "timer.h"

timer_values_t timer_0;
timer_values_t timer_1;

void initTimer(LPC_TMR_TypeDef *timer, uint32_t prescaler_val, uint32_t match_val)
{
	if((timer!=LPC_TMR32B0 && timer!=LPC_TMR32B1) || !timer)
		return;

	if(timer==LPC_TMR32B0)
	{
		LPC_SYSCON->SYSAHBCLKCTRL |= 1<<9;
		LPC_SYSCON->SYSAHBCLKDIV |= 1<<0;
		LPC_IOCON->PIO1_5 |= 1<<2;
	}
	else
	{
		LPC_SYSCON->SYSAHBCLKCTRL |= 1<<10;
		LPC_SYSCON->SYSAHBCLKDIV |= 1<<0;
		LPC_IOCON->JTAG_TMS_PIO1_0 |= 1<<2;
	}

	// Timer reset
	timer->TCR |= 1<<1;
	timer->TCR &= ~(1<<1);

	timer->TC = 0;
	timer->CCR |= 1<<0;
	timer->CTCR &= ~(1<<2);
	timer->PC = 0;
	timer->PR = prescaler_val;
	timer->MR0 = match_val;
#if TIMER_DEBUG
	timer->MCR |= (1<<0) | (1<<1);
#else
	timer->MCR |= 1<<1;
#endif
	// connect interrupts
	if(timer==LPC_TMR32B0)
	{
		NVIC_EnableIRQ(TIMER_32_0_IRQn);
		NVIC_SetPriority(TIMER_32_0_IRQn, TIMER_0_INT_PRIO);
	}
	else
	{
		NVIC_EnableIRQ(TIMER_32_1_IRQn);
		NVIC_SetPriority(TIMER_32_1_IRQn, TIMER_1_INT_PRIO);
	}
}

void updateTimerPeriod(LPC_TMR_TypeDef *timer, uint32_t match_val)
{
	if((timer!=LPC_TMR32B0 && timer!=LPC_TMR32B1) || !timer)
			return;

	timer->MR0 = match_val;
}

void startTimer(LPC_TMR_TypeDef *timer)
{
	if((timer!=LPC_TMR32B0 && timer!=LPC_TMR32B1) || !timer)
		return;

	timer->TCR |= 1<<0;
}

void stopTimer(LPC_TMR_TypeDef *timer)
{
	if((timer!=LPC_TMR32B0 && timer!=LPC_TMR32B1) || !timer)
		return;

	timer->TCR &= ~(1<<0);
}

uint32_t calculateFrequency(uint32_t prefix, uint8_t channel)
{
	uint32_t frequency = 0;
	switch(channel)
	{
	case 0:
		frequency = roundValue(prefix / (timer_0.capture_val_1 - timer_0.capture_val_2));
		timer_0.ready_for_calc = 0;
		break;
	case 1:
		frequency = roundValue(prefix / (timer_1.capture_val_1 - timer_1.capture_val_2));
		timer_1.ready_for_calc = 0;
		break;
	}
	return frequency;
}

uint32_t roundValue(double value)
{
	uint32_t rounded_value, temp_value;

	temp_value = (uint32_t) value;
	if(value - temp_value >= 0.5)
		rounded_value = temp_value + 1;
	else
		rounded_value = temp_value;

	return rounded_value;
}

/**
 * 	@brief	Function TIMER32_0_IRQHandler() implements
 * 			the interrupt handler for LPC_TMR32B0.
 */
void TIMER32_0_IRQHandler()
{
	if(timer_0.values_amount==0)
		timer_0.capture_val_1 = LPC_TMR32B0->CR0;
	else
		timer_0.capture_val_2 = LPC_TMR32B0->CR0;

	timer_0.values_amount++;
	if(timer_0.values_amount>=2)
	{
		timer_0.values_amount = 0;
		timer_0.ready_for_calc = 1;
		frequencyInfo(1, 0, calculateFrequency(TIMER_MATCH_VAL, 1));
		stopTimer(LPC_TMR32B0);
	}
	else
		triggerNotify(1, 0);
	NVIC_ClearPendingIRQ(TIMER_32_0_IRQn);
	LPC_TMR32B0->IR |= 1<<4;
#if TIMER_DEBUG
	LPC_TMR32B0->IR |= 1<<0;
	GPIO_ToggleOutput(LPC_GPIO3, 0);
#endif
}

/**
 * 	@brief	Function TIMER32_1_IRQHandler() implements
 * 			the interrupt handler for LPC_TMR32B1.
 */
void TIMER32_1_IRQHandler()
{
	if(timer_1.values_amount==0)
		timer_1.capture_val_1 = LPC_TMR32B1->CR0;
	else
		timer_1.capture_val_2 = LPC_TMR32B1->CR0;

	timer_1.values_amount++;
	if(timer_1.values_amount>=2)
	{
		timer_1.values_amount = 0;
		timer_1.ready_for_calc = 1;
		frequencyInfo(2, 0, calculateFrequency(TIMER_MATCH_VAL, 2));
		stopTimer(LPC_TMR32B1);
	}
	else
		triggerNotify(2, 0);
	NVIC_ClearPendingIRQ(TIMER_32_1_IRQn);
	LPC_TMR32B1->IR |= 1<<4;
#if TIMER_DEBUG
	LPC_TMR32B1->IR |= 1<<0;
	GPIO_ToggleOutput(LPC_GPIO3, 1);
#endif
}
