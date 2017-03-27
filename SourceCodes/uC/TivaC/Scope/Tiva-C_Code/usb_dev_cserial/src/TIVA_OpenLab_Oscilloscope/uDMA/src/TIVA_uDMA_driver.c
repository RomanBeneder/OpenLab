/*
 * TIVA_uDMA_driver.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

/***************************************************************************** LIBRARIES*/
#include "TIVA_uDMA_driver.h"
/***************************************************************************** FUNCTIONS */

void _TIVA_Reboot(void)
{
	HWREG(NVIC_APINT) = (NVIC_APINT_VECTKEY | NVIC_APINT_SYSRESETREQ);

	return;
}

