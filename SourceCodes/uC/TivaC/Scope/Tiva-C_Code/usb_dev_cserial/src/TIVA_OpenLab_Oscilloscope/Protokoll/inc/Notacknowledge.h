/*
 * Notacknowledge.h
 *
 *  Created on: 13.09.2016
 *      Author: HP
 */

#ifndef NOTACKNOWLEDGE_H_
#define NOTACKNOWLEDGE_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values HeaderCrcNACK(struct values *ptr);
struct values PayloadCrcNACK(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_NOTACKNOWLEDGE_H_ */
