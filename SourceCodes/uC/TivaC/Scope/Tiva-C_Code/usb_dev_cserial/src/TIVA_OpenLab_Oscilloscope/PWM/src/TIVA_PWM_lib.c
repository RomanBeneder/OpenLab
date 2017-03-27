/*
 * TIVA_PWM_lib.c
 *
 *  Created on: 23.09.2016
 *      Author: HP
 */

#include "TIVA_PWM_lib.h"

struct values SetPWMValue(struct values *ptr)
{
	//uint16_t pwm,
	uint16_t triggerLevel;

	ptr->pwm = ptr->package[9] | (ptr->package[8] << 8);
	if(ptr->pwm == 255) {
		trigger1 = trigger2 = OUTOFRANGE;
//		TimerEnable(TIMER0_BASE, TIMER_A);
	} else
		trigger1 = trigger2 = INRANGE;

	triggerLevel = ptr->pwm *100 / ADCRANGE;

//	ptr->pwm_low = pwm_low= pwm - 3;
//	ptr->pwm_high = pwm_high = pwm + 3;

	switch(ptr->channel)
	{
	case CHANNEL0:
		ptr->pwm = TICKS * triggerLevel / 100;
		PWMPulseWidthSet(PWM0_BASE, PWM_OUT_1, ptr->pwm);
		PWMPulseWidthSet(PWM0_BASE, PWM_OUT_2, ptr->pwm);
		ptr->error = SUCCESS;
		break;

	case CHANNEL1:
		ptr->pwm = TICKS * triggerLevel / 100;
		PWMPulseWidthSet(PWM0_BASE, PWM_OUT_1, ptr->pwm);
		PWMPulseWidthSet(PWM0_BASE, PWM_OUT_2, ptr->pwm);
		ptr->error = SUCCESS;
		break;

	default:
		ptr->error = NACK_NOT_SUPPORTED;
	}

	//ptr->error = SUCCESS;
	return *ptr;
}
