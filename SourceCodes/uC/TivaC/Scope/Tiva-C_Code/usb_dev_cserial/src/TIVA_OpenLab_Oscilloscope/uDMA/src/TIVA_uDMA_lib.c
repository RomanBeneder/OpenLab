/*
 * TIVA_uDMA_lib.c
 *
 *  Created on: 27.07.2015
 *      Author: Markus Lechner
 */

/***************************************************************************** LIBRARIES*/
#include "TIVA_uDMA_lib.h"
#include "TIVA_uDMA_driver.h"
/***************************************************************************** DMA CONTROL TABLE*/
// The control table used by the uDMA controller.  This table must be aligned
// to a 1024 byte boundary.
#if defined(ewarm)
#pragma data_alignment=1024
uint8_t pui8ControlTable[1024];
#elif defined(ccs)
#pragma DATA_ALIGN(pui8ControlTable, 1024)
uint8_t pui8ControlTable[1024];
#else
uint8_t pui8ControlTable[1024] __attribute__ ((aligned(1024)));
#endif
/***************************************************************************** ADC RESULT */
static uint32_t ADC_VALUE_PRI[1024] = {0};
static uint32_t ADC_VALUE_ALT[1024] = {0};

static uint8_t ADC_VALUE_PRI_TXD[1024] = {0};
static uint8_t ADC_VALUE_ALT_TXD[1024] = {0};
/***************************************************************************** FUNCTIONS */

uint16_t _TIVA_uDMA_Configuration(void)
{
	_TIVA_uDMA_Peripheral_Configuration();
	_TIVA_uDMA_Control_Configuration();

	_TIVA_uDMA_ADC_Channel_Configuration();

	_TIVA_uDMA_Interrupt_Configuration();

	return SUCCESS;
}

uint16_t _TIVA_uDMA_Peripheral_Configuration(void)
{
	SysCtlPeripheralReset(SYSCTL_PERIPH_UDMA);
	SysCtlPeripheralEnable(SYSCTL_PERIPH_UDMA);
	SysCtlDelay(5);

	return SUCCESS;
}

uint16_t _TIVA_uDMA_Control_Configuration(void)
{
	//Enable the uDMA controller
	uDMAEnable();

	//Set up the channel control table
	uDMAControlBaseSet(pui8ControlTable);

	return SUCCESS;
}

uint16_t _TIVA_uDMA_ADC_Channel_Configuration(void)
{
	//Channel assign to ADC1 Channel 26
	uDMAChannelDisable(UDMA_CH27_ADC1_3);
	SysCtlDelay(5);
	uDMAChannelAssign(UDMA_CH27_ADC1_3);

	//Disable all attributes -> allow also single transfer
	uDMAChannelAttributeDisable(UDMA_SEC_CHANNEL_ADC13, UDMA_ATTR_ALL);

	//uDMAChannelAttributeEnable(UDMA_CHANNEL_ADC3, UDMA_ATTR_USEBURST);

	//Configuration for primary control structure
	//Transfer size: 32 Bit, No source increment, Burst mode activated
	//Arbitration size: 1024
	//Desitination increment (32 Bit)
	uDMAChannelControlSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_PRI_SELECT),
						  UDMA_SIZE_32 |
						  UDMA_SRC_INC_NONE |
						  UDMA_NEXT_USEBURST |
						  UDMA_ARB_1024 |
						  UDMA_DST_INC_32);

	//Configuration for alternate control structure
	//Transfer size: 32 Bit, No source increment, Burst mode activated
	//Arbitration size: 1024
	//Desitination increment (32 Bit)
	uDMAChannelControlSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_ALT_SELECT),
						  UDMA_SIZE_32 |
						  UDMA_SRC_INC_NONE |
						  UDMA_NEXT_USEBURST |
						  UDMA_ARB_1024 |
						  UDMA_DST_INC_32);

	//Transfer configuration for primary control structure
	//Selected mode: PING-PONG, Source address: Sample Sequencer2 - FIFO2
	//Destination address: ADC_value (uint32_t ADC_value[1024];)
	//Transfer size 1024
	uDMAChannelTransferSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_PRI_SELECT),
						   UDMA_MODE_PINGPONG,
						  (void *)(ADC1_BASE + ADC_O_SSFIFO3),
						  ADC_VALUE_PRI,
						  TRANSFER_SIZE_1024);

	//Transfer configuration for alternate control structure
	//Selected mode: PING-PONG, Source address: Sample Sequencer2 - FIFO2
	//Destination address: ADC_value1 (uint32_t ADC_value1[1024];)
	//Transfer size 1024
	uDMAChannelTransferSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_ALT_SELECT),
						   UDMA_MODE_PINGPONG,
						  (void *)(ADC1_BASE + ADC_O_SSFIFO3),
						  ADC_VALUE_ALT,
						  TRANSFER_SIZE_1024);

	uDMAChannelEnable(UDMA_SEC_CHANNEL_ADC13);

	return SUCCESS;
}

