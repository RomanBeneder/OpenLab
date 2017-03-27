#include "LPC13xx.h"                        /* LPC13xx definitions */

#include "type.h"

#include "main.h"

#include "gpio.h"
#include "main.h"
#include "pwm.h"
#include "crc.h"
#include "commands.h"
#include "timer.h"
#include "uart.h"


void initAll()
{
	initTimer(LPC_TMR32B0, TIMER_PRESCALER_VAL, TIMER_MATCH_VAL);
	initTimer(LPC_TMR32B1, TIMER_PRESCALER_VAL, TIMER_MATCH_VAL);
	initPWM(LPC_TMR16B1, PWM_PRESCALER_VAL, PWM_MATCH_3_VAL, PWM_MATCH_REG_0, 25.0);
	initPWM(LPC_TMR16B1, PWM_PRESCALER_VAL, PWM_MATCH_3_VAL, PWM_MATCH_REG_1, 25.0);
	UART_init(LPC_UART);
}

/*------------------------------------------------------------------------------
  Main Program
 *------------------------------------------------------------------------------*/
int main (void)
{
	LPC_SYSCON->SYSAHBCLKCTRL |= 1 << 6;        /* Enable clock for GPIO        */

	SystemInit();                               /* initialize clocks */

	initAll();

	while (1)
	{

	}
}

