/*
 * ExtendedSampleData.c
 *
 *  Created on: 24.01.2017
 *      Author: HP
 */
#include "ExtendedSampleData.h"

struct values HeaderCrcESD(struct values *ptr)
{
	ptr->crc_header[0] = ptr->replyESD[0];
	ptr->crc_header[1] = ptr->replyESD[1];
	ptr->crc_header[2] = ptr->replyESD[2];
	ptr->crc_header[3] = ptr->replyESD[3];
	ptr->crc_header[4] = ptr->replyESD[4];
	ptr->size = HEADER;
	return *ptr;
}

struct values PayloadCrcESD(struct values *ptr)
{
	int i, j=7;
	for(i = 0; i<(SETSsamples + 11); i++) {
		ptr->crc_header[i] = ptr->replyESD[j];
		j++;
	}
	ptr->size = SETSsamples + 11;

	return *ptr;
}
