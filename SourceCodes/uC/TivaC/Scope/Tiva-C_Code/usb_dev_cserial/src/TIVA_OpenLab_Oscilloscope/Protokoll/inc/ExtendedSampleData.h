/*
 * ExtendedSampleData.h
 *
 *  Created on: 24.01.2017
 *      Author: HP
 */

#ifndef EXTENDEDSAMPLEDATA_H_
#define EXTENDEDSAMPLEDATA_H_


#include <stdio.h>
#include <stdbool.h>
#include <stdlib.h>
#include <stdint.h>
#include <string.h>
#include "TIVA_GPIO_lib.h"
#include "Protokoll.h"

struct values HeaderCrcESD(struct values *ptr);
struct values PayloadCrcESD(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_EXTENDEDSAMPLEDATA_H_ */
