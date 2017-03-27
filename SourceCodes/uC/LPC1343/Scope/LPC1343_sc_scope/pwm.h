/**
 * 	@file 		pwm.h
 * 	@brief		16-bit PWM header file
 * 	@author		Christian Matar
 */

#ifndef PWM_H_
#define PWM_H_

#include "LPC13xx.h"
#include "gpio.h"

#define PROTO_PCB 1

#define PWM_INT_PRIO 		10

/**
 * 	@brief	This defines set the PWM's frequency to 3.6 kHz
 */
#define PWM_PRESCALER_VAL	7200
#define PWM_MATCH_3_VAL		20000

#define PWM_MATCH_REG_0		0
#define PWM_MATCH_REG_1		1
#define PWM_MATCH_REG_2		2

#define PWM_DEBUG			1


/**
 * 	@brief	Function initPWM() initializes a 16-bit PWM.
 * 	@param	timer indicates, which timer will be initialized.
 * 	@param	prescaler_val defines the prescaler value.
 * 	@param	match_val determines the timer's match value.
 * 	@param	match_reg is the match registers number, which holds the duty cycle [0-2]
 * 	@param	duty_cycle sets the PWM's duty cycle.
 * 			The value has to be a percentage value.
 */
void initPWM(LPC_TMR_TypeDef *timer, uint32_t prescaler_val, uint32_t match_val, uint8_t match_reg, double duty_cycle);

/**
 * 	@brief	Function startPWM() starts 16-bit PWM.
 * 	@param	timer indicates, which timer's PWM will be initialized.
 */
void startPWM(LPC_TMR_TypeDef *timer);

/**
 * 	@brief	Function stopPWM() stops a 16-bit PWM.
 * 	@param	timer indicates, which timer's PWM will be initialized.
 */
void stopPWM(LPC_TMR_TypeDef *timer);

/**
 * 	@brief	Function updateDutyCycle() changes a timer's duty cycle.
 * 	@param	timer indicates, which PWM's duty cycle will be updated.
 * 	@param	match_reg, determines the match register to be updated.
 * 	@param	duty_cycle sets the PWM's duty cycle.
 * 			The value has to be a percentage value.
 */
void updateDutyCycle(LPC_TMR_TypeDef *timer, uint8_t match_reg, double duty_cycle);


#endif /* PWM_H_ */
