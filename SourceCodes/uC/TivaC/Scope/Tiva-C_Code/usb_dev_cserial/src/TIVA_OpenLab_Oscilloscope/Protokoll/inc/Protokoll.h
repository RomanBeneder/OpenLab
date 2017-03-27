/*
 * Protokoll.h
 *
 *  Created on: 01.09.2016
 *      Author: HP
 */

#ifndef PROTOKOLL_H_
#define PROTOKOLL_H_

#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <stdint.h>
#include <stdbool.h>
#include "usblib/usblib.h"
#include "global.h"
#include "TIVA_ADC_lib.h"
//#include "TIVA_ADC_driver.h"
#include "TIVA_GPIO_lib.h"
#include "TIVA_PWM_lib.h"
#include "TIVA_USB_lib.h"
#include "TIVA_Timer_lib.h"
#include "CRC16.h"
#include "Error_request.h"
#include "HardwareVersion.h"
#include "SoftwareVersion.h"
#include "Acknowledge.h"
#include "Notacknowledge.h"
#include "SetTriggerSettings.h"
#include "SetChannelSettings.h"
#include "SetTimeBase.h"
#include "SampleData.h"
#include "SetSamplingMode.h"
#include "ExtendedSampleData.h"


#define STS		1
#define SCS		2
#define STB		3
#define GHWV	4
#define GSWV	5
#define SSM		7
#define SD		8
#define HWV		9
#define SWV		10
#define ESD		11
#define NACK 	126
#define ACK 	127

#define PAYLOAD 4
#define PAYLOAD_STS	7
#define PAYLOAD_SCS 2
#define PAYLOAD_STB 4
#define PAYLOAD_GHWV 0
#define PAYLOAD_GSWV 0
#define PAYLOAD_SSM	5
#define PAYLOAD_SD_HIGH 2
#define PAYLOAD_SD_LOW 255
#define PAYLOAD_SD 512
#define PAYLOAD_HWV 4
#define PAYLOAD_SWV 4
#define PAYLOAD_NACK 1
#define PAYLOAD_ACK 0

#define MAJOR_VERSION 3
#define MINOR_VERSION 0
#define SUB_VERSION 2

#define CH1	0
#define CH2	1
#define BOTH 3

#define HEADER 5
#define CRC 2
#define FIX_FIELDS	6
#define ZERO	0
#define BITNUMBER 8

#define DEVICE 0
#define OSCILLOSCOPE 1
#define SIGNAL_GENERATOR 2

#define NACK_WARNING	1
#define NACK_NOT_SUPPORTED	2
#define NACK_UNKNOWN_COMMAND 3
#define NACK_FAILED 4
#define NACK_OVERFLOW 5
#define SUCCESS 0

#define OUTOFRANGE 2
#define INRANGE 1
#define TRIGGER 2
#define NOT_TRIGGER 1
#define TRANSMIT 2
#define NOT_TRANSMIT 1
#define ON 2
#define OFF 1
#define ERROR 1
#define SAMPLE 2
#define ADC 512

#define RISING1 GPIO_PIN_4
#define RISING2 GPIO_PIN_5
#define FALLING 0
#define DISABLE 3

#define RISING_EDGE 0
#define FALLING_EDGE 1
#define BOTH_EDGES 2
#define DISABLE_TRIGGER 3
#define AUTO_MODE 0
#define NORMAL_MODE 1

#define SIZE_STS 15
#define SIZE_SCS 10
#define SIZE_STB 12
#define SIZE_GHWV 6
#define SIZE_GSWV 6
#define SIZE_SSM 13
#define SIZE_SD 520
#define SIZE_HWV 12
#define SIZE_SWV 12
#define SIZE_NACK 10
#define SIZE_ACK 6

#define RTS 0
#define ETS 1

/*********************************nachbessern!!!! **************/
#define RoundHalf 1
#define RoundOne 3
#define RoundTwoHalf 9
#define RoundFive 19
#define RoundTen 39
#define RoundTwenty 79
#define RoundFifthy 199

