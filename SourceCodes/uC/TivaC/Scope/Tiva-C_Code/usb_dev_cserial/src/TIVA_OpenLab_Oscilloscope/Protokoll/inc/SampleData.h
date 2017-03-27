/*
 * SampleData.h
 *
 *  Created on: 15.09.2016
 *      Author: HP
 */

#ifndef SAMPLEDATA_H_
#define SAMPLEDATA_H_

#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include "TIVA_GPIO_lib.h"
#include "Protokoll.h"

struct values SampleData(struct values *ptr);//, uint16_t CHANNEL_num);
//void SampleData(struct values *ptr, uint16_t CHANNEL_num);
struct values HeaderCrcSD(struct values *ptr);
struct values PayloadCrcSD(struct values *ptr);



#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SAMPLEDATA_H_ */
