
/**
 * @file main.c
 * @brief Application to test the OpenLab library implementation
 * @author Harald Schloffer
 */

// This file is part of the GNU ARM Eclipse Plug-ins project.
// Copyright (c) 2014 Liviu Ionescu.
//

// ----------------------------------------------------------------------------

#include <stdio.h>
#include <stdlib.h>
#include "OpenLab.h"

// Sample pragmas to cope with warnings. Please note the related line at
// the end of this function, used to pop the compiler diagnostics status.
//#pragma GCC diagnostic push
//#pragma GCC diagnostic ignored "-Wunused-parameter"
//#pragma GCC diagnostic ignored "-Wmissing-declarations"
//#pragma GCC diagnostic ignored "-Wreturn-type"

static int8_t data[64];

int
main (void)
{
  if (OLB_init () < 0)
    {
      while (1)
        ;
    }
  if (OLB_USB_connect () < 0)
    {
      while (1)
        ;
    }
  while (1)
    {
      if (OLB_USB_is_connected ())
        {
          uint16_t received = OLB_USB_bytes_received ();
          if (received > 0)
            {
              OLB_USB_receive_data(data, received);
              OLB_USB_send_data (data, received);
              received = 0;
            }
        }
    }

  return 0;
}

//#pragma GCC diagnostic pop

// ----------------------------------------------------------------------------
