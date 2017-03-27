/**
 * 	@file 		timer.h
 * 	@brief		32-bit Timer header file
 * 	@author		Christian Matar
 */

#ifndef TIMER_H_
#define TIMER_H_

#include "LPC13xx.h"
#include <string.h>

#define TIMER_0_INT_PRIO 	10
#define TIMER_1_INT_PRIO 	10

/**
 *  @brief	Constant PRESCALER_VAL ensures that one timer step equals to one µs.
 */
#define TIMER_PRESCALER_VAL		72

/**
 * 	@brief	Constant MATCH_VAL is a delay of one second, given PRESCALER_VAL is defined as 72.
 */
#define TIMER_MATCH_VAL			1000000

#define EXTERN extern

#define TIMER_DEBUG	1

#if TIMER_DEBUG
#include "gpio.h"
#endif

#include "commands.h"

/**
 * 	@brief	Structure to store a timer's captured values.
 */
typedef struct
{
	uint32_t capture_val_1;
	uint32_t capture_val_2;
	uint8_t values_amount;
	uint8_t ready_for_calc;
} timer_values_t;

EXTERN timer_values_t timer_0;
EXTERN timer_values_t timer_1;

/**
 * 	@brief	Function initTimer() initializes a 32-bit timer.
 * 	@param	timer indicates, which timer will be initialized.
 * 	@param	prescaler_val defines the prescaler value.
 * 	@param	match_val determines the timer's match value.
 */
void initTimer(LPC_TMR_TypeDef *timer, uint32_t prescaler_val, uint32_t match_val);

/**
 * 	@brief	Function updateTimerPeriod() updates the timer's match register,
 * 			which is responsible for the timer's period / frequency.
 * 	@param	timer indicates, which timer will be updated.
 * 	@param	match_val the new period.
 */
void updateTimerPeriod(LPC_TMR_TypeDef *timer, uint32_t match_val);

/**
 * 	@brief	Function startTimer() starts a 32-bit timer.
 * 	@param	timer indicates, which timer will be started.
 */
void startTimer(LPC_TMR_TypeDef *timer);

/**
 * 	@brief	Function stopTimer() starts a 32-bit timer.
 * 	@param	timer indicates, which timer will be stopped.
 */
void stopTimer(LPC_TMR_TypeDef *timer);

/**
 *  @brief	Function calculateFrequency() calculates the frequency
 *  		depending on the captured timer values.
 *  @param 	prefix defines the frequency's metric prefix.
 *  @param	channel defines which channel's frequency shall be calculated.
 *  @return	Returns the calculated frequency, or 0.
 */
uint32_t calculateFrequency(uint32_t prefix, uint8_t channel);

/**
 *  @brief	Function roundValue() rounds a given value.
 *  @param	value the value to be rounded.
 *  @return	returns the rounded value.
 */
uint32_t roundValue(double value);


#endif /* TIMER_H_ */
