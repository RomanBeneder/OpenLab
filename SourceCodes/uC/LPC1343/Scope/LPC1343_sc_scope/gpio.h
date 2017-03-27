/**
 * 	@file		gpio.h
 *	@author		Christian Matar
 *	@brief		GPIO header file
 */

#ifndef GPIO_H_
#define GPIO_H_

#include "LPC13xx.h"
#include <stdint.h>


#define GPIO_OUTPUT_LOW		0
#define GPIO_OUTPUT_HIGH	1
#define GPIO_DIR_INPUT		0
#define GPIO_DIR_OUTPUT		1

/**
 * 	@brief	If you are using high-active LEDs, set this constant to 0.
 */
#define LOW_ACTIVE_LED		1

/**
 * 	@brief		Function GPIO_SetDirection() sets a GPIO pin as input or output.
 * 	@param		gpio register the pin belongs to.
 * 	@param 		pin to be set.
 *	@param		dir the direction to be set:
 *				0 --> INPUT
 *				1 --> OUTPUT
 */
void GPIO_SetDirection(LPC_GPIO_TypeDef *gpio, uint8_t pin, uint8_t dir);

/**
 *	@brief		Function GPIO_SetOutputLevel() sets a GPIO pin's output level.
 *	@param		gpio register the pin belongs to.
 *	@param		pin to be set.
 *	@param 		level the output level
 *				0 --> LOW
 *				1 --> HIGH
 */
void GPIO_SetOutputLevel(LPC_GPIO_TypeDef *gpio, uint8_t pin, uint8_t level);

/**
 * 	@brief		Function GPIO_ToggleOutput toggles a GPIO pin's output level.
 *	@param		gpio register the pin belongs to.
 *	@param		pin to be set.
 */
void GPIO_ToggleOutput(LPC_GPIO_TypeDef *gpio, uint8_t pin);

/**
 *	@brief		Function GPIO_GetPinLevel() reads a GPIO pin's data register.
 *				It returns the pin's current logic level.
 *	@param		gpio register the pin belongs to.
 *	@param		pin to be read.
 *	@return		0 --> LOW
 *				1 --> HIGH
 */
uint32_t GPIO_GetPinLevel(LPC_GPIO_TypeDef *gpio, uint8_t pin);

/**
 * 	@brief		Function GPIO_TurnOnLED() turns a given LED on.
 * 	@param		gpio register the pin belongs to.
 * 	@param		pin	the led is connected with.
 * 	@note		Set the constant LOW_ACTIVE_LED according to your LEDs.
 */
void GPIO_TurnOnLED(LPC_GPIO_TypeDef *gpio, uint8_t pin);

/**
 * 	@brief		Function GPIO_TurnOffLED() turns a given LED on.
 * 	@param		gpio register the pin belongs to.
 * 	@param		pin	the led is connected with.
 * 	@note		Set the constant LOW_ACTIVE_LED according to your LEDs.
 */
void GPIO_TurnOffLED(LPC_GPIO_TypeDef *gpio, uint8_t pin);


#endif /* GPIO_H_ */
