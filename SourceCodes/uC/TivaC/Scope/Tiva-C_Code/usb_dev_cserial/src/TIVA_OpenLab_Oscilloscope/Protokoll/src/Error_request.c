/*
 * error.c
 *
 *  Created on: 09.09.2016
 *      Author: HP
 */

#include "Error_request.h"

struct values error_request(struct values *handle)
{
	if(handle->command == STS || handle->command == SCS || handle->command == STB || handle->command == GHWV || handle->command == GSWV
			|| handle->command == NACK || handle->command == ACK) {
		//handle->error = SUCCESS;
		if(handle->device == DEVICE || handle->device == OSCILLOSCOPE || handle->device == SIGNAL_GENERATOR) {
			//handle->error = SUCCESS;
			if(handle->channel == CH1 || handle->channel == CH2) {
				//handle->error = SUCCESS;
				if(handle->payload_high == 0) {
					//handle->error = SUCCESS;
					if(handle->payload_low == PAYLOAD_STS || handle->payload_low == PAYLOAD_SCS || handle->payload_low == PAYLOAD_STB
							|| handle->payload_low == PAYLOAD_GHWV || handle->payload_low == PAYLOAD_GSWV
							|| handle->payload_low == PAYLOAD_NACK || handle->payload_low == PAYLOAD_ACK)
						handle->error = SUCCESS;
					else
						handle->error = NACK_NOT_SUPPORTED;
				} else
					handle->error = NACK_NOT_SUPPORTED;
			} else
				handle->error = NACK_NOT_SUPPORTED;
		} else
				handle->error = NACK_NOT_SUPPORTED;
	} else
		handle->error = NACK_NOT_SUPPORTED;

	return *handle;
}

struct values compare_crc(struct values *handle)
{
	if(handle->crc_low == handle->crc_receive_low && handle->crc_high == handle->crc_receive_high) {
		handle->error = SUCCESS;
	} else
		handle->error = NACK_FAILED;

	return *handle;
}
