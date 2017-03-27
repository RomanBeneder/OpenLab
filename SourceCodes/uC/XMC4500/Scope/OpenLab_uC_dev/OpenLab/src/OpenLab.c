/*
 * OpenLab.c
 *
 *  Created on: 19 Sep 2015
 *      Author: Harald
 */

#include "../include/OpenLab.h"
#include "DAVE.h"

static const XMC_SCU_CLOCK_CONFIG_t CLOCK_XMC4_0_CONFIG =
      { .syspll_config.n_div = 80U, .syspll_config.p_div = 2U,

      .syspll_config.k_div = 4U,

        .syspll_config.mode = XMC_SCU_CLOCK_SYSPLL_MODE_NORMAL,
        .syspll_config.clksrc = XMC_SCU_CLOCK_SYSPLLCLKSRC_OSCHP,
        .enable_oschp =
        true,

        .enable_osculp = false,

        .calibration_mode = XMC_SCU_CLOCK_FOFI_CALIBRATION_MODE_FACTORY,
        .fstdby_clksrc = XMC_SCU_HIB_STDBYCLKSRC_OSI, .fsys_clksrc =
            XMC_SCU_CLOCK_SYSCLKSRC_PLL,
        .fsys_clkdiv = 1U, .fcpu_clkdiv = 1U,
        .fccu_clkdiv = 1U,
        .fperipheral_clkdiv = 1U };
/**
 * @brief Initialize system clocks and enable USB clock.
 */
void
OLB_init_clocks (void);

void
OLB_init_clocks (void)
{
  /* Initialize the SCU clock */
  XMC_SCU_CLOCK_Init (&CLOCK_XMC4_0_CONFIG);
  /* RTC source clock*/
  XMC_SCU_HIB_SetRtcClockSource (XMC_SCU_HIB_RTCCLKSRC_OSI);

  /* USB/SDMMC source clock*/
  XMC_SCU_CLOCK_SetUsbClockSource (XMC_SCU_CLOCK_USBCLKSRC_USBPLL);
  XMC_SCU_CLOCK_SetUsbClockDivider (4U);

  XMC_SCU_CLOCK_StartUsbPll (2U, 64U);

  XMC_SCU_CLOCK_SetWdtClockSource (XMC_SCU_CLOCK_WDTCLKSRC_OFI);
  XMC_SCU_CLOCK_SetWdtClockDivider (1U);

  XMC_SCU_CLOCK_SetEbuClockDivider (1U);
}

int
OLB_init (void)
{
  // initialize clocks
  OLB_init_clocks ();

  if (DAVE_Init () == DAVE_STATUS_SUCCESS)
    {
      return 1;
    }
  else
    {
      return 0;
    }
}

int
OLB_USB_connect (void)
{
  if (USBD_VCOM_Connect () == USBD_VCOM_STATUS_SUCCESS)
    {
      return 1;
    }
  else
    {
      return -1;
    }
}
int
OLB_USB_is_connected (void)
{
  if (USBD_VCOM_IsEnumDone ())
    {
      return 1;
    }
  else
    {
      return -1;
    }
}
int
OLB_USB_send_data (int8_t* const src, uint16_t const count)
{
  if (USBD_VCOM_SendData (src, count) == USBD_VCOM_STATUS_SUCCESS)
    {
      return 1;
    }
  else
    {
      return -1;
    }
}
int
OLB_USB_receive_data (int8_t* const dst, uint16_t const count)
{
  if (USBD_VCOM_ReceiveData (dst, count) == USBD_VCOM_STATUS_SUCCESS)
    {
      return 1;
    }
  else
    {
      return -1;
    }
}

uint16_t
OLB_USB_bytes_received (void)
{
  return USBD_VCOM_BytesReceived ();
}