uint16_t _TIVA_uDMA_Interrupt_Configuration(void)
{
	//ISR in case of DMA error
	IntEnable(INT_UDMAERR);

	return SUCCESS;
}

void ADC1_SEQ3_ISR(void)
{
	uint16_t i = 0;

	ADCIntClearEx(ADC1_BASE, (ADC_INT_DMA_SS3 | ADC_INT_SS3) );

	GPIO_PORTN_DATA_R |= 0x01;

	//Check whether the primary control structure stopped
	if(uDMAChannelModeGet(UDMA_SEC_CHANNEL_ADC13 | UDMA_PRI_SELECT) == UDMA_MODE_STOP)
	{
		//Reload primary structure
		uDMAChannelTransferSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_PRI_SELECT),
							   UDMA_MODE_PINGPONG,
							  (void *)(ADC1_BASE + ADC_O_SSFIFO3),
							  ADC_VALUE_PRI,
							  TRANSFER_SIZE_1024);

		//Remove the last 4 Bit of ADC result -> ADC resolution: 8 Bit
//		for(i=0; i<=1023; i++)
		for(i=0; i<=1023; i++)
		{
//			ADC_VALUE_PRI_TXD[i] = (ADC_VALUE_PRI[i] >> 4);
			adc1[i] = (ADC_VALUE_PRI[i] >> 4);
		}

//		if(uDMAChannelModeGet(UDMA_SEC_CHANNEL_UART2TX_1 | UDMA_PRI_SELECT) == UDMA_MODE_STOP)
//		{
//			uDMAChannelTransferSet((UDMA_SEC_CHANNEL_UART2TX_1 | UDMA_PRI_SELECT),
//								   UDMA_MODE_PINGPONG,
//								   ADC_VALUE_PRI_TXD,
//								  (void *)(UART2_BASE),
//								  TRANSFER_SIZE_1024);
//		}
	}

	//Check whether the alternate control structure stopped
	if(uDMAChannelModeGet(UDMA_SEC_CHANNEL_ADC13 | UDMA_ALT_SELECT) == UDMA_MODE_STOP)
	{
		//Reload alternate structure
		uDMAChannelTransferSet((UDMA_SEC_CHANNEL_ADC13 | UDMA_ALT_SELECT),
							   UDMA_MODE_PINGPONG,
							  (void *)(ADC1_BASE + ADC_O_SSFIFO3),
							  ADC_VALUE_ALT,
							  TRANSFER_SIZE_1024);

		//Remove the last 4 Bit of ADC result -> ADC resolution: 8 Bit
		for(i=0; i<=1023; i++)
		{
			ADC_VALUE_ALT_TXD[i] = (ADC_VALUE_ALT[i] >> 4);
		}

//		if(uDMAChannelModeGet(UDMA_SEC_CHANNEL_UART2TX_1 | UDMA_ALT_SELECT) == UDMA_MODE_STOP)
//		{
//			uDMAChannelTransferSet((UDMA_SEC_CHANNEL_UART2TX_1 | UDMA_ALT_SELECT),
//								   UDMA_MODE_PINGPONG,
//								   ADC_VALUE_ALT_TXD,
//								  (void *)(UART2_BASE),
//								  TRANSFER_SIZE_1024);
//		}
	}

	//Check whether the currently used channel is deactivated
	if(uDMAChannelIsEnabled(UDMA_SEC_CHANNEL_ADC13)  == false)
	{
//		uDMAChannelEnable(UDMA_SEC_CHANNEL_ADC12);
		ADCIntDisableEx(ADC1_BASE, ADC_INT_DMA_SS3);
		IntDisable(INT_ADC1SS3);
		packet1ETS = TRANSMIT;

//		_TIVA_UART_Send("?");
	}

	//Check whether the currently used channel is deactivated
//	if(uDMAChannelIsEnabled(UDMA_SEC_CHANNEL_UART2TX_1)  == false)
//	{
//		uDMAChannelEnable(UDMA_SEC_CHANNEL_UART2TX_1);
//
//		_TIVA_UART_Send("?");
//	}

	GPIO_PORTN_DATA_R &= ~(0x01);

	return;
}

void uDMA_Error_ISR(void)
{
//	_TIVA_UART_Send("uDMA BUS FAULT!");

	while(1)
	{
	}

}

void _TIVA_Reboot_Microcontroller(void)
{
	_TIVA_Reboot();

	return;
}


/* EOF */
