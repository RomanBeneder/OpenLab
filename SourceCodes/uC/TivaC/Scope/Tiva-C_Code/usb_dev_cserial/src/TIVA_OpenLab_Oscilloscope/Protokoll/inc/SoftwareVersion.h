/*
 * SoftwareVersion.h
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */

#ifndef SOFTWAREVERSION_H_
#define SOFTWAREVERSION_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"
#include "CRC16.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values HeaderCrcSWV(struct values *ptr);
struct values PayloadCrcSWV(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SOFTWAREVERSION_H_ */
