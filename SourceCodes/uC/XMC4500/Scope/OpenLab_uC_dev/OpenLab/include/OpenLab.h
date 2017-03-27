/*
 * OpenLab.h
 *
 *  Created on: 19 Sep 2015
 *      Author: Harald
 */

#ifndef INCLUDE_OPENLAB_H_
#define INCLUDE_OPENLAB_H_

#include <stdint.h>

#ifdef __cplusplus
extern "C"
  {
#endif // __cplusplus

int
OLB_init (void);
int
OLB_USB_connect (void);
int
OLB_USB_is_connected (void);
int
OLB_USB_send_data (int8_t* const src, uint16_t const count);
int
OLB_USB_receive_data (int8_t* const dst, uint16_t const count);
uint16_t
OLB_USB_bytes_received (void);
#ifdef __cplusplus
}
#endif // __cplusplus
#endif /* INCLUDE_OPENLAB_H_ */
