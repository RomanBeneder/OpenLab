/**
 * 	@file 	crc.h
 * 	@author	Christian Matar
 */


#ifndef CRC_H_
#define CRC_H_

#include <stdint.h>

#define CRC16 		0x1021
#define HEADER_LEN	7

/**
 * 	@brief	Function gen_crc16() generates a CRC16.
 * 	@param	data which serves to calculate the CRC.
 * 	@param	size the data's size.
 */
uint16_t gen_crc16(const uint8_t *data, uint16_t size);

/**
 * 	@brief	Function check_crc16() checks a data's CRC.
 * 	@param	data which serves to check the CRC.
 * 	@param	size the data's size.
 */
uint8_t check_crc16(const uint8_t *data, uint16_t size);

#endif /* CRC_H_ */
