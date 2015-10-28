Title: mc8051 IP Core Demo Design
Date: 2013-02-28
Autor: http://embsys.technikum-wien.at
======================================

This a demo design which shows how to to implement the mc8051 IP core from Oregano Systems
in an Altera Cyclone-III EP3C16 FPGA. The mc8051 is processing a simple program which
toggles a GPIO pin connected to a LED.

The required tools are:

- ModelSim Altera, Version 6.5
- Altera Quartus II, Version 10.0
- Keil uVision 4, C51 Version 9.51a  

Directory structure of this design:

- mc8051_de0

  - doc ... Documents (User Guides, Datasheets, ...)
    - ds ... Datasheets

  - hw ...  Hardware related stuff
    - generate ... Generated hardware blocks (RAM, ROM, PLL, ...)
      - cyclone_iii_ep3c16 ... Generated blocks for Altera Cyclone-III EP3C16 FPGA
    - hdl ... HDL code (VHDL, Verilog)
      - mc8051 ... HDL code of the mc8051 IP core
    - impl ... Implementation directory (Altera Quartus II project directory)
    - sim ... ModelSim working directories
      tc_8051 ... ModelSim working directory of "Test Case 8051"
    - tb ... Testbenches

  - keil .. Keil uVision IDE working directory (used to develop software for the mc8051)
    - blink_led ... Keil uVision IDE working directory of the "Blink LED" example
      - src ... C source code of the mc8051 "Blink LED" example

  - sw ... Misc. software
    - tools ... Misc. tools (e.g., CONVHEX.EXE)

More information can be found in the following lecture notes of the Dept. of Embedded Systems,
University of Applied Sciences Technikum Wien:

- Slides_mc8051_IP_Core_Introduction.pdf
- mc8051_Getting_Started.pdf
- Keil_uVision_mc8051_Getting_Started.pdf

---
