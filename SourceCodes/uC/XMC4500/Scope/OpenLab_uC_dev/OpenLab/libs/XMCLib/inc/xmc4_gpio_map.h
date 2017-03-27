/**
 * @file xmc4_gpio_map.h
 * @date 2015-06-20
 *
 * @cond
  *********************************************************************************************************************
 * XMClib v2.0.0 - XMC Peripheral Driver Library
 *
 * Copyright (c) 2015, Infineon Technologies AG
 * All rights reserved.                        
 *                                             
 * Redistribution and use in source and binary forms, with or without modification,are permitted provided that the 
 * following conditions are met:   
 *                                                                              
 * Redistributions of source code must retain the above copyright notice, this list of conditions and the following 
 * disclaimer.                        
 * 
 * Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following 
 * disclaimer in the documentation and/or other materials provided with the distribution.                       
 * 
 * Neither the name of the copyright holders nor the names of its contributors may be used to endorse or promote 
 * products derived from this software without specific prior written permission.                                           
 *                                                                              
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, 
 * INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE  
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE  FOR ANY DIRECT, INDIRECT, INCIDENTAL, 
 * SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR  
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, 
 * WHETHER IN CONTRACT, STRICT LIABILITY,OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE 
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.                                                  
 *                                                                              
 * To improve the quality of the software, users are encouraged to share modifications, enhancements or bug fixes with 
 * Infineon Technologies AG dave@infineon.com).                                                          
 *********************************************************************************************************************
 *
 * Change History
 * --------------
 *
 * 2015-02-20:
 *     - Initial draft<br>
 *      
 * 2015-06-20:
 *     - Updated copyright information
 *
 * @endcond
 *
 * @brief XMC pin mapping definitions
 */

#ifndef XMC4_GPIO_MAP_H
#define XMC4_GPIO_MAP_H

#if (UC_DEVICE == XMC4100) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#endif


#if (UC_DEVICE == XMC4100) && (UC_PACKAGE == VQFN48)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#endif


#if (UC_DEVICE == XMC4104) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#endif


#if (UC_DEVICE == XMC4104) && (UC_PACKAGE == VQFN48)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#endif


#if (UC_DEVICE == XMC4108) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#endif


#if (UC_DEVICE == XMC4200) && (UC_PACKAGE == VQFN48)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#endif


#if (UC_DEVICE == XMC4200) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#endif


#if (UC_DEVICE == XMC4200) && (UC_PACKAGE == VQFN48)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#endif


