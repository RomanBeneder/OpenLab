/*
 * SetTimeBase.c
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */


#include "SetTimeBase.h"

struct values SetTimeBaseSettings(struct values *ptr)
{
	ptr->RTMSsamples = ptr->package[10] | (ptr->package[9] << 8) | (ptr->package[8] << 16) | (ptr->package[7] << 24);

	*ptr = SetTimeBase(ptr);
//	if(ptr->error == SUCCESS)
//		setACK();
//	else
//		setNACK();

	return *ptr;
}

struct values PayloadCrcSTB(struct values *ptr)
{
	ptr->crc_header[0] = ptr->package[7];
	ptr->crc_header[1] = ptr->package[8];
	ptr->crc_header[2] = ptr->package[9];
	ptr->crc_header[3] = ptr->package[10];

	ptr->crc_receive_high = ptr->package[11];
	ptr->crc_receive_low = ptr->package[12];
	ptr->size = PAYLOAD_STB;

	return *ptr;
}
