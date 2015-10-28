Demo design for interfacing a par. ADC (ADC1173) and transfering data over UART
================================================================================
Structure
---------
The design is split in several components. The main component, UART_PAR_ADC, 
handels the ADC readout and data transmission. The UART communication itself is 
split in two components. TX and RX. The TX one is for handling outgoing- and the
RX for incoming data.
Inside the UART_PAR_ADC component the ADC clock is generated and the ADC digital
output is verified for valid values.
Also the logic for software flow control is included.
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