#define HALF_Ms 2
#define ONE_Ms 4
#define TWOHALF_Ms 10
#define FIVE_Ms 20
#define TEN_Ms 40
#define TWENTY_Ms 80
#define FIFTHY_Ms 200

//#define RoundQuarter 2
//#define RoundHalf 4
//#define RoundOne 9
//#define RoundTwoHalf 24
//#define RoundFive 49
//#define RoundTen 99
//#define RoundTwenty 199
//#define RoundFifthy 499
//
//#define QUARTER_Ms 3
//#define HALF_Ms 5
//#define ONE_Ms 10
//#define TWOHALF_Ms 25
//#define FIVE_Ms 50
//#define TEN_Ms 100
//#define TWENTY_Ms 200
//#define FIFTHY_Ms 500

#define ETSsampleOne_HH 0
#define ETSsampleOne_H 0
#define ETSsampleOne_L 0
#define ETSsampleOne_LL 0
#define ETSsampleTwoHalf_HH 0
#define ETSsampleTwoHalf_H 0
#define ETSsampleTwoHalf_L 0
#define ETSsampleTwoHalf_LL 0
#define ETSsampleFive_HH 0
#define ETSsampleFive_H 0
#define ETSsampleFive_L 0
#define ETSsampleFive_LL 0
#define ETSsampleTen_HH 0
#define ETSsampleTen_H 0
#define ETSsampleTen_L 0
#define ETSsampleTen_LL 0
#define ETSsampleTwenty_HH 0
#define ETSsampleTwenty_H 0
#define ETSsampleTwenty_L 0
#define ETSsampleTwenty_LL 0
#define ETSsampleFifthy_HH 0
#define ETSsampleFifthy_H 0
#define ETSsampleFifthy_L 0
#define ETSsampleFifthy_LL 0

struct values receive_package(/*char */uint8_t data);
void enableFunction(void);
void disableFunction(void);
//int16_t receive_package(void);

void setSTS(void);
void setSCS(void);
void setSTB(void);
void setSSM(void);
void setHWV(void);
void setSWV(void);
void setNACK(void);
void setACK(void);
void setSD1(void);
void setSD2(void);
void setEDS1(void);
void setEDS2(void);

void convertItoa(void);
void process(void);
void clear_package(void);
void sort_package(void);
void HeaderCrcReceive(void);
//struct values HeaderCrcTransmit(struct values *pointer);
void PayloadCrcNack(void);
void set_reply(void);

//extern uint8_t adc1[65535];
//extern uint8_t adc2[65535];

struct values {
	uint8_t transmit_size;
	uint8_t transmit;
	uint16_t size;
	uint8_t error;
	uint8_t receive;
	uint8_t command;
	uint8_t device;
	uint8_t channel;
	uint8_t channel1;
	uint8_t channel2;
	uint8_t payload_low;
	uint8_t payload_high;
	uint8_t payload_ets_low;
	uint8_t payload_ets_high;
	uint8_t packetNumber_HH;
	uint8_t packetNumber_H;
	uint8_t packetNumber_L;
	uint8_t packetNumber_LL;
	uint8_t crc_receive_low;
	uint8_t crc_receive_high;
	uint8_t crc_header[520];
	uint8_t crc_low;
	uint8_t crc_high;
	uint8_t reply[20];
	uint8_t package[20];
	uint8_t edge;
	uint32_t RTMSsamples;
//	uint16_t SETSsamples;
//	uint16_t SETSrounds;
	uint8_t replySD[522];
	uint8_t replyESD[500];
	uint16_t transmit_sizeSD;
	uint8_t transmitSD;
	uint16_t transmit_sizeESD;
	uint16_t transmitESD;
	uint8_t coupling;
	uint16_t pwm;
	uint8_t pwm_low;
	uint8_t pwm_high;
	uint8_t etsSampleHigh;
	uint8_t etsSampleLow;
	uint32_t loadValue;
};


#endif /* SRC_TIVA_OPENLAB_OSCILLOSCOPE_USB_INC_PROTOKOLL_H_ */
