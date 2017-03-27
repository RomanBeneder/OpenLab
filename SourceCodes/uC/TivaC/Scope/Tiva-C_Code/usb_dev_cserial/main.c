#include <stdint.h>
#include <stdbool.h>
#include "driverlib/timer.h"
//#include "TIVA_ADC_driver.h"
//#include "TIVA_USB_lib.h"
//#include "TIVA_GPIO_driver.h"
//#include "TIVA_PWM_driver.h"
//#include "TIVA_Timer_driver.h"
#include "openlab_embsys_scope.h"
#include "Protokoll.h"
#include "global.h"

int main(void)
{
//	uint8_t retError;
	rounds1 = rounds2 = 1;
	adcTimer1 = adcTimer2 = OFF;
//	adcInt1 = adcInt2 = OFF;

//	retError = _TIVA_USB_Configuration();
//	if(retError != SUCCESS)
//		printf("ERROR USB Configuration!\n");
//
//	retError = _TIVA_GPIO_Configuration();
//	if(retError != SUCCESS)
//		printf("ERROR GPIO Configuration!\n");
//
//	retError = _TIVA_PWM_Configuration();
//	if(retError != SUCCESS)
//		printf("ERROR ADC Configuration!\n");
//
//	retError = _TIVA_Timer_Configuration();
//	if(retError != SUCCESS)
//		printf("ERROR TIMER Configuration!\n");
//
//	retError = _TIVA_ADC_Configuration();
//	if(retError != SUCCESS)
//		printf("ERROR ADC Configuration!\n");

	// Set the clocking to run directly from the crystal at 120MHz.
		SysCtlClockFreqSet((SYSCTL_XTAL_25MHZ |
							SYSCTL_OSC_MAIN |
							SYSCTL_USE_PLL |
							SYSCTL_CFG_VCO_480), 120000000);

		_TIVA_Oscilloscope_Configuration();


	while(1)
	{
//		ADCProcessorTrigger(ADC1_BASE, 1);
//		ADCProcessorTrigger(ADC0_BASE, 2);
		TimerControlTrigger(TIMER0_BASE, TIMER_A, true);
		TimerControlTrigger(TIMER1_BASE, TIMER_A, true);
		TimerControlTrigger(TIMER2_BASE, TIMER_A, true);
		TimerControlTrigger(TIMER3_BASE, TIMER_A, true);
//		TimerControlTrigger(TIMER4_BASE, TIMER_A, true);
		if(sampleData1 == TRANSMIT && channel1 == ON && packet1 == TRANSMIT)
			setSD1();
		if(sampleData2 == TRANSMIT && channel2 == ON && packet2 == TRANSMIT)
			setSD2();
		if(sampleData1 == TRANSMIT && channel1 == ON && packet1ETS == TRANSMIT)
			setEDS1();
		if(sampleData2 == TRANSMIT && channel2 == ON && packet2ETS == TRANSMIT)
			setEDS2();
	}
}
