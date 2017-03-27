/*
 * SetTimeBase.h
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */

#ifndef SETTIMEBASE_H_
#define SETTIMEBASE_H_


#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"
#include "CRC16.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values SetTimeBaseSettings(struct values *ptr);
//void SetTimeBaseSettings(struct values *ptr);
struct values PayloadCrcSTB(struct values *ptr);

#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SETTIMEBASE_H_ */
