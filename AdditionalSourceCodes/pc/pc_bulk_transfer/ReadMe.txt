Linux/Windows/MAC OSX libusb 1.0 - ReadMe
------------------------------------------------------------------------------------------------
libusb 1.0 API documentation page:
http://libusb.sourceforge.net/api-1.0/pages.html 
------------------------------------------------------------------------------------------------
WINDOWS: -MinGW
Before the utilization of the library libusb 1.0 it is necessary to perform the following steps:
***Depending on your system either MinGW32 or MinGW64***

1.Copy libusb-1.0.19-rc1-win.7z\include\libusb-1.0\libusb.h to C:\MingW\include\
2.Copy libusb-1.0.19-rc1-win.7z\MinGW32\libusb-1.0\dll\libusb-1.0.dll.a to C:\MingW\lib\
3.Copy libusb-1.0.19-rc1-win.7z\MinGW32\libusb-1.0\dll\libusb-1.0.dll to C:\MingW\bin\

***TIVA C DRIVER FOR WINDOWS***
The driver for the TIVA C Launchpad board can be found in WINDOWS/windows_drivers
------------------------------------------------------------------------------------------------
Linux: USB Device permission denied
It is necessary to allow the access to the device. Thus, the following commands need to be
executed within a terminal programm:

Change access rights:
sudo chmod 666 /dev/(**Used USB Device**)
------------------------------------------------------------------------------------------------
MAC OSX: XCode
The libusb-1.0.19.dylib needs to be compiled from the sources (installed on OSX):
Change to the directory and run the following commands:
  1. ./configure
  2. make
  3. sudo make install (This will install the library)

Generating the libusb-1.0.0.dylib with XCode
  1. Open libusb-1.0.19/Xcode/libusb.xcodeproj
  2. Compile project
  3. Extend libusb on the left side of the project navigator
  4. Extend Products 
  5. Right click on libusb-1.0.0.dylib and select Show in Finder
  6. The libary can be copied to the desired location

Furthermore the libusb-1.0.0.dylib needs to be linked within XCode:
Build Phases->Link Binary With Libraries: (Add with +) libusb-1.0.0.dylib

It is mandatory to change the search path within XCode:
Build Settings->Header Search Paths->Debug(Any Architecture|Any SDK): /usr/local/include
Build Settings->Header Search Paths->Release(Any Architecture|Any SDK): /usr/local/include
------------------------------------------------------------------------------------------------
It is mandatory to include the library before usage!

For Windwos:
#include <libusb.h>

For Linux:
#include <libusb-1.0/libusb.h>

For MAC OSX:
#include "libusb.h"
------------------------------------------------------------------------------------------------
