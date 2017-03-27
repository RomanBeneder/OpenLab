/*------------------------------------------------------------------------------
HELLO.C

Copyright 1995-2005 Keil Software, Inc.
------------------------------------------------------------------------------*/

#include <REG52.H>                /* special function register declarations   */
                                  /* for the intended 8051 derivative         */

#include <stdio.h>                /* prototype declarations for I/O functions */



                                         /* is enabled                        */

void Delay();

/*------------------------------------------------
The main C function.  Program execution starts
here after stack initialization.
------------------------------------------------*/


/*8051, PIC, AVR, ARM development Boards and Programmers - www.Embeddedmarket.com */
/*All products, schematics and pricings are listed at www.Embeddedmarket.com*/
/*Embedded Market, 205 Decision Tower, Next To CityPride, Satara Road, Pune 411037 India*/

void main (void) {

/*------------------------------------------------
Setup the serial port for 2400 baud at 11.0592MHz.
------------------------------------------------*/

    SCON  = 0x50;		        /* SCON: mode 1, 8-bit UART, enable rcvr      */
    SM0   = 1;
		TMOD |= 0x20;               /* TMOD: timer 1, mode 2, 8-bit reload        */
    TH1   = 0xF7;              /* TH1:  reload value for 2400 baud @ 11.0592MHz   */
    TR1   = 1;                  /* TR1:  timer 1 run                          */
    TI    = 1;                  /* TI:   set TI to send first char of UART    */


/*------------------------------------------------
Note that an embedded program never exits (because
there is no operating system to return to).  It
must loop and execute forever.
------------------------------------------------*/
  while (1) {    
    printf ("Hello World\n");   /* Print "Hello World" */	
	Delay();	
	Delay();
	Delay();
  }
}

void Delay(void)
{
	int j;
	
		for(j=0;j<10000;j++)
		{
		}
}

