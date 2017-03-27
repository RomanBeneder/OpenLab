/*
 * HardwareVersion.c
 *
 *  Created on: 09.09.2016
 *      Author: HP
 */

#include "HardwareVersion.h"

//struct values setHWV(struct values set)
//void setHWV(struct values set)
//{
//	int i = 0;
//
////	set.reply[0] = HWV;
////	set.reply[1] = set.device;
////	set.reply[2] = set.channel;
////	set.reply[3] = ZERO;
////	set.reply[4] = PAYLOAD_HWV;
////	set = HeaderCrcHWV(&set);
////	set.size = HEADER;
////	set = crc16(&set);
////	set.reply[5] = set.crc_high;
////	set.reply[6] = set.crc_low;
////	set.reply[7] = MAJOR_VERSION;
////	set.reply[8] = MINOR_VERSION;
////	set.reply[9] = SUB_VERSION;
////	set.reply[10] = ZERO;
////	set = PayloadCrcHWV(&set);
////	set = crc16(&set);
////	set.reply[11] = set.crc_high;
////	set.reply[12] = set.crc_low;
////	set.reply[13] = '\0';
////	set.transmit_size = SIZE_HWV;
////
////	for(i = 0; i<set.transmit_size+1; i++)
////		sprintf(&set.test[i][0], "%d", set.reply[i]);
//
//	//return set;
//}

struct values HeaderCrcHWV(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[0];
	ptr->crc_header[1] = ptr->device;
	ptr->crc_header[2] = ptr->channel;
	ptr->crc_header[3] = ptr->reply[3];
	ptr->crc_header[4] = ptr->reply[4];

	return *ptr;
}

struct values PayloadCrcHWV(struct values *ptr)
{
	ptr->crc_header[0] = ptr->reply[7];
	ptr->crc_header[1] = ptr->reply[8];
	ptr->crc_header[2] = ptr->reply[9];
	ptr->crc_header[3] = ptr->reply[10];
	ptr->size = PAYLOAD_HWV;

	return *ptr;
}

