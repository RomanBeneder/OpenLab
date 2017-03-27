/** \file
 *  \brief LUFA Library Configuration Header File (Template)
 *
 *  This is a header file which can be used to configure LUFA's
 *  compile time options, as an alternative to the compile time
 *  constants supplied through a makefile. To use this configuration
 *  header, copy this into your project's root directory and supply
 *  the \c USE_LUFA_CONFIG_HEADER token to the compiler so that it is
 *  defined in all compiled source files.
 *
 *  For information on what each token does, refer to the LUFA
 *  manual section "Summary of Compile Tokens".
 */

#ifndef __LUFA_CONFIG_H__
#define __LUFA_CONFIG_H__

	#if (ARCH == ARCH_AVR8)

		/* Non-USB Related Configuration Tokens: */
//		#define DISABLE_TERMINAL_CODES

		/* USB Class Driver Related Tokens: */
//		#define HID_HOST_BOOT_PROTOCOL_ONLY
//		#define HID_STATETABLE_STACK_DEPTH       {Insert Value Here}
//		#define HID_USAGE_STACK_DEPTH            {Insert Value Here}
//		#define HID_MAX_COLLECTIONS              {Insert Value Here}
//		#define HID_MAX_REPORTITEMS              {Insert Value Here}
//		#define HID_MAX_REPORT_IDS               {Insert Value Here}
		#define NO_CLASS_DRIVER_AUTOFLUSH

		/* General USB Driver Related Tokens: */
//		#define ORDERED_EP_CONFIG
		#define USE_STATIC_OPTIONS               (USB_OPT_AUTO_PLL | USB_OPT_REG_ENABLED | USB_DEVICE_OPT_FULLSPEED)
		#define USB_DEVICE_ONLY
//		#define USB_HOST_ONLY
//		#define USB_STREAM_TIMEOUT_MS            {Insert Value Here}
//		#define NO_LIMITED_CONTROLLER_CONNECT
		#define NO_SOF_EVENTS					 /* remove start of frame events */

		/* USB Device Mode Driver Related Tokens: */
//		#define USE_RAM_DESCRIPTORS
		#define USE_FLASH_DESCRIPTORS
//		#define USE_EEPROM_DESCRIPTORS			 /* adds more than it saves */
		#define NO_INTERNAL_SERIAL
		#define FIXED_CONTROL_ENDPOINT_SIZE      8
		#define DEVICE_STATE_AS_GPIOR            2	/* removes a global, puts state in GPIOR2, saves space */
		#define FIXED_NUM_CONFIGURATIONS         1
//		#define CONTROL_ONLY_DEVICE
//		#define INTERRUPT_CONTROL_ENDPOINT
		#define NO_DEVICE_REMOTE_WAKEUP			 /* saves a lot of space */
		#define NO_DEVICE_SELF_POWER			 /* we are only bus powered, this safes us a bit here */

		/* USB Host Mode Driver Related Tokens: */
//		#define HOST_STATE_AS_GPIOR              {Insert Value Here}
//		#define USB_HOST_TIMEOUT_MS              {Insert Value Here}
//		#define HOST_DEVICE_SETTLE_DELAY_MS	     {Insert Value Here}
//		#define NO_AUTO_VBUS_MANAGEMENT
//		#define INVERTED_VBUS_ENABLE_LINE

	#else
		#error Unsupported architecture for this LUFA configuration file.
	#endif
#endif

