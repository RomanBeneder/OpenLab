/* Function to read and write the TM4C1294 USB port */

/******************************************************************** LIBRARY */
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

//For Linux
#include "../libs/inc/libusb.h"
//For Windows
//#include <libusb.h>
//For MAC OSX
//#include "libusb.h"
/******************************************************************** DEFINES */
#define SUCCESS 0
#define DEBUG 1

#define MAX_TRANSMIT_LENGTH 20

#define USB_BULK_INTERFACE_NUMBER 0
#define USB_BULK_CLAIMED_INTERFACE 0
#define USB_BULK_AVAILABLE_INTERFACES 1

//#define USB_BULK_VENDOR_ID  0x1CBE
#define USB_BULK_VENDOR_ID  0xFFFF
//#define USB_BULK_PRODUCT_ID 0x0003
#define USB_BULK_PRODUCT_ID 0x0000

//#define BULK_IN_EP 0x81
#define BULK_IN_EP 0x83
//#define BULK_OUT_EP 0x01
#define BULK_OUT_EP 0x04

#define ERROR_NO_DEVICE_FOUND 100
#define ERROR_DEVICE_HANDLER  101
/********************************************************************************************* STRUCTURES*/
typedef struct libusb_device_descriptor libusb_device_descriptor;
/********************************************************************************************* FUNCTION PROTOTYPE */
int
_locate_uC (int dev_in_list, libusb_device **dev);
int
_close_current_libusb_session (libusb_device_handle *OpenLab_handle,
                               libusb_context *ctx);
