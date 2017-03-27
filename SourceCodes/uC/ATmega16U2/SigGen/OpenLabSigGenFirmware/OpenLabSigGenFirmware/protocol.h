/*
 * Definitions for the OpenLab Protocol
 */ 


#ifndef OL_PROTOCOL_H
#define OL_PROTOCOL_H

typedef enum
{
	INVALID,
	SET_TRIGGER_SETTINGS,
	SET_CHANNEL_SETTINGS,
	SET_TIME_BASE,
	GET_HARDWARE_VERSION,
	GET_SOFTWARE_VERSION,
	SET_LOOPBACK,
	SET_SAMPLING_MODE,
	SET_OFFSET = 44
}command_codes;

typedef enum
{
	SAMPLE_DATA = 8,
	HARDWARE_VERSION,
	SOFTWARE_VERSION,
	SAMPLE_DATA_EXT,
	
	NACK = 126,
	ACK
}reply_codes;



// Packing is actually not required as we are on
// an 8bit architecture.

/************************************************************************/
/* Packet Header   	                                                    */
/* Use ol_header if there is additional data attached					*/
/* Use ol_request if there is no additional data attached				*/
/************************************************************************/
typedef struct __attribute__ ((packed)) _ol_header
{
	uint8_t command;
	uint8_t device_type;
	uint8_t channel_num;
	uint16_t payload_size;
	uint16_t checksum;
}ol_header, ol_request;

/************************************************************************/
/* SET_OFFSET Request													*/
/* offset: IEEE754 single precision floating point, in Volts            */
/************************************************************************/
typedef struct __attribute__ ((packed)) _ol_set_offset_req
{
	float offset;	
	uint16_t checksum;
}ol_set_offset_req;


/************************************************************************/
/* HARDWARE_VERSION Response Structure                                  */
/************************************************************************/
typedef struct __attribute__ ((packed)) _ol_version_resp
{
	uint8_t major;
	uint8_t minor;
	uint8_t build;
	uint8_t reserved;
	uint16_t checksum;
}ol_version_resp;

#endif