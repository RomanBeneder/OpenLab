/*
 * SoftwareVersion.c
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */

#include "SoftwareVersion.h"

struct values HeaderCrcSWV(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[0];
	ptr->crc_header[1] = ptr->device;
	ptr->crc_header[2] = ptr->channel;
	ptr->crc_header[3] = ptr->reply[3];
	ptr->crc_header[4] = ptr->reply[4];

	return *ptr;
}

struct values PayloadCrcSWV(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[7];
	ptr->crc_header[1] = ptr->reply[8];
	ptr->crc_header[2] = ptr->reply[9];
	ptr->crc_header[3] = ptr->reply[10];
	ptr->size = PAYLOAD_SWV;

	return *ptr;
}