/********************************************************************************************* MAIN APPLICATION */
int
main (int argc, char **argv)
{
  libusb_context *ctx = NULL;
  libusb_device **available_devices = NULL;
  libusb_device_handle *FHTW_OpenLab_handle = NULL;

  int iRet = 0;
  int dev_in_list = 0;

  int send_status = 0;
  int receive_status = 0;
  int transfered_data = 0;
  int transmit_data_length = 0;

  unsigned char received_data[MAX_TRANSMIT_LENGTH] =
    { 0 };
  char transmit_data[MAX_TRANSMIT_LENGTH] = "Hello World\n";

  //Initialize a library session
  //ctx (the context to operate on)
  if ((iRet = libusb_init (&ctx)) != SUCCESS)
    {
      printf ("ERROR: USB initialization %d\n", iRet);
      return iRet;
    }

  //informational messages are printed to stdout,
  //warning and error messages are printed to stderr
  libusb_set_debug (ctx, LIBUSB_LOG_LEVEL_INFO);

  //This is the entry point into finding a USB device to operate
  if ((dev_in_list = libusb_get_device_list (ctx, &available_devices)) < 0)
    {
      printf ("ERROR: Device List %d\n", dev_in_list);
      return dev_in_list;
    }

  //Function _locate_uC returns SUCCESS if the microcontroller was found
  if ((iRet = _locate_uC (dev_in_list, available_devices)) != SUCCESS)
    {
      printf ("ERROR: Unable to connect to OpenLab device %d\n", iRet);
      return iRet;
    }

  //Finding a device with the particular idVendor/idProduct combination
  FHTW_OpenLab_handle = libusb_open_device_with_vid_pid (ctx, USB_BULK_VENDOR_ID,
                                                     USB_BULK_PRODUCT_ID);

  if (FHTW_OpenLab_handle == NULL)
    {
      printf ("ERROR: Can not open device\n");
      return ERROR_DEVICE_HANDLER;
    }

  //The list is freed and the devices are unreferenced
  libusb_free_device_list (available_devices, 1);

  //Determine if a kernel driver is active on an interface
  if ((iRet = libusb_kernel_driver_active (FHTW_OpenLab_handle,
                                           USB_BULK_INTERFACE_NUMBER)) == 1)
    {
      printf ("Kernel Driver is active\n");

      //If a kernel driver is active, it is impossible to claim the interface
      //Thus the kernel needs to be switched off
      if ((iRet = libusb_detach_kernel_driver (FHTW_OpenLab_handle,
                                               USB_BULK_INTERFACE_NUMBER))
          == SUCCESS)
        {
          printf ("Kernel Driver detached\n");
        }

      else
        {
          printf ("ERROR: Unable to detach Kernel Driver %d \n", iRet);
          _close_current_libusb_session (FHTW_OpenLab_handle, ctx);
          return iRet;
        }
    }

  else if (iRet == LIBUSB_ERROR_NOT_SUPPORTED)
    {
      printf ("Warning: Kernel Driver is not supported!");
    }
  else if (iRet == 0)
    {
      printf ("Kernel Driver is not active\n");
    }
  else
    {
      printf ("ERROR: Kernel Driver active-verification failed %d\n", iRet);
      _close_current_libusb_session (FHTW_OpenLab_handle, ctx);
      return iRet;
    }

  //Claim an interface on a given device handle
  if ((iRet = libusb_claim_interface (FHTW_OpenLab_handle,
                                      USB_BULK_CLAIMED_INTERFACE)) != SUCCESS)
    {
      printf ("ERROR: Can not claim interface %d\n", iRet);
      _close_current_libusb_session (FHTW_OpenLab_handle, ctx);
      return iRet;
    }

  printf ("Claimed Interface\n");
  printf ("Writing Data...\n");

  transmit_data_length = strlen (&transmit_data[0]);

  //Perform a USB bulk transfer
  send_status = libusb_bulk_transfer (FHTW_OpenLab_handle,
  BULK_OUT_EP,
                                      (unsigned char *) &transmit_data[0],
                                      transmit_data_length,
                                      &transfered_data,
                                      10); //Timeout

  if (send_status != SUCCESS)
    {
      printf ("ERROR: Data transmission failed!");
    }
  else
    {
      printf("SUCESS: Sent 'Hello World\n");
      printf("Trying to receive echoed data...\n");
    }

  //Perform a USB bulk transfer
  receive_status = libusb_bulk_transfer (FHTW_OpenLab_handle,
  BULK_IN_EP,
                                         (unsigned char *) &received_data[0],
                                         transmit_data_length,
                                         &transfered_data,
                                         10); //Timeout

  if (receive_status != SUCCESS)
    {
      printf ("ERROR Receive status: %d\n", receive_status);
      _close_current_libusb_session (FHTW_OpenLab_handle, ctx);
      return receive_status;
    }
  else
    {
      printf("SUCESS: Received %s\n", received_data);
    }

  if ((iRet = _close_current_libusb_session (FHTW_OpenLab_handle, ctx)) != SUCCESS)
    {
      printf ("ERROR: The current session could not be closed %d\n", iRet);
      return iRet;
    }

  return SUCCESS;
}
//
int
_locate_uC (int dev_in_list, libusb_device **dev)
{
  int i = 0;
  libusb_device_descriptor desc;

  //Check the entire device list as long as
  //the microncontroller TM4C1294 is detected
  for (i = 0; i < dev_in_list; i++)
    {
      //
      libusb_get_device_descriptor (dev[i], &desc);

      if ((desc.idVendor == USB_BULK_VENDOR_ID)
          && (desc.idProduct == USB_BULK_PRODUCT_ID))
        {
          printf (
              "***FOUND Open Lab device***\n***VendorID: 0x%x, ProductID: 0x%x***\n",
              desc.idVendor, desc.idProduct);
          return SUCCESS;
        }

#if DEBUG
      printf ("VendorID: 0x%x, ProductID: 0x%x\n", desc.idVendor,
              desc.idProduct);
#endif
    }

  return ERROR_NO_DEVICE_FOUND;
}

int
_close_current_libusb_session (libusb_device_handle *OpenLab_handle,
                               libusb_context *ctx)
{
  int iRet = 0;

  //It is necessary to release all claimed interfaces before closing a device handle
  if ((iRet = libusb_release_interface (OpenLab_handle,
                                        USB_BULK_INTERFACE_NUMBER)) != SUCCESS)
    {
      printf ("ERROR: Interface can not be released %d\n", iRet);
      return iRet;
    }

  printf ("Released Interface\n");

  libusb_close (OpenLab_handle);

  //Has to be called after closing all open devices
  //and before the application terminates
  libusb_exit (ctx);

  return SUCCESS;
}

/* EOF */
