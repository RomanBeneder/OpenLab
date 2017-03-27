/*
 * Error_request.h
 *
 *  Created on: 09.09.2016
 *      Author: HP
 */

#ifndef ERROR_REQUEST_H_
#define ERROR_REQUEST_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"

struct values error_request(struct values *handle);
struct values compare_crc(struct values *ptr);



#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_ERROR_REQUEST_H_ */
