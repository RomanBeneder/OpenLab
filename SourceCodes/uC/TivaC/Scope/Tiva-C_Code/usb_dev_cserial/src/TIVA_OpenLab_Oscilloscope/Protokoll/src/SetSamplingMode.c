/*
 * SetSamplingMode.c
 *
 *  Created on: 06.12.2016
 *      Author: HP
 */

#include "SetSamplingMode.h"

struct values SetSamplingMode(struct values *ptr)
{
	if(ptr->package[7] == RTS) {
		sample_mode = RTS;
//		_TIVA_ADC_Configuration();
		SETSsamples = 514;

	} else if(ptr->package[7] == SETS) {
		sample_mode = ETS;
		actualRounds = 0;
		if(triggerChannel == CH1) {
			if(edge1 == RISING1)
				edge2 = RISING2;
			else
				edge2 = edge1;
		} else if(triggerChannel == CH2) {
			if(edge2 == RISING2)
				edge1 = RISING1;
			else
				edge1 = edge2;
		}
//		_TIVA_uDMA_Configuration();
//		_TIVA_ADC_ETS_Configuration();

		ptr->etsSampleHigh = ptr->package[8];
		ptr->etsSampleLow = ptr->package[9];
		SETSsamples = ptr->etsSampleLow | (ptr->etsSampleHigh << 8);
		SETSrounds = ptr->package[11] | (ptr->package[10] << 8);

	} else if(ptr->package[7] == RETS) {

	} else
		ptr->error = NACK_NOT_SUPPORTED;
	return *ptr;
}

struct values PayloadCrcSSM(struct values *ptr)
{
	ptr->crc_header[0] = ptr->package[7];
	ptr->crc_header[1] = ptr->package[8];
	ptr->crc_header[2] = ptr->package[9];
	ptr->crc_header[3] = ptr->package[10];
	ptr->crc_header[4] = ptr->package[11];

	ptr->crc_receive_high = ptr->package[12];
	ptr->crc_receive_low = ptr->package[13];
	ptr->size = PAYLOAD_SSM;

	return *ptr;
}
