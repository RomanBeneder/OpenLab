/*
 * Notacknowledge.c
 *
 *  Created on: 13.09.2016
 *      Author: HP
 */

#include "Notacknowledge.h"

struct values HeaderCrcNACK(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[0];
	ptr->crc_header[1] = ptr->device;
	ptr->crc_header[2] = ptr->channel;
	ptr->crc_header[3] = ptr->reply[3];
	ptr->crc_header[4] = ptr->reply[4];

	return *ptr;
}

struct values PayloadCrcNACK(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[7];
	ptr->size = PAYLOAD_NACK;

	return *ptr;
}
