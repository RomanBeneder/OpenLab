/*
 * HardwareVersion.h
 *
 *  Created on: 09.09.2016
 *      Author: HP
 */

#ifndef HARDWAREVERSION_H_
#define HARDWAREVERSION_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"
#include "CRC16.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values HeaderCrcHWV(struct values *ptr);
struct values PayloadCrcHWV(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_HARDWAREVERSION_H_ */
