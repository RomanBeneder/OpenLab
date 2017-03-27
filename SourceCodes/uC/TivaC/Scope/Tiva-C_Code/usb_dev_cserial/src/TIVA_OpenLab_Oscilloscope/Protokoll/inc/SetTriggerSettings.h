/*
 * SetTriggerSettings.h
 *
 *  Created on: 12.09.2016
 *      Author: HP
 */

#ifndef SETTRIGGERSETTINGS_H_
#define SETTRIGGERSETTINGS_H_

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
#include "Protokoll.h"

//struct values setHWV(struct values set);
//void setHWV(struct values set);
struct values HeaderCrcSTS(struct values *ptr);
struct values PayloadCrcSTS(struct values *ptr);


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_PROTOKOLL_INC_SETTRIGGERSETTINGS_H_ */
