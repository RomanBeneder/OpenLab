/**
 * 	@file 	commands.h
 * 	@date 	3. Jän. 2016
 * 	@author	Christian Matar
 */

#ifndef COMMANDS_H_
#define COMMANDS_H_

#include "timer.h"
#include "pwm.h"
#include "crc.h"
#include "uart.h"

#define USE_CRC			0

#define CMD_SIZE		1
#define DEVICE_SIZE		1
#define CHANNEL_SIZE	1
#define PAYLOAD_SIZE	2
#define CRC_SIZE		2

#define MAJOR_SIZE		1
#define MINOR_SIZE		1
#define SUB_SIZE		1
#define RSV_SIZE		1
#define ERROR_SIZE		1
#define TRIG_VAL_SIZE	1
#define SYNC_SIZE		1
#define HOLDOFF_SIZE	4
#define FREQ_VAL_SIZE	4

// COMMAND SIZES
#define HEADER_SIZE		CMD_SIZE + DEVICE_SIZE + CHANNEL_SIZE + PAYLOAD_SIZE + CRC_SIZE
#define HW_SIZE			HEADER_SIZE + MAJOR_SIZE + MINOR_SIZE + SUB_SIZE + RSV_SIZE + CRC_SIZE
#define NACK_SIZE		HEADER_SIZE	+ ERROR_SIZE + CRC_SIZE
#define ACK_SIZE		HEADER_SIZE
#define SYNCHRO_SIZE	HEADER_SIZE + SYNC_SIZE + HOLDOFF_SIZE + CRC_SIZE
#define TRIGGER_SIZE	HEADER_SIZE + TRIG_VAL_SIZE + HOLDOFF_SIZE + CRC_SIZE
#define FREQ_SIZE		HEADER_SIZE + FREQ_VAL_SIZE + HOLDOFF_SIZE + CRC_SIZE

// COMMAND CODES
#define HW_CMD_NR		9
#define SW_CMD_NR		10
#define NACK_CMD_NR		126
#define ACK_CMD_NR		127
#define SYNC_CMD_NR		1
#define TRIGGER_CMD_NR	2
#define FREQ_CMD_NR		3

// ERROR CODES
#define WARNING			1
#define NOT_SUPPORTED	2
#define UNKNOWN			3
#define FAILED			4
#define OVERFLOW		5
#define WRONG_CHANNEL	0

#define ASCII_OFFSET	0x30

/**
 * 	@brief	Set to 1 if USB communication is implemented.
 * 			0 enables communication via UART.
 */
#define USB_COM			0

/**
 * 	@brief	Function hardwareVersion() implements the hardware version reply.
 * 	@param	channel the channel, which runs the measurement.
 */
void hardwareVersion(uint8_t channel);

/**
 * 	@brief	Function nack() implements the nack reply.
 * 	@param	channel the channel, which runs the measurement.
 * 	@param	error an error code
 */
void nack(uint8_t channel, uint8_t error);

/**
 * 	@brief	Function ack() implements the ack reply.
 * 	@param	channel the channel, which runs the measurement.
 */
void ack(uint8_t channel);

/**
 * 	@brief	Function syncMsg() implements the sync message reply.
 * 	@param	channel the channel, which runs the measurement.
 */
void syncMsg(uint8_t channel);

/**
 * 	@brief	Function triggerNotify() implements the trigger notify reply.
 * 	@param	channel the channel, which runs the measurement.
 * 	@param	holdoff the time between two notifications.
 */
void triggerNotify(uint8_t channel, uint32_t holdoff);

/**
 * 	@brief	Function frequencyInfo() implements the frequency info reply.
 * 	@param	channel the channel, which runs the measurement.
 * 	@param	holdoff the time between two notifications.
 * 	@param	freq the calculated frequency
 */
void frequencyInfo(uint8_t channel, uint32_t holdoff, uint32_t freq);

/**
 * 	@brief	Function parseCmd() parses a command received from the GUI.
 * 	@data	data which was received.
 */
void parseCmd(uint8_t *data);


#endif /* COMMANDS_H_ */
