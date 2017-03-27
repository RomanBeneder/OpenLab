/*
 * Acknowledge.c
 *
 *  Created on: 13.09.2016
 *      Author: HP
 */

#include "Acknowledge.h"

struct values HeaderCrcACK(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[0];
	ptr->crc_header[1] = ptr->device;
	ptr->crc_header[2] = ptr->channel;
	ptr->crc_header[3] = ptr->reply[3];
	ptr->crc_header[4] = ptr->reply[4];
	ptr->size = HEADER;

	return *ptr;
}
