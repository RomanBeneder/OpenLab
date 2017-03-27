/*
 * TIVA_USB_lib.h
 *
 *  Created on: 14.08.2016
 *      Author: HP
 */

#ifndef TIVA_USB_LIB_H_
#define TIVA_USB_LIB_H_

/***************************************************************************** LIBRARIES */
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <stdint.h>
//#include "inc/hw_ints.h"
#include "inc/hw_memmap.h"
#include "inc/hw_types.h"
#include "driverlib/debug.h"
#include "driverlib/gpio.h"
#include "driverlib/interrupt.h"
#include "driverlib/rom.h"
#include "driverlib/rom_map.h"
#include "driverlib/sysctl.h"
#include "driverlib/systick.h"
#include "driverlib/timer.h"
#include "driverlib/usb.h"
#include "usblib/usblib.h"
#include "usblib/usbcdc.h"
#include "usblib/usb-ids.h"
#include "usblib/device/usbdevice.h"
#include "usblib/device/usbdcomp.h"
#include "usblib/device/usbdcdc.h"
#include "utils/cmdline.h"
#include "utils/ustdlib.h"
#include "drivers/pinout.h"
#include "usb_structs.h"
#include "Protokoll.h"
#include "global.h"

/***************************************************************************** DEFINES */
#define SUCCESS 0
#define BUFFERSIZE 4095
/***************************************************************************** FUNCTION PROTOTYPES */
uint16_t _TIVA_USB_Configuration(void);
void SendSD(struct values *struc);
void SendESD(struct values *struc);
//void SendText(void);

#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_USB_INC_TIVA_USB_LIB_H_ */
