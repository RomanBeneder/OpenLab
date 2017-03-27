/*
 * SampleData.c
 *
 *  Created on: 15.09.2016
 *      Author: HP
 */


#include "SampleData.h"


struct values SampleData(struct values *ptr)//, uint16_t CHANNEL_num)
//void SampleData(struct values *ptr, uint16_t CHANNEL_num)
{
	int i, j=7;
	uint32_t ADC0, ADC1;

	//switch(CHANNEL_num)
	switch(ptr->channel)
	{
	case CHANNEL0:
		for(i=0; i<512; i++) {
//			cbGet_0(&ADC0);
			ptr->reply[j] = ADC0;
			j++;
		}
		break;

	case CHANNEL1:
		for(i=0; i<512; i++) {
//			cbGet_1(&ADC1);
			ptr->reply[j] = ADC1;
			j++;
		}
		break;

	default:
		ptr->error = NACK_NOT_SUPPORTED;
	}
	ptr->size = i;
	ptr->transmit_size = j;

	return *ptr;
}

struct values HeaderCrcSD(struct values *ptr)
{
	ptr->crc_header[0] = ptr->replySD[0];
	ptr->crc_header[1] = ptr->replySD[1];
	ptr->crc_header[2] = ptr->replySD[2];
	ptr->crc_header[3] = ptr->replySD[3];
	ptr->crc_header[4] = ptr->replySD[4];
	ptr->size = HEADER;
	return *ptr;
}

struct values PayloadCrcSD(struct values *ptr)
{
	int i, j=7;
	for(i = 0; i<ADC; i++) {
		ptr->crc_header[i] = ptr->replySD[j];
		j++;
	}
	ptr->size = PAYLOAD_SD;

	return *ptr;
}
