/*
 * Acknowledge.h
 *
 *  Created on: 13.09.2016
 *      Author: HP
 */

#ifndef ACKNOWLEDGE_H_
#define ACKNOWLEDGE_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values HeaderCrcACK(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_ACKNOWLEDGE_H_ */
