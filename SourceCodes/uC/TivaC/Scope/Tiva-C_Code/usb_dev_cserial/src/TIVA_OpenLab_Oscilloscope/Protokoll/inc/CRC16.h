/*
 * CRC16.h
 *
 *  Created on: 26.08.2016
 *      Author: HP
 */

#ifndef CRC16_H_
#define CRC16_H_

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>
#include "Protokoll.h"

#define           poly     0x1021          /* crc-ccitt mask */

struct values crc16(struct values *pointer);
void go(void);
void update_good_crc(unsigned short);

#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_USB_INC_CRC_16_H_ */
