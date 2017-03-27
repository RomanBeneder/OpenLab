/*
 * SetChannelSettings.c
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */


#include "SetChannelSettings.h"

struct values GainSetting(struct values *ptr)
{
	if(ptr->channel == CH1) { // && (ptr->package[7] >= 0 && ptr->package[7] <= 7)) {
		if(ptr->package[7] == 0) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 1) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, 0);
			ptr->error = SUCCESS;
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 2) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, GPIO_PIN_1);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 3) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, GPIO_PIN_1);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 4) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, GPIO_PIN_0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 5) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, GPIO_PIN_0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 6) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, GPIO_PIN_1);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, GPIO_PIN_0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 7) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_1, GPIO_PIN_1);
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_0, GPIO_PIN_0);
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
	} else if(ptr->channel == CH2) { // && (ptr->package[7] >= '0' && ptr->package[7] <= '7')) {
		if(ptr->package[7] == 0) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 1) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, GPIO_PIN_3);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 2) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 3) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, GPIO_PIN_3);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, 0);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 4) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, GPIO_PIN_3);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 5) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, GPIO_PIN_3);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, GPIO_PIN_3);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 6) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, 0);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, GPIO_PIN_3);
			ptr->error = SUCCESS;
		} else if(ptr->package[7] == 7) {
			GPIOPinWrite(GPIO_PORTL_BASE, GPIO_PIN_3, GPIO_PIN_3);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_2, GPIO_PIN_2);
			GPIOPinWrite(GPIO_PORTH_BASE, GPIO_PIN_3, GPIO_PIN_3);
			ptr->error = SUCCESS;
		} else
			ptr->error = NACK_NOT_SUPPORTED;
	} else
		ptr->error =  NACK_NOT_SUPPORTED;

	return *ptr;
}


struct values HeaderCrcSCS(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[0];
	ptr->crc_header[1] = ptr->device;
	ptr->crc_header[2] = ptr->channel;
	ptr->crc_header[3] = ptr->reply[3];
	ptr->crc_header[4] = ptr->reply[4];

	return *ptr;
}

struct values PayloadCrcSCS(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[7];
	ptr->crc_header[1] = ptr->reply[8];
	ptr->crc_header[2] = ptr->reply[9];
	ptr->crc_header[3] = ptr->reply[10];
	ptr->size = PAYLOAD_HWV;

	return *ptr;
}