#if (UC_DEVICE == XMC4400) && (UC_PACKAGE == LQFP100)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_7	XMC_GPIO_PORT5, 7
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_ETH0_TX_EN  	(0x8UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_ETH0_TXD0   	(0x8UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_ETH0_TXD1   	(0x8UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_ETH0_MDC   	(0x8UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_10_AF_ETH0_MDC   	(0x8UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_ETH0_TX_EN 	(0x8UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_ETH0_TXD0  	(0x8UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_ETH0_TXD1  	(0x8UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_ETH0_TX_EN  	(0x8UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_ETH0_MDC    	(0x8UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_ETH0_TXD0   	(0x8UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_ETH0_TXD1   	(0x8UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#endif


#if (UC_DEVICE == XMC4400) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_ETH0_TX_EN  	(0x8UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_ETH0_TXD0   	(0x8UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_ETH0_TXD1   	(0x8UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_ETH0_MDC   	(0x8UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_ETH0_TX_EN  	(0x8UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_ETH0_MDC    	(0x8UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_ETH0_TXD0   	(0x8UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_ETH0_TXD1   	(0x8UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#endif


#if (UC_DEVICE == XMC4402) && (UC_PACKAGE == LQFP100)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_7	XMC_GPIO_PORT5, 7
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_7_AF_U1C1_SELO2  	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#endif


#if (UC_DEVICE == XMC4402) && (UC_PACKAGE == LQFP64)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_14	XMC_GPIO_PORT14, 14


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_2_AF_HRPWM0_OUT01	(0x20UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_3_AF_HRPWM0_OUT20	(0x20UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_4_AF_HRPWM0_OUT21	(0x20UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_5_AF_HRPWM0_OUT00	(0x20UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_6_AF_HRPWM0_OUT30	(0x20UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_7_AF_HRPWM0_OUT11	(0x20UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_8_AF_HRPWM0_OUT10	(0x20UL)
#define P0_9_AF_HRPWM0_OUT31	(0x8UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U1C1_SCLKOUT	(0x20UL)
#define P1_9_AF_U0C0_SCLKOUT	(0x8UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_U1C1_DOUT0  	(0x20UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P1_15_AF_U1C0_DOUT0 	(0x20UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#endif


#if (UC_DEVICE == XMC4500) && (UC_PACKAGE == BGA144)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P0_13	XMC_GPIO_PORT0, 13
#define P0_14	XMC_GPIO_PORT0, 14
#define P0_15	XMC_GPIO_PORT0, 15
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_11	XMC_GPIO_PORT2, 11
#define P2_12	XMC_GPIO_PORT2, 12
#define P2_13	XMC_GPIO_PORT2, 13
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P3_7	XMC_GPIO_PORT3, 7
#define P3_8	XMC_GPIO_PORT3, 8
#define P3_9	XMC_GPIO_PORT3, 9
#define P3_10	XMC_GPIO_PORT3, 10
#define P3_11	XMC_GPIO_PORT3, 11
#define P3_12	XMC_GPIO_PORT3, 12
#define P3_13	XMC_GPIO_PORT3, 13
#define P3_14	XMC_GPIO_PORT3, 14
#define P3_15	XMC_GPIO_PORT3, 15
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P4_2	XMC_GPIO_PORT4, 2
#define P4_3	XMC_GPIO_PORT4, 3
#define P4_4	XMC_GPIO_PORT4, 4
#define P4_5	XMC_GPIO_PORT4, 5
#define P4_6	XMC_GPIO_PORT4, 6
#define P4_7	XMC_GPIO_PORT4, 7
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_3	XMC_GPIO_PORT5, 3
#define P5_4	XMC_GPIO_PORT5, 4
#define P5_5	XMC_GPIO_PORT5, 5
#define P5_6	XMC_GPIO_PORT5, 6
#define P5_7	XMC_GPIO_PORT5, 7
#define P5_8	XMC_GPIO_PORT5, 8
#define P5_9	XMC_GPIO_PORT5, 9
#define P5_10	XMC_GPIO_PORT5, 10
#define P5_11	XMC_GPIO_PORT5, 11
#define P6_0	XMC_GPIO_PORT6, 0
#define P6_1	XMC_GPIO_PORT6, 1
#define P6_2	XMC_GPIO_PORT6, 2
#define P6_3	XMC_GPIO_PORT6, 3
#define P6_4	XMC_GPIO_PORT6, 4
#define P6_5	XMC_GPIO_PORT6, 5
#define P6_6	XMC_GPIO_PORT6, 6
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_4	XMC_GPIO_PORT15, 4
#define P15_5	XMC_GPIO_PORT15, 5
#define P15_6	XMC_GPIO_PORT15, 6
#define P15_7	XMC_GPIO_PORT15, 7
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9
#define P15_12	XMC_GPIO_PORT15, 12
#define P15_13	XMC_GPIO_PORT15, 13
#define P15_14	XMC_GPIO_PORT15, 14
#define P15_15	XMC_GPIO_PORT15, 15


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_ETH0_TX_EN  	(0x8UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_ETH0_TXD0   	(0x8UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_ETH0_TXD1   	(0x8UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_ETH0_MDC   	(0x8UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P0_13_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_13_AF_CCU40_OUT2 	(0x18UL)
#define P0_14_AF_U1C0_SELO1 	(0x10UL)
#define P0_14_AF_CCU40_OUT1 	(0x18UL)
#define P0_15_AF_U1C0_SELO2 	(0x10UL)
#define P0_15_AF_CCU40_OUT0 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_CAN_N2_TXD  	(0x10UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_ETH0_MDC   	(0x8UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_ETH0_TX_EN 	(0x8UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_ETH0_TXD0  	(0x8UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_ETH0_TXD1  	(0x8UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_ETH0_TX_EN  	(0x8UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_ETH0_MDC    	(0x8UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_ETH0_TXD0   	(0x8UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_ETH0_TXD1   	(0x8UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_11_AF_ETH0_TXER  	(0x8UL)
#define P2_11_AF_CCU80_OUT22	(0x18UL)
#define P2_12_AF_ETH0_TXD2  	(0x8UL)
#define P2_12_AF_CCU81_OUT33	(0x18UL)
#define P2_12_AF_ETH0_TXD0  	(0x20UL)
#define P2_13_AF_ETH0_TXD3  	(0x8UL)
#define P2_13_AF_ETH0_TXD1  	(0x20UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P3_7_AF_CAN_N2_TXD  	(0x10UL)
#define P3_7_AF_CCU41_OUT3  	(0x18UL)
#define P3_7_AF_LEDTS0_LINE0	(0x20UL)
#define P3_8_AF_U2C0_DOUT0  	(0x8UL)
#define P3_8_AF_U0C1_SELO3  	(0x10UL)
#define P3_8_AF_CCU41_OUT2  	(0x18UL)
#define P3_8_AF_LEDTS0_LINE1	(0x20UL)
#define P3_9_AF_U2C0_SCLKOUT	(0x8UL)
#define P3_9_AF_CAN_N1_TXD  	(0x10UL)
#define P3_9_AF_CCU41_OUT1  	(0x18UL)
#define P3_9_AF_LEDTS0_LINE2	(0x20UL)
#define P3_10_AF_U2C0_SELO0 	(0x8UL)
#define P3_10_AF_CAN_N0_TXD 	(0x10UL)
#define P3_10_AF_CCU41_OUT0 	(0x18UL)
#define P3_10_AF_LEDTS0_LINE3	(0x20UL)
#define P3_11_AF_U2C1_DOUT0 	(0x8UL)
#define P3_11_AF_U0C1_SELO2 	(0x10UL)
#define P3_11_AF_CCU42_OUT3 	(0x18UL)
#define P3_11_AF_LEDTS0_LINE4	(0x20UL)
#define P3_12_AF_U0C1_SELO1 	(0x10UL)
#define P3_12_AF_CCU42_OUT2 	(0x18UL)
#define P3_12_AF_LEDTS0_LINE5	(0x20UL)
#define P3_13_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_13_AF_U0C1_DOUT0 	(0x10UL)
#define P3_13_AF_CCU42_OUT1 	(0x18UL)
#define P3_13_AF_LEDTS0_LINE6	(0x20UL)
#define P3_14_AF_U1C0_SELO3 	(0x10UL)
#define P3_15_AF_U1C1_DOUT0 	(0x10UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P4_2_AF_U2C1_SELO1  	(0x8UL)
#define P4_2_AF_U1C1_DOUT0  	(0x10UL)
#define P4_2_AF_U2C1_SCLKOUT	(0x20UL)
#define P4_3_AF_U2C1_SELO2  	(0x8UL)
#define P4_3_AF_U0C0_SELO5  	(0x10UL)
#define P4_3_AF_CCU43_OUT3  	(0x18UL)
#define P4_4_AF_U0C0_SELO4  	(0x10UL)
#define P4_4_AF_CCU43_OUT2  	(0x18UL)
#define P4_5_AF_U0C0_SELO3  	(0x10UL)
#define P4_5_AF_CCU43_OUT1  	(0x18UL)
#define P4_6_AF_U0C0_SELO2  	(0x10UL)
#define P4_6_AF_CCU43_OUT0  	(0x18UL)
#define P4_7_AF_CAN_N2_TXD  	(0x10UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_3_AF_U2C0_SELO0  	(0x8UL)
#define P5_3_AF_CCU81_OUT22 	(0x18UL)
#define P5_4_AF_U2C0_SELO1  	(0x8UL)
#define P5_4_AF_CCU81_OUT13 	(0x18UL)
#define P5_5_AF_U2C0_SELO2  	(0x8UL)
#define P5_5_AF_CCU81_OUT12 	(0x18UL)
#define P5_6_AF_U2C0_SELO3  	(0x8UL)
#define P5_6_AF_CCU81_OUT03 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#define P5_8_AF_U1C0_SCLKOUT	(0x10UL)
#define P5_8_AF_CCU80_OUT01 	(0x18UL)
#define P5_9_AF_U1C0_SELO0  	(0x10UL)
#define P5_9_AF_CCU80_OUT20 	(0x18UL)
#define P5_9_AF_ETH0_TX_EN  	(0x20UL)
#define P5_10_AF_U1C0_MCLKOUT	(0x10UL)
#define P5_10_AF_CCU80_OUT10	(0x18UL)
#define P5_10_AF_LEDTS0_LINE7	(0x20UL)
#define P5_11_AF_U1C0_SELO1 	(0x10UL)
#define P5_11_AF_CCU80_OUT00	(0x18UL)
#define P6_0_AF_ETH0_TXD2   	(0x8UL)
#define P6_0_AF_U0C1_SELO1  	(0x10UL)
#define P6_0_AF_CCU81_OUT31 	(0x18UL)
#define P6_1_AF_ETH0_TXD3   	(0x8UL)
#define P6_1_AF_U0C1_SELO0  	(0x10UL)
#define P6_1_AF_CCU81_OUT30 	(0x18UL)
#define P6_2_AF_ETH0_TXER   	(0x8UL)
#define P6_2_AF_U0C1_SCLKOUT	(0x10UL)
#define P6_2_AF_CCU43_OUT3  	(0x18UL)
#define P6_3_AF_CCU43_OUT2  	(0x18UL)
#define P6_4_AF_U0C1_DOUT0  	(0x10UL)
#define P6_4_AF_CCU43_OUT1  	(0x18UL)
#define P6_5_AF_U0C1_MCLKOUT	(0x10UL)
#define P6_5_AF_CCU43_OUT0  	(0x18UL)
#define P6_6_AF_DSD_MCLKOUT 	(0x18UL)
#endif


#if (UC_DEVICE == XMC4500) && (UC_PACKAGE == LQFP100)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_7	XMC_GPIO_PORT5, 7
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_ETH0_TX_EN  	(0x8UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_ETH0_TXD0   	(0x8UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_ETH0_TXD1   	(0x8UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_ETH0_MDC   	(0x8UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_CAN_N2_TXD  	(0x10UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_ETH0_MDC   	(0x8UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_ETH0_TX_EN 	(0x8UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_ETH0_TXD0  	(0x8UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_ETH0_TXD1  	(0x8UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_ETH0_TX_EN  	(0x8UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_ETH0_MDC    	(0x8UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_ETH0_TXD0   	(0x8UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_ETH0_TXD1   	(0x8UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#endif


#if (UC_DEVICE == XMC4500) && (UC_PACKAGE == LQFP144)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P0_13	XMC_GPIO_PORT0, 13
#define P0_14	XMC_GPIO_PORT0, 14
#define P0_15	XMC_GPIO_PORT0, 15
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_11	XMC_GPIO_PORT2, 11
#define P2_12	XMC_GPIO_PORT2, 12
#define P2_13	XMC_GPIO_PORT2, 13
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P3_7	XMC_GPIO_PORT3, 7
#define P3_8	XMC_GPIO_PORT3, 8
#define P3_9	XMC_GPIO_PORT3, 9
#define P3_10	XMC_GPIO_PORT3, 10
#define P3_11	XMC_GPIO_PORT3, 11
#define P3_12	XMC_GPIO_PORT3, 12
#define P3_13	XMC_GPIO_PORT3, 13
#define P3_14	XMC_GPIO_PORT3, 14
#define P3_15	XMC_GPIO_PORT3, 15
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P4_2	XMC_GPIO_PORT4, 2
#define P4_3	XMC_GPIO_PORT4, 3
#define P4_4	XMC_GPIO_PORT4, 4
#define P4_5	XMC_GPIO_PORT4, 5
#define P4_6	XMC_GPIO_PORT4, 6
#define P4_7	XMC_GPIO_PORT4, 7
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_3	XMC_GPIO_PORT5, 3
#define P5_4	XMC_GPIO_PORT5, 4
#define P5_5	XMC_GPIO_PORT5, 5
#define P5_6	XMC_GPIO_PORT5, 6
#define P5_7	XMC_GPIO_PORT5, 7
#define P5_8	XMC_GPIO_PORT5, 8
#define P5_9	XMC_GPIO_PORT5, 9
#define P5_10	XMC_GPIO_PORT5, 10
#define P5_11	XMC_GPIO_PORT5, 11
#define P6_0	XMC_GPIO_PORT6, 0
#define P6_1	XMC_GPIO_PORT6, 1
#define P6_2	XMC_GPIO_PORT6, 2
#define P6_3	XMC_GPIO_PORT6, 3
#define P6_4	XMC_GPIO_PORT6, 4
#define P6_5	XMC_GPIO_PORT6, 5
#define P6_6	XMC_GPIO_PORT6, 6
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_4	XMC_GPIO_PORT15, 4
#define P15_5	XMC_GPIO_PORT15, 5
#define P15_6	XMC_GPIO_PORT15, 6
#define P15_7	XMC_GPIO_PORT15, 7
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9
#define P15_12	XMC_GPIO_PORT15, 12
#define P15_13	XMC_GPIO_PORT15, 13
#define P15_14	XMC_GPIO_PORT15, 14
#define P15_15	XMC_GPIO_PORT15, 15


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_ETH0_TX_EN  	(0x8UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_ETH0_TXD0   	(0x8UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_ETH0_TXD1   	(0x8UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_ETH0_MDC   	(0x8UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P0_13_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_13_AF_CCU40_OUT2 	(0x18UL)
#define P0_14_AF_U1C0_SELO1 	(0x10UL)
#define P0_14_AF_CCU40_OUT1 	(0x18UL)
#define P0_15_AF_U1C0_SELO2 	(0x10UL)
#define P0_15_AF_CCU40_OUT0 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_CAN_N2_TXD  	(0x10UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_ETH0_MDC   	(0x8UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_ETH0_TX_EN 	(0x8UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_ETH0_TXD0  	(0x8UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_ETH0_TXD1  	(0x8UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_ETH0_TX_EN  	(0x8UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_ETH0_MDC    	(0x8UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_ETH0_TXD0   	(0x8UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_ETH0_TXD1   	(0x8UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_11_AF_ETH0_TXER  	(0x8UL)
#define P2_11_AF_CCU80_OUT22	(0x18UL)
#define P2_12_AF_ETH0_TXD2  	(0x8UL)
#define P2_12_AF_CCU81_OUT33	(0x18UL)
#define P2_12_AF_ETH0_TXD0  	(0x20UL)
#define P2_13_AF_ETH0_TXD3  	(0x8UL)
#define P2_13_AF_ETH0_TXD1  	(0x20UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P3_7_AF_CAN_N2_TXD  	(0x10UL)
#define P3_7_AF_CCU41_OUT3  	(0x18UL)
#define P3_7_AF_LEDTS0_LINE0	(0x20UL)
#define P3_8_AF_U2C0_DOUT0  	(0x8UL)
#define P3_8_AF_U0C1_SELO3  	(0x10UL)
#define P3_8_AF_CCU41_OUT2  	(0x18UL)
#define P3_8_AF_LEDTS0_LINE1	(0x20UL)
#define P3_9_AF_U2C0_SCLKOUT	(0x8UL)
#define P3_9_AF_CAN_N1_TXD  	(0x10UL)
#define P3_9_AF_CCU41_OUT1  	(0x18UL)
#define P3_9_AF_LEDTS0_LINE2	(0x20UL)
#define P3_10_AF_U2C0_SELO0 	(0x8UL)
#define P3_10_AF_CAN_N0_TXD 	(0x10UL)
#define P3_10_AF_CCU41_OUT0 	(0x18UL)
#define P3_10_AF_LEDTS0_LINE3	(0x20UL)
#define P3_11_AF_U2C1_DOUT0 	(0x8UL)
#define P3_11_AF_U0C1_SELO2 	(0x10UL)
#define P3_11_AF_CCU42_OUT3 	(0x18UL)
#define P3_11_AF_LEDTS0_LINE4	(0x20UL)
#define P3_12_AF_U0C1_SELO1 	(0x10UL)
#define P3_12_AF_CCU42_OUT2 	(0x18UL)
#define P3_12_AF_LEDTS0_LINE5	(0x20UL)
#define P3_13_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_13_AF_U0C1_DOUT0 	(0x10UL)
#define P3_13_AF_CCU42_OUT1 	(0x18UL)
#define P3_13_AF_LEDTS0_LINE6	(0x20UL)
#define P3_14_AF_U1C0_SELO3 	(0x10UL)
#define P3_15_AF_U1C1_DOUT0 	(0x10UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P4_2_AF_U2C1_SELO1  	(0x8UL)
#define P4_2_AF_U1C1_DOUT0  	(0x10UL)
#define P4_2_AF_U2C1_SCLKOUT	(0x20UL)
#define P4_3_AF_U2C1_SELO2  	(0x8UL)
#define P4_3_AF_U0C0_SELO5  	(0x10UL)
#define P4_3_AF_CCU43_OUT3  	(0x18UL)
#define P4_4_AF_U0C0_SELO4  	(0x10UL)
#define P4_4_AF_CCU43_OUT2  	(0x18UL)
#define P4_5_AF_U0C0_SELO3  	(0x10UL)
#define P4_5_AF_CCU43_OUT1  	(0x18UL)
#define P4_6_AF_U0C0_SELO2  	(0x10UL)
#define P4_6_AF_CCU43_OUT0  	(0x18UL)
#define P4_7_AF_CAN_N2_TXD  	(0x10UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_3_AF_U2C0_SELO0  	(0x8UL)
#define P5_3_AF_CCU81_OUT22 	(0x18UL)
#define P5_4_AF_U2C0_SELO1  	(0x8UL)
#define P5_4_AF_CCU81_OUT13 	(0x18UL)
#define P5_5_AF_U2C0_SELO2  	(0x8UL)
#define P5_5_AF_CCU81_OUT12 	(0x18UL)
#define P5_6_AF_U2C0_SELO3  	(0x8UL)
#define P5_6_AF_CCU81_OUT03 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#define P5_8_AF_U1C0_SCLKOUT	(0x10UL)
#define P5_8_AF_CCU80_OUT01 	(0x18UL)
#define P5_9_AF_U1C0_SELO0  	(0x10UL)
#define P5_9_AF_CCU80_OUT20 	(0x18UL)
#define P5_9_AF_ETH0_TX_EN  	(0x20UL)
#define P5_10_AF_U1C0_MCLKOUT	(0x10UL)
#define P5_10_AF_CCU80_OUT10	(0x18UL)
#define P5_10_AF_LEDTS0_LINE7	(0x20UL)
#define P5_11_AF_U1C0_SELO1 	(0x10UL)
#define P5_11_AF_CCU80_OUT00	(0x18UL)
#define P6_0_AF_ETH0_TXD2   	(0x8UL)
#define P6_0_AF_U0C1_SELO1  	(0x10UL)
#define P6_0_AF_CCU81_OUT31 	(0x18UL)
#define P6_1_AF_ETH0_TXD3   	(0x8UL)
#define P6_1_AF_U0C1_SELO0  	(0x10UL)
#define P6_1_AF_CCU81_OUT30 	(0x18UL)
#define P6_2_AF_ETH0_TXER   	(0x8UL)
#define P6_2_AF_U0C1_SCLKOUT	(0x10UL)
#define P6_2_AF_CCU43_OUT3  	(0x18UL)
#define P6_3_AF_CCU43_OUT2  	(0x18UL)
#define P6_4_AF_U0C1_DOUT0  	(0x10UL)
#define P6_4_AF_CCU43_OUT1  	(0x18UL)
#define P6_5_AF_U0C1_MCLKOUT	(0x10UL)
#define P6_5_AF_CCU43_OUT0  	(0x18UL)
#define P6_6_AF_DSD_MCLKOUT 	(0x18UL)
#endif


#if (UC_DEVICE == XMC4502) && (UC_PACKAGE == LQFP100)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_7	XMC_GPIO_PORT5, 7
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_CAN_N0_TXD  	(0x10UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_USB_DRIVEVBUS	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CAN_N0_TXD  	(0x10UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_CAN_N1_TXD  	(0x8UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_CAN_N2_TXD  	(0x10UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_CAN_N1_TXD 	(0x10UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CAN_N1_TXD  	(0x10UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_USB_DRIVEVBUS	(0x8UL)
#define P3_2_AF_CAN_N0_TXD  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#endif


#if (UC_DEVICE == XMC4504) && (UC_PACKAGE == LQFP100)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_7	XMC_GPIO_PORT5, 7
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#endif


#if (UC_DEVICE == XMC4504) && (UC_PACKAGE == LQFP144)
#define P0_0	XMC_GPIO_PORT0, 0
#define P0_1	XMC_GPIO_PORT0, 1
#define P0_2	XMC_GPIO_PORT0, 2
#define P0_3	XMC_GPIO_PORT0, 3
#define P0_4	XMC_GPIO_PORT0, 4
#define P0_5	XMC_GPIO_PORT0, 5
#define P0_6	XMC_GPIO_PORT0, 6
#define P0_7	XMC_GPIO_PORT0, 7
#define P0_8	XMC_GPIO_PORT0, 8
#define P0_9	XMC_GPIO_PORT0, 9
#define P0_10	XMC_GPIO_PORT0, 10
#define P0_11	XMC_GPIO_PORT0, 11
#define P0_12	XMC_GPIO_PORT0, 12
#define P0_13	XMC_GPIO_PORT0, 13
#define P0_14	XMC_GPIO_PORT0, 14
#define P0_15	XMC_GPIO_PORT0, 15
#define P1_0	XMC_GPIO_PORT1, 0
#define P1_1	XMC_GPIO_PORT1, 1
#define P1_2	XMC_GPIO_PORT1, 2
#define P1_3	XMC_GPIO_PORT1, 3
#define P1_4	XMC_GPIO_PORT1, 4
#define P1_5	XMC_GPIO_PORT1, 5
#define P1_6	XMC_GPIO_PORT1, 6
#define P1_7	XMC_GPIO_PORT1, 7
#define P1_8	XMC_GPIO_PORT1, 8
#define P1_9	XMC_GPIO_PORT1, 9
#define P1_10	XMC_GPIO_PORT1, 10
#define P1_11	XMC_GPIO_PORT1, 11
#define P1_12	XMC_GPIO_PORT1, 12
#define P1_13	XMC_GPIO_PORT1, 13
#define P1_14	XMC_GPIO_PORT1, 14
#define P1_15	XMC_GPIO_PORT1, 15
#define P2_0	XMC_GPIO_PORT2, 0
#define P2_1	XMC_GPIO_PORT2, 1
#define P2_2	XMC_GPIO_PORT2, 2
#define P2_3	XMC_GPIO_PORT2, 3
#define P2_4	XMC_GPIO_PORT2, 4
#define P2_5	XMC_GPIO_PORT2, 5
#define P2_6	XMC_GPIO_PORT2, 6
#define P2_7	XMC_GPIO_PORT2, 7
#define P2_8	XMC_GPIO_PORT2, 8
#define P2_9	XMC_GPIO_PORT2, 9
#define P2_10	XMC_GPIO_PORT2, 10
#define P2_11	XMC_GPIO_PORT2, 11
#define P2_12	XMC_GPIO_PORT2, 12
#define P2_13	XMC_GPIO_PORT2, 13
#define P2_14	XMC_GPIO_PORT2, 14
#define P2_15	XMC_GPIO_PORT2, 15
#define P3_0	XMC_GPIO_PORT3, 0
#define P3_1	XMC_GPIO_PORT3, 1
#define P3_2	XMC_GPIO_PORT3, 2
#define P3_3	XMC_GPIO_PORT3, 3
#define P3_4	XMC_GPIO_PORT3, 4
#define P3_5	XMC_GPIO_PORT3, 5
#define P3_6	XMC_GPIO_PORT3, 6
#define P3_7	XMC_GPIO_PORT3, 7
#define P3_8	XMC_GPIO_PORT3, 8
#define P3_9	XMC_GPIO_PORT3, 9
#define P3_10	XMC_GPIO_PORT3, 10
#define P3_11	XMC_GPIO_PORT3, 11
#define P3_12	XMC_GPIO_PORT3, 12
#define P3_13	XMC_GPIO_PORT3, 13
#define P3_14	XMC_GPIO_PORT3, 14
#define P3_15	XMC_GPIO_PORT3, 15
#define P4_0	XMC_GPIO_PORT4, 0
#define P4_1	XMC_GPIO_PORT4, 1
#define P4_2	XMC_GPIO_PORT4, 2
#define P4_3	XMC_GPIO_PORT4, 3
#define P4_4	XMC_GPIO_PORT4, 4
#define P4_5	XMC_GPIO_PORT4, 5
#define P4_6	XMC_GPIO_PORT4, 6
#define P4_7	XMC_GPIO_PORT4, 7
#define P5_0	XMC_GPIO_PORT5, 0
#define P5_1	XMC_GPIO_PORT5, 1
#define P5_2	XMC_GPIO_PORT5, 2
#define P5_3	XMC_GPIO_PORT5, 3
#define P5_4	XMC_GPIO_PORT5, 4
#define P5_5	XMC_GPIO_PORT5, 5
#define P5_6	XMC_GPIO_PORT5, 6
#define P5_7	XMC_GPIO_PORT5, 7
#define P5_8	XMC_GPIO_PORT5, 8
#define P5_9	XMC_GPIO_PORT5, 9
#define P5_10	XMC_GPIO_PORT5, 10
#define P5_11	XMC_GPIO_PORT5, 11
#define P6_0	XMC_GPIO_PORT6, 0
#define P6_1	XMC_GPIO_PORT6, 1
#define P6_2	XMC_GPIO_PORT6, 2
#define P6_3	XMC_GPIO_PORT6, 3
#define P6_4	XMC_GPIO_PORT6, 4
#define P6_5	XMC_GPIO_PORT6, 5
#define P6_6	XMC_GPIO_PORT6, 6
#define P14_0	XMC_GPIO_PORT14, 0
#define P14_1	XMC_GPIO_PORT14, 1
#define P14_2	XMC_GPIO_PORT14, 2
#define P14_3	XMC_GPIO_PORT14, 3
#define P14_4	XMC_GPIO_PORT14, 4
#define P14_5	XMC_GPIO_PORT14, 5
#define P14_6	XMC_GPIO_PORT14, 6
#define P14_7	XMC_GPIO_PORT14, 7
#define P14_8	XMC_GPIO_PORT14, 8
#define P14_9	XMC_GPIO_PORT14, 9
#define P14_12	XMC_GPIO_PORT14, 12
#define P14_13	XMC_GPIO_PORT14, 13
#define P14_14	XMC_GPIO_PORT14, 14
#define P14_15	XMC_GPIO_PORT14, 15
#define P15_2	XMC_GPIO_PORT15, 2
#define P15_3	XMC_GPIO_PORT15, 3
#define P15_4	XMC_GPIO_PORT15, 4
#define P15_5	XMC_GPIO_PORT15, 5
#define P15_6	XMC_GPIO_PORT15, 6
#define P15_7	XMC_GPIO_PORT15, 7
#define P15_8	XMC_GPIO_PORT15, 8
#define P15_9	XMC_GPIO_PORT15, 9
#define P15_12	XMC_GPIO_PORT15, 12
#define P15_13	XMC_GPIO_PORT15, 13
#define P15_14	XMC_GPIO_PORT15, 14
#define P15_15	XMC_GPIO_PORT15, 15


/* Alternate Output Function */
#define P0_0_AF_SCU_HIBOUT  	(0x8UL)
#define P0_0_AF_WDT_REQUEST 	(0x10UL)
#define P0_0_AF_CCU80_OUT21 	(0x18UL)
#define P0_0_AF_LEDTS0_COL2 	(0x20UL)
#define P0_1_AF_SCU_HIBOUT  	(0x8UL)
#define P0_1_AF_U1C1_DOUT0  	(0x10UL)
#define P0_1_AF_WDT_REQUEST 	(0x10UL)
#define P0_1_AF_CCU80_OUT11 	(0x18UL)
#define P0_1_AF_LEDTS0_COL3 	(0x20UL)
#define P0_2_AF_U1C1_SELO1  	(0x10UL)
#define P0_2_AF_CCU80_OUT01 	(0x18UL)
#define P0_3_AF_CCU80_OUT20 	(0x18UL)
#define P0_4_AF_CCU80_OUT10 	(0x18UL)
#define P0_5_AF_U1C0_DOUT0  	(0x10UL)
#define P0_5_AF_CCU80_OUT00 	(0x18UL)
#define P0_6_AF_U1C0_SELO0  	(0x10UL)
#define P0_6_AF_CCU80_OUT30 	(0x18UL)
#define P0_7_AF_WDT_REQUEST 	(0x8UL)
#define P0_7_AF_U0C0_SELO0  	(0x10UL)
#define P0_8_AF_SCU_EXTCLK  	(0x8UL)
#define P0_8_AF_U0C0_SCLKOUT	(0x10UL)
#define P0_9_AF_U1C1_SELO0  	(0x10UL)
#define P0_9_AF_CCU80_OUT12 	(0x18UL)
#define P0_9_AF_LEDTS0_COL0 	(0x20UL)
#define P0_10_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_10_AF_CCU80_OUT02	(0x18UL)
#define P0_10_AF_LEDTS0_COL1	(0x20UL)
#define P0_11_AF_U1C0_SCLKOUT	(0x10UL)
#define P0_11_AF_CCU80_OUT31	(0x18UL)
#define P0_12_AF_U1C1_SELO0 	(0x10UL)
#define P0_12_AF_CCU40_OUT3 	(0x18UL)
#define P0_13_AF_U1C1_SCLKOUT	(0x10UL)
#define P0_13_AF_CCU40_OUT2 	(0x18UL)
#define P0_14_AF_U1C0_SELO1 	(0x10UL)
#define P0_14_AF_CCU40_OUT1 	(0x18UL)
#define P0_15_AF_U1C0_SELO2 	(0x10UL)
#define P0_15_AF_CCU40_OUT0 	(0x18UL)
#define P1_0_AF_DSD_CGPWMN  	(0x8UL)
#define P1_0_AF_U0C0_SELO0  	(0x10UL)
#define P1_0_AF_CCU40_OUT3  	(0x18UL)
#define P1_0_AF_ERU1_PDOUT3 	(0x20UL)
#define P1_1_AF_DSD_CGPWMP  	(0x8UL)
#define P1_1_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_1_AF_CCU40_OUT2  	(0x18UL)
#define P1_1_AF_ERU1_PDOUT2 	(0x20UL)
#define P1_2_AF_CCU40_OUT1  	(0x18UL)
#define P1_2_AF_ERU1_PDOUT1 	(0x20UL)
#define P1_3_AF_U0C0_MCLKOUT	(0x10UL)
#define P1_3_AF_CCU40_OUT0  	(0x18UL)
#define P1_3_AF_ERU1_PDOUT0 	(0x20UL)
#define P1_4_AF_WDT_REQUEST 	(0x8UL)
#define P1_4_AF_CCU80_OUT33 	(0x18UL)
#define P1_4_AF_CCU81_OUT20 	(0x20UL)
#define P1_5_AF_U0C0_DOUT0  	(0x10UL)
#define P1_5_AF_CCU80_OUT23 	(0x18UL)
#define P1_5_AF_CCU81_OUT10 	(0x20UL)
#define P1_6_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_7_AF_U0C0_DOUT0  	(0x10UL)
#define P1_7_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_8_AF_U0C0_SELO1  	(0x10UL)
#define P1_8_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_9_AF_DSD_MCLKOUT 	(0x18UL)
#define P1_10_AF_U0C0_SCLKOUT	(0x10UL)
#define P1_10_AF_CCU81_OUT21	(0x18UL)
#define P1_11_AF_U0C0_SELO0 	(0x10UL)
#define P1_11_AF_CCU81_OUT11	(0x18UL)
#define P1_12_AF_CCU81_OUT01	(0x18UL)
#define P1_13_AF_U0C1_SELO3 	(0x10UL)
#define P1_13_AF_CCU81_OUT20	(0x18UL)
#define P1_14_AF_U0C1_SELO2 	(0x10UL)
#define P1_14_AF_CCU81_OUT10	(0x18UL)
#define P1_15_AF_SCU_EXTCLK 	(0x8UL)
#define P1_15_AF_DSD_MCLKOUT	(0x10UL)
#define P1_15_AF_CCU81_OUT00	(0x18UL)
#define P2_0_AF_CCU81_OUT21 	(0x10UL)
#define P2_0_AF_DSD_CGPWMN  	(0x18UL)
#define P2_0_AF_LEDTS0_COL1 	(0x20UL)
#define P2_1_AF_CCU81_OUT11 	(0x10UL)
#define P2_1_AF_DSD_CGPWMP  	(0x18UL)
#define P2_1_AF_LEDTS0_COL0 	(0x20UL)
#define P2_2_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_2_AF_CCU81_OUT01 	(0x10UL)
#define P2_2_AF_CCU41_OUT3  	(0x18UL)
#define P2_2_AF_LEDTS0_LINE0	(0x20UL)
#define P2_3_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_3_AF_U0C1_SELO0  	(0x10UL)
#define P2_3_AF_CCU41_OUT2  	(0x18UL)
#define P2_3_AF_LEDTS0_LINE1	(0x20UL)
#define P2_4_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_4_AF_U0C1_SCLKOUT	(0x10UL)
#define P2_4_AF_CCU41_OUT1  	(0x18UL)
#define P2_4_AF_LEDTS0_LINE2	(0x20UL)
#define P2_5_AF_U0C1_DOUT0  	(0x10UL)
#define P2_5_AF_CCU41_OUT0  	(0x18UL)
#define P2_5_AF_LEDTS0_LINE3	(0x20UL)
#define P2_6_AF_U2C0_SELO4  	(0x8UL)
#define P2_6_AF_CCU80_OUT13 	(0x18UL)
#define P2_6_AF_LEDTS0_COL3 	(0x20UL)
#define P2_7_AF_CCU80_OUT03 	(0x18UL)
#define P2_7_AF_LEDTS0_COL2 	(0x20UL)
#define P2_8_AF_CCU80_OUT32 	(0x18UL)
#define P2_8_AF_LEDTS0_LINE4	(0x20UL)
#define P2_9_AF_CCU80_OUT22 	(0x18UL)
#define P2_9_AF_LEDTS0_LINE5	(0x20UL)
#define P2_10_AF_VADC_EMUX0_IN	(0x8UL)
#define P2_11_AF_CCU80_OUT22	(0x18UL)
#define P2_12_AF_CCU81_OUT33	(0x18UL)
#define P2_14_AF_VADC_EMUX1_IN	(0x8UL)
#define P2_14_AF_U1C0_DOUT0 	(0x10UL)
#define P2_14_AF_CCU80_OUT21	(0x18UL)
#define P2_15_AF_VADC_EMUX2_IN	(0x8UL)
#define P2_15_AF_CCU80_OUT11	(0x18UL)
#define P2_15_AF_LEDTS0_LINE6	(0x20UL)
#define P3_0_AF_U2C1_SELO0  	(0x8UL)
#define P3_0_AF_U0C1_SCLKOUT	(0x10UL)
#define P3_0_AF_CCU42_OUT0  	(0x18UL)
#define P3_1_AF_U0C1_SELO0  	(0x10UL)
#define P3_2_AF_LEDTS0_COLA 	(0x20UL)
#define P3_3_AF_U1C1_SELO1  	(0x10UL)
#define P3_3_AF_CCU42_OUT3  	(0x18UL)
#define P3_4_AF_U2C1_MCLKOUT	(0x8UL)
#define P3_4_AF_U1C1_SELO2  	(0x10UL)
#define P3_4_AF_CCU42_OUT2  	(0x18UL)
#define P3_4_AF_DSD_MCLKOUT 	(0x20UL)
#define P3_5_AF_U2C1_DOUT0  	(0x8UL)
#define P3_5_AF_U1C1_SELO3  	(0x10UL)
#define P3_5_AF_CCU42_OUT1  	(0x18UL)
#define P3_5_AF_U0C1_DOUT0  	(0x20UL)
#define P3_6_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_6_AF_U1C1_SELO4  	(0x10UL)
#define P3_6_AF_CCU42_OUT0  	(0x18UL)
#define P3_6_AF_U0C1_SCLKOUT	(0x20UL)
#define P3_7_AF_CCU41_OUT3  	(0x18UL)
#define P3_7_AF_LEDTS0_LINE0	(0x20UL)
#define P3_8_AF_U2C0_DOUT0  	(0x8UL)
#define P3_8_AF_U0C1_SELO3  	(0x10UL)
#define P3_8_AF_CCU41_OUT2  	(0x18UL)
#define P3_8_AF_LEDTS0_LINE1	(0x20UL)
#define P3_9_AF_U2C0_SCLKOUT	(0x8UL)
#define P3_9_AF_CCU41_OUT1  	(0x18UL)
#define P3_9_AF_LEDTS0_LINE2	(0x20UL)
#define P3_10_AF_U2C0_SELO0 	(0x8UL)
#define P3_10_AF_CCU41_OUT0 	(0x18UL)
#define P3_10_AF_LEDTS0_LINE3	(0x20UL)
#define P3_11_AF_U2C1_DOUT0 	(0x8UL)
#define P3_11_AF_U0C1_SELO2 	(0x10UL)
#define P3_11_AF_CCU42_OUT3 	(0x18UL)
#define P3_11_AF_LEDTS0_LINE4	(0x20UL)
#define P3_12_AF_U0C1_SELO1 	(0x10UL)
#define P3_12_AF_CCU42_OUT2 	(0x18UL)
#define P3_12_AF_LEDTS0_LINE5	(0x20UL)
#define P3_13_AF_U2C1_SCLKOUT	(0x8UL)
#define P3_13_AF_U0C1_DOUT0 	(0x10UL)
#define P3_13_AF_CCU42_OUT1 	(0x18UL)
#define P3_13_AF_LEDTS0_LINE6	(0x20UL)
#define P3_14_AF_U1C0_SELO3 	(0x10UL)
#define P3_15_AF_U1C1_DOUT0 	(0x10UL)
#define P4_0_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U2C1_SELO0  	(0x8UL)
#define P4_1_AF_U1C1_MCLKOUT	(0x10UL)
#define P4_1_AF_DSD_MCLKOUT 	(0x18UL)
#define P4_1_AF_U0C1_SELO0  	(0x20UL)
#define P4_2_AF_U2C1_SELO1  	(0x8UL)
#define P4_2_AF_U1C1_DOUT0  	(0x10UL)
#define P4_2_AF_U2C1_SCLKOUT	(0x20UL)
#define P4_3_AF_U2C1_SELO2  	(0x8UL)
#define P4_3_AF_U0C0_SELO5  	(0x10UL)
#define P4_3_AF_CCU43_OUT3  	(0x18UL)
#define P4_4_AF_U0C0_SELO4  	(0x10UL)
#define P4_4_AF_CCU43_OUT2  	(0x18UL)
#define P4_5_AF_U0C0_SELO3  	(0x10UL)
#define P4_5_AF_CCU43_OUT1  	(0x18UL)
#define P4_6_AF_U0C0_SELO2  	(0x10UL)
#define P4_6_AF_CCU43_OUT0  	(0x18UL)
#define P5_0_AF_U2C0_DOUT0  	(0x8UL)
#define P5_0_AF_DSD_CGPWMN  	(0x10UL)
#define P5_0_AF_CCU81_OUT33 	(0x18UL)
#define P5_1_AF_U0C0_DOUT0  	(0x8UL)
#define P5_1_AF_DSD_CGPWMP  	(0x10UL)
#define P5_1_AF_CCU81_OUT32 	(0x18UL)
#define P5_2_AF_U2C0_SCLKOUT	(0x8UL)
#define P5_2_AF_CCU81_OUT23 	(0x18UL)
#define P5_3_AF_U2C0_SELO0  	(0x8UL)
#define P5_3_AF_CCU81_OUT22 	(0x18UL)
#define P5_4_AF_U2C0_SELO1  	(0x8UL)
#define P5_4_AF_CCU81_OUT13 	(0x18UL)
#define P5_5_AF_U2C0_SELO2  	(0x8UL)
#define P5_5_AF_CCU81_OUT12 	(0x18UL)
#define P5_6_AF_U2C0_SELO3  	(0x8UL)
#define P5_6_AF_CCU81_OUT03 	(0x18UL)
#define P5_7_AF_CCU81_OUT02 	(0x18UL)
#define P5_7_AF_LEDTS0_COLA 	(0x20UL)
#define P5_8_AF_U1C0_SCLKOUT	(0x10UL)
#define P5_8_AF_CCU80_OUT01 	(0x18UL)
#define P5_9_AF_U1C0_SELO0  	(0x10UL)
#define P5_9_AF_CCU80_OUT20 	(0x18UL)
#define P5_10_AF_U1C0_MCLKOUT	(0x10UL)
#define P5_10_AF_CCU80_OUT10	(0x18UL)
#define P5_10_AF_LEDTS0_LINE7	(0x20UL)
#define P5_11_AF_U1C0_SELO1 	(0x10UL)
#define P5_11_AF_CCU80_OUT00	(0x18UL)
#define P6_0_AF_U0C1_SELO1  	(0x10UL)
#define P6_0_AF_CCU81_OUT31 	(0x18UL)
#define P6_1_AF_U0C1_SELO0  	(0x10UL)
#define P6_1_AF_CCU81_OUT30 	(0x18UL)
#define P6_2_AF_U0C1_SCLKOUT	(0x10UL)
#define P6_2_AF_CCU43_OUT3  	(0x18UL)
#define P6_3_AF_CCU43_OUT2  	(0x18UL)
#define P6_4_AF_U0C1_DOUT0  	(0x10UL)
#define P6_4_AF_CCU43_OUT1  	(0x18UL)
#define P6_5_AF_U0C1_MCLKOUT	(0x10UL)
#define P6_5_AF_CCU43_OUT0  	(0x18UL)
#define P6_6_AF_DSD_MCLKOUT 	(0x18UL)
#endif

#endif
