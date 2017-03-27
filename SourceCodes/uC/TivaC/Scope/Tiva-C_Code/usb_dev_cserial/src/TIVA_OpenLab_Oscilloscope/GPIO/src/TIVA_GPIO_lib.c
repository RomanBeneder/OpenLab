/*
 * TIVA_GPIO_lib.c
 *
 *  Created on: 23.09.2016
 *      Author: HP
 */


#include "TIVA_GPIO_lib.h"

#define TRIGGER_TYPE 31
#define TRIGGER_MODE 224

struct values SetTriggerType_Mode(struct values *ptr)
{
	uint8_t mode = ptr->package[7] & TRIGGER_MODE;
	uint8_t type = ptr->package[7] - mode;

	switch(ptr->channel)
	{
	case CHANNEL0:
		edge2 = DISABLE;
		if(type == RISING_EDGE) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_4, GPIO_RISING_EDGE);
//			TimerDisable(TIMER0_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			edge1 = RISING1;
			packet1 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == FALLING_EDGE) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_4, GPIO_FALLING_EDGE);
			TimerDisable(TIMER0_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			edge1 = FALLING;
			packet1 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == BOTH_EDGES) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_4, GPIO_BOTH_EDGES);
			TimerDisable(TIMER0_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			edge1 = BOTH_EDGES;
			packet1 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == DISABLE_TRIGGER) {
			GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
			TimerEnable(TIMER0_BASE, TIMER_A);
			edge1 = DISABLE;
			packet1 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
		if(mode == AUTO_MODE) {
//			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
//			TimerDisable(TIMER0_BASE, TIMER_A);
			sampleData1 = TRANSMIT;
			ptr->error = SUCCESS;
		} else if(mode == NORMAL_MODE) {
			sampleData1 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
		break;

	case CHANNEL1:
		edge1 = DISABLE;
		if(type == RISING_EDGE) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_5, GPIO_RISING_EDGE);
	//			TimerDisable(TIMER1_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			edge2 = RISING2;
			packet2 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == FALLING_EDGE) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_5, GPIO_FALLING_EDGE);
			TimerDisable(TIMER1_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			edge2 = FALLING;
			packet2 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == BOTH_EDGES) {
			GPIOIntTypeSet(GPIO_PORTL_BASE, GPIO_PIN_5, GPIO_BOTH_EDGES);
			TimerDisable(TIMER1_BASE, TIMER_A);
			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			edge2 = BOTH_EDGES;
			packet2 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else if(type == DISABLE_TRIGGER) {
			GPIOIntDisable(GPIO_PORTL_BASE, GPIO_INT_PIN_5);
			TimerEnable(TIMER1_BASE, TIMER_A);
			edge2 = DISABLE;
			packet2 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
		if(mode == AUTO_MODE) {
	//			GPIOIntEnable(GPIO_PORTL_BASE, GPIO_INT_PIN_4);
	//			TimerDisable(TIMER0_BASE, TIMER_A);
			sampleData2 = TRANSMIT;
			ptr->error = SUCCESS;
		} else if(mode == NORMAL_MODE) {
			sampleData2 = NOT_TRANSMIT;
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
		break;

	default:
		ptr->error = NACK_NOT_SUPPORTED;
	}

	return *ptr;
}
