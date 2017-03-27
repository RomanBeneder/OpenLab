/**
 * 	@file		gpio.h
 *	@author		Christian Matar
 *	@brief		GPIO header file
 */

#include "gpio.h"

void GPIO_SetDirection(LPC_GPIO_TypeDef *gpio, uint8_t pin, uint8_t dir)
{
	if(dir)
		gpio->DIR |= 1<<pin;
	else
		gpio->DIR &= ~(1<<pin);
}

void GPIO_SetOutputLevel(LPC_GPIO_TypeDef *gpio, uint8_t pin, uint8_t level)
{
	GPIO_SetDirection(gpio, pin, GPIO_DIR_OUTPUT);
	if(level)
		gpio->DATA |= 1<<pin;
	else
		gpio->DATA &= ~(1<<pin);
}

void GPIO_ToggleOutput(LPC_GPIO_TypeDef *gpio, uint8_t pin)
{
	if(GPIO_GetPinLevel(gpio, pin))
		GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_LOW);
	else
		GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_HIGH);
}

uint32_t GPIO_GetPinLevel(LPC_GPIO_TypeDef *gpio, uint8_t pin)
{
	return gpio->DATA & (1<<pin);
}

void GPIO_TurnOnLED(LPC_GPIO_TypeDef *gpio, uint8_t pin)
{
#if LOW_ACTIVE_LED
	GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_LOW);
#else
	GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_HIGH);
#endif
}

void GPIO_TurnOffLED(LPC_GPIO_TypeDef *gpio, uint8_t pin)
{
#if LOW_ACTIVE_LED
	GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_HIGH);
#else
	GPIO_SetOutputLevel(gpio, pin, GPIO_OUTPUT_LOW);
#endif
}
