/*
 * SetChannel Settings.h
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */

#ifndef SETCHANNELSETTINGS_H_
#define SETCHANNELSETTINGS_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include <stdbool.h>
#include "driverlib/gpio.h"
#include "Protokoll.h"


struct values GainSetting(struct values *ptr);
struct values HeaderCrcSCS(struct values *ptr);
struct values PayloadCrcSCS(struct values *ptr);

#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SETCHANNEL_SETTINGS_H_ */
