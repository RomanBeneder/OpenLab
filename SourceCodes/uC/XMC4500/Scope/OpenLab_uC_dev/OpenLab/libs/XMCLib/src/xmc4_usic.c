/**
 * @file xmc4_usic.c
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
 * 2015-02-16:
 *     - Initial <br>
 *      
 * @endcond 
 *
 */

/*********************************************************************************************************************
 * HEADER FILES
 *********************************************************************************************************************/
#include <xmc_usic.h>

/*********************************************************************************************************************
 * MACROS
 ********************************************************************************************************************/
#if UC_FAMILY == XMC4
#include <xmc_scu.h>

/*********************************************************************************************************************
 * API IMPLEMENTATION
 ********************************************************************************************************************/
/**
 * @brief API to enable the USIC driver
 * @retval void
 *
 */
void XMC_USIC_Enable(XMC_USIC_t *const usic)
{
  if (usic == USIC0)
  {
#if(UC_SERIES != XMC45)
    XMC_SCU_CLOCK_UngatePeripheralClock(XMC_SCU_PERIPHERAL_CLOCK_USIC0);
#endif
    XMC_SCU_RESET_DeassertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC0);
  }
#if defined(USIC1)  
  else if (usic == USIC1)
  {
#if(UC_SERIES != XMC45)
    XMC_SCU_CLOCK_UngatePeripheralClock(XMC_SCU_PERIPHERAL_CLOCK_USIC1);
#endif	
    XMC_SCU_RESET_DeassertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC1);
  }
#endif  
#if defined(USIC2)  
  else if (usic == USIC2)
  {
#if(UC_SERIES != XMC45)  
    XMC_SCU_CLOCK_UngatePeripheralClock(XMC_SCU_CLOCK_USIC2);
#endif
    XMC_SCU_RESET_DeassertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC2);
  }
#endif  
  else
  {
    XMC_ASSERT("USIC module not available", 0/*Always*/);
  }
}
/**
 * @brief API to Disable the USIC driver
 * @retval void
 *
 */
void XMC_USIC_Disable(XMC_USIC_t *const usic)
{
  if (usic == (XMC_USIC_t *)USIC0)
  {
    XMC_SCU_RESET_AssertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC0);
#if(UC_SERIES != XMC45)
    XMC_SCU_CLOCK_GatePeripheralClock(XMC_SCU_PERIPHERAL_CLOCK_USIC0);
#endif
  }
#if defined(USIC1)  
  else if (usic == (XMC_USIC_t *)USIC1)
  {
    XMC_SCU_RESET_AssertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC1);
#if(UC_SERIES != XMC45)
    XMC_SCU_CLOCK_GatePeripheralClock(XMC_SCU_PERIPHERAL_CLOCK_USIC1);
#endif
  }
#endif  
#if defined(USIC2)  
  else if (usic == (XMC_USIC_t *)USIC2)
  {
    XMC_SCU_RESET_AssertPeripheralReset(XMC_SCU_PERIPHERAL_RESET_USIC2);
#if(UC_SERIES != XMC45)
    XMC_SCU_CLOCK_GatePeripheralClock(XMC_SCU_CLOCK_USIC2);
#endif
  }
#endif  
  else
  {
	  XMC_ASSERT("USIC module not available", 0/*Always*/);
  }
  
}

#endif /* UC_FAMILY == XMC4 */
