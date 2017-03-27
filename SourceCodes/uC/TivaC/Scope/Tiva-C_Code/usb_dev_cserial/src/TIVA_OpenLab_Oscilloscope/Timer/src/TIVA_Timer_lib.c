/*
 * TIVA_Timer_lib.c
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#include "TIVA_Timer_lib.h"

struct values ETS_Packages(struct values *ptr)
{
	uint32_t loadValue;

	switch(SETSrounds) {
//	case QUARTER_Ms:
//		LoadValue = TIMER / 250000 * 2 * actualRounds;
//		break;
	case HALF_Ms:
		loadValue = TIMER / 500000 * 2 * actualRounds;
		break;
	case ONE_Ms:
		loadValue = TIMER / 1000000 * 2 * actualRounds;
		break;
	case TWOHALF_Ms:
		loadValue = TIMER / 2500000 * 2 * actualRounds;
		break;
	case FIVE_Ms:
		loadValue = TIMER / 5000000 * 2 * actualRounds;
		break;
	case TEN_Ms:
		loadValue = TIMER / 10000000 * 2 * actualRounds;
		break;
	case TWENTY_Ms:
		loadValue = TIMER / 20000000 * 2 * actualRounds;
		break;
	case FIFTHY_Ms:
		loadValue = TIMER / 50000000 * 2 * actualRounds;
		break;
	}

	TimerLoadSet(TIMER2_BASE, TIMER_A, loadValue);
	TimerLoadSet(TIMER3_BASE, TIMER_A, loadValue);
	SysCtlDelay(50);

	return *ptr;
}
