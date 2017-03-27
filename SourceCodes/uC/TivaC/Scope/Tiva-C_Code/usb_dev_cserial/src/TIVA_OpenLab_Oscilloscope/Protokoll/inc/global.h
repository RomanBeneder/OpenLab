/*
 * global.h
 *
 *  Created on: 13.10.2016
 *      Author: HP
 */


#ifdef PROTOKOLL
#define EXTERN
#else
#define EXTERN extern
#endif

#include "usblib/usblib.h"

EXTERN volatile uint8_t adc1[2048];
EXTERN volatile uint8_t adc2[2048];
EXTERN volatile uint8_t adc1ETS[2048];
EXTERN volatile uint8_t sampleData1;
EXTERN volatile uint8_t sampleData2;
EXTERN volatile uint8_t channel1;
EXTERN volatile uint8_t channel2;
EXTERN volatile uint8_t triggerCH0;
EXTERN volatile uint8_t triggerCH1;
EXTERN volatile uint8_t markChannel1;
EXTERN volatile uint8_t markChannel2;
EXTERN volatile uint8_t adcTimer1;
EXTERN volatile uint8_t adcTimer2;
//EXTERN uint8_t adcInt1;
//EXTERN uint8_t adcInt2;
EXTERN volatile uint8_t trigger1;
EXTERN volatile uint8_t trigger2;
EXTERN volatile uint8_t triggerChannel;
EXTERN volatile uint16_t actualRounds;
EXTERN volatile uint16_t SETSrounds;
EXTERN volatile uint16_t SETSsamples;
//EXTERN uint16_t state1;
//EXTERN uint8_t state2;
EXTERN volatile uint8_t packet1;
EXTERN uint8_t packet2;
EXTERN volatile uint8_t packet1ETS;
EXTERN volatile uint8_t packet2ETS;
EXTERN volatile uint8_t Intstate1;
EXTERN volatile uint8_t Intstate2;
EXTERN volatile uint8_t Timerstate1;
EXTERN volatile uint8_t Timerstate2;
EXTERN volatile uint8_t GPIOstate1;
EXTERN volatile uint8_t GPIOstate2;
EXTERN volatile uint8_t pwm_high;
EXTERN volatile uint8_t pwm_low;
EXTERN volatile uint8_t sample_mode;
EXTERN volatile uint16_t sampleCH1;
EXTERN volatile uint16_t sampleCH2;
EXTERN volatile uint32_t LoadValue;
EXTERN volatile uint8_t edge1;
EXTERN volatile uint8_t edge2;
EXTERN volatile uint8_t rounds1;
EXTERN volatile uint8_t rounds2;

EXTERN const tUSBBuffer *pBufferSend;


//#endif /* GLOBAL_H_ */
