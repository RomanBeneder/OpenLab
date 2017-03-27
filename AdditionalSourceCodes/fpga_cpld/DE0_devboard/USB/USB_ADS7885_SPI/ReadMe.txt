Demo design for interfacing a serial (SPI) ADC (ADS7885) and transfering data 
over a FTDI-USB IC. (currently only FPGA ---> PC)
================================================================================
Structure
---------
The design is split in several components. The main component, USB_SPI_ADC, 
handels the ADC readout and data transmission. The USB communication itself is 
done by the USB_FTDI component. This component handels the exact timings 
necessary for corruption free data transfer. The FTDI-USB IC is currently set 
to FIFO mode. So the FPGA is accessing the component like a FIFO.
The SPI interface for accessing the ADC is defined by the SPI_ADC component.
--------------------------------------------------------------------------------
Usage
-----
This design is optimized for the DE0 devboard and its peripherals.
A FTDI serial adapter was used during stability and performance tests.
The design is completly synthesized and ready for usage.

With Switch 0 and Switch 1 some options can be configured:
	Switch 0 OFF .............. Hardware flow control OFF
	Switch 0 ON  .............. Hardware flow control ON
	Switch 1 OFF .............. Continuous transfering
	Switch 1 ON  .............. waiting for PC command "sd" (send data)
--------------------------------------------------------------------------------
