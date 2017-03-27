/*************************************************************************
 * Copyright (c) 2009 Altera Corporation, San Jose, California, USA.      *
 * All rights reserved. All use of this software and documentation is     *
 * subject to the License Agreement located at the end of this file below.*
 *************************************************************************/
/******************************************************************************
 *
 * Description
 * ************* 
 * A simple program which, using an 8 bit variable, counts from 0 to ff, 
 * repeatedly.  Output of this variable is displayed on the LEDs, the Seven
 * Segment Display, and the LCD.  The four "buttons" (SW0-SW3) are used
 * to control output to these devices in the following manner:
 *   Button1 (SW0) => LED is "counting"
 *   Button2 (SW1) => Seven Segment is "counting"
 *   Button3 (SW2) => LCD is "counting"
 *   Button4 (SW3) => All of the peripherals are "counting".
 *
 * Upon completion of "counting", there is a short waiting period during 
 * which button/switch presses will be identified on STDOUT.
 * NOTE:  These buttons have not been de-bounced, so one button press may 
 *        cause multiple notifications to STDOUT.
 * 
 * Requirements
 * **************
 * This program requires the following devices to be configured:
 *   an LED PIO named 'led_pio',
 *   a Seven Segment Display PIO named 'seven_seg_pio',
 *   an LCD Display named 'lcd_display',
 *   a Button PIO named 'button_pio',
 *   a UART (JTAG or standard serial)
 *
 * Peripherals Exercised by SW
 * *****************************
 * LEDs
 * Seven Segment Display
 * LCD
 * Buttons (SW0-SW3)
 * UART (JTAG or serial)

 * Software Files
 * ****************
 * count_binary.c ==>  This file.
 *                     main() is contained here, as is the lion's share of the
 *                     functionality.
 * count_binary.h ==>  Contains some very simple VT100 ESC sequence defines
 *                     for formatting text to the LCD Display.
 * 
 *
 * Useful Functions
 * *****************
 * count_binary.c (this file) has the following useful functions.
 *   static void sevenseg_set_hex( int hex )
 *     - Defines a hexadecimal display map for the seven segment display.
 *   static void handle_button_interrupts( void* context, alt_u32 id)
 *   static void init_button_pio()
 *     - These are useful functions because they demonstrate how to write
 *       and register an interrupt handler with the system library.
 *
 * count_binary.h 
 *   The file defines some useful VT100 escape sequences for use on the LCD
 *   Display.
 */

#include "count_binary.h"

/* A "loop counter" variable. */
static alt_u8 count;
/* A variable to hold the value of the button pio edge capture register. */
volatile int edge_capture;


/* Button pio functions */

/*
  Some simple functions to:
  1.  Define an interrupt handler function.
  2.  Register this handler in the system.
*/

/*******************************************************************
 * static void handle_button_interrupts( void* context, alt_u32 id)*
 *                                                                 *  
 * Handle interrupts from the buttons.                             *
 * This interrupt event is triggered by a button/switch press.     *
 * This handler sets *context to the value read from the button    *
 * edge capture register.  The button edge capture register        *
 * is then cleared and normal program execution resumes.           *
 * The value stored in *context is used to control program flow    *
 * in the rest of this program's routines.                         *
 *                                                                 *
 * Provision is made here for systems that might have either the   *
 * legacy or enhanced interrupt API active, or for the Nios II IDE *
 * which does not support enhanced interrupts. For systems created *
 * using the Nios II softawre build tools, the enhanced API is     *
 * recommended for new designs.                                    *
 ******************************************************************/
#ifdef BUTTON_PIO_BASE

#ifdef ALT_ENHANCED_INTERRUPT_API_PRESENT
static void handle_button_interrupts(void* context)
#else
static void handle_button_interrupts(void* context, alt_u32 id)
#endif
{
    /* Cast context to edge_capture's type. It is important that this be 
     * declared volatile to avoid unwanted compiler optimization.
     */
    volatile int* edge_capture_ptr = (volatile int*) context;
    /* Store the value in the Button's edge capture register in *context. */
    *edge_capture_ptr = IORD_ALTERA_AVALON_PIO_EDGE_CAP(BUTTON_PIO_BASE);
    /* Reset the Button's edge capture register. */
    IOWR_ALTERA_AVALON_PIO_EDGE_CAP(BUTTON_PIO_BASE, 0);
    
    /* 
     * Read the PIO to delay ISR exit. This is done to prevent a spurious
     * interrupt in systems with high processor -> pio latency and fast
     * interrupts.
     */
    IORD_ALTERA_AVALON_PIO_EDGE_CAP(BUTTON_PIO_BASE); 
}

/* Initialize the button_pio. */

static void init_button_pio()
{
    /* Recast the edge_capture pointer to match the alt_irq_register() function
     * prototype. */
    void* edge_capture_ptr = (void*) &edge_capture;
    /* Enable all 4 button interrupts. */
    IOWR_ALTERA_AVALON_PIO_IRQ_MASK(BUTTON_PIO_BASE, 0xf);
    /* Reset the edge capture register. */
    IOWR_ALTERA_AVALON_PIO_EDGE_CAP(BUTTON_PIO_BASE, 0x0);
    /* Register the interrupt handler. */
#ifdef ALT_ENHANCED_INTERRUPT_API_PRESENT
    alt_ic_isr_register(BUTTON_PIO_IRQ_INTERRUPT_CONTROLLER_ID, BUTTON_PIO_IRQ, 
      handle_button_interrupts, edge_capture_ptr, 0x0);
#else
    alt_irq_register( BUTTON_PIO_IRQ, edge_capture_ptr, 
      handle_button_interrupts);
#endif
}
#endif /* BUTTON_PIO_BASE */

/* Seven Segment Display PIO Functions 
 * sevenseg_set_hex() --  implements a hex digit map.
 */
 
#ifdef SEVEN_SEG_PIO_BASE
static void sevenseg_set_hex(int hex)
{
    static alt_u8 segments[16] = {
        0x81, 0xCF, 0x92, 0x86, 0xCC, 0xA4, 0xA0, 0x8F, 0x80, 0x84, /* 0-9 */
        0x88, 0xE0, 0xF2, 0xC2, 0xB0, 0xB8 };                       /* a-f */

    unsigned int data = segments[hex & 15] | (segments[(hex >> 4) & 15] << 8);

    IOWR_ALTERA_AVALON_PIO_DATA(SEVEN_SEG_PIO_BASE, data);
}
#endif

/* Functions used in main loop
 * lcd_init() -- Writes a simple message to the top line of the LCD.
 * initial_message() -- Writes a message to stdout (usually JTAG_UART).
 * count_<device>() -- Implements the counting on the respective device.
 * handle_button_press() -- Determines what to do when one of the buttons
 * is pressed.
 */
static void lcd_init( FILE *lcd )
{
    /* If the LCD Display exists, write a simple message on the first line. */
    LCD_PRINTF(lcd, "%c%s Counting will be displayed below...", ESC,
               ESC_TOP_LEFT);
}

static void initial_message()
{
    printf("\n\n**************************\n");
    printf("* Hello from Nios II!    *\n");
    printf("* Counting from 00 to ff *\n");
    printf("**************************\n");
}

/********************************************************
 * The following functions write the value of the global*
 * variable 'count' to 3 peripherals, if they exist in  *
 * the system.  Specifically:                           *
 * The LEDs will illuminate, the Seven Segment Display  *
 * will count from 00-ff, and the LCD will display the  *
 * hex value as the program loops.                      *
 * *****************************************************/

/* static void count_led()
 * 
 * Illuminate LEDs with the value of 'count', if they
 * exist in the system
 */

static void count_led()
{
#ifdef LED_PIO_BASE
    IOWR_ALTERA_AVALON_PIO_DATA(
        LED_PIO_BASE,
        count
        );
#endif
}

/* static void count_sevenseg()
 * 
 * Display value of 'count' on the Seven Segment Display
 */

static void count_sevenseg()
{
#ifdef SEVEN_SEG_PIO_BASE
    sevenseg_set_hex(count);
#endif
}

/* static void count_lcd()
 * 
 * Display the value of 'count' on the LCD Display, if it
 * exists in the system.
 * 
 * NOTE:  A HAL character device driver is used, so the LCD
 * is treated as an I/O device (i.e.: using fprintf).  You
 * can read more about HAL drivers <link/reference here>.
 */

static void count_lcd( void* arg )
{
    FILE *lcd = (FILE*) arg;
    LCD_PRINTF(lcd, "%c%s 0x%x\n", ESC, ESC_COL2_INDENT5, count);
}

/* count_all merely combines all three peripherals counting */

static void count_all( void* arg )
{
    count_led();
    count_sevenseg();
    count_lcd( arg );
    printf("%02x,  ", count);
}
  

static void handle_button_press(alt_u8 type, FILE *lcd)
{
    /* Button press actions while counting. */
    if (type == 'c')
    {
        switch (edge_capture) 
        {
            /* Button 1:  Output counting to LED only. */
        case 0x1:
            count_led();
            break;
            /* Button 2:  Output counting to SEVEN SEG only. */
        case 0x2:
            count_sevenseg();
            break;
            /* Button 3:  Output counting to D only. */
        case 0x4:
            count_lcd( lcd );
            break;
            /* Button 4:  Output counting to LED, SEVEN_SEG, and D. */ 
        case 0x8:
            count_all( lcd );
            break;
            /* If value ends up being something different (shouldn't) do
               same as 8. */
        default:
            count_all( lcd );
            break;
        }
    }
    /* If 'type' is anything else, assume we're "waiting"...*/
    else
    {
        switch (edge_capture)
        {
        case 0x1:
            printf( "Button 1\n");
            edge_capture = 0;
            break;
        case 0x2:
            printf( "Button 2\n");
            edge_capture = 0;
            break;
        case 0x4:
            printf( "Button 3\n");
            edge_capture = 0;
            break;
        case 0x8:
            printf( "Button 4\n");
            edge_capture = 0;
            break;
        default:
            printf( "Button press UNKNOWN!!\n");
        }
    }
}
    
/*******************************************************************************
 * int main()                                                                  *
 *                                                                             *
 * Implements a continuous loop counting from 00 to FF.  'count' is the loop   *
 * counter.                                                                    *
 * The value of 'count' will be displayed on one or more of the following 3    *
 * devices, based upon hardware availability:  LEDs, Seven Segment Display,    *
 * and the LCD Display.                                                        *
 *                                                                             *
 * During the counting loop, a switch press of SW0-SW3 will affect the         *
 * behavior of the counting in the following way:                              *
 *                                                                             *
 * SW0 - Only the LED will be "counting".                                      *
 * SW1 - Only the Seven Segment Display will be "counting".                    *
 * SW2 - Only the LCD Display will be "counting".                              *
 * SW3 - All devices "counting".                                               *
 *                                                                             *
 * There is also a 7 second "wait", following the count loop,                 *
 * during which button presses are still                                       *
 * detected.                                                                   *
 *                                                                             *
 * The result of the button press is displayed on STDOUT.                      *
 *                                                                             *
 * NOTE:  These buttons are not de-bounced, so you may get multiple            *
 * messages for what you thought was a single button press!                    *
 *                                                                             *
 * NOTE:  References to Buttons 1-4 correspond to SW0-SW3 on the Development   *
 * Board.                                                                      *
 ******************************************************************************/

int main(void)
{ 
    int i;
    int wait_time;
    FILE * lcd;

    count = 0;

    /* Initialize the LCD, if there is one.
     */
    lcd = LCD_OPEN();
    if(lcd != NULL) {lcd_init( lcd );}
    
    /* Initialize the button pio. */

#ifdef BUTTON_PIO_BASE
    init_button_pio();
#endif

/* Initial message to output. */

    initial_message();

/* Continue 0-ff counting loop. */

    while( 1 ) 
    {
        usleep(100000);
        if (edge_capture != 0)
        {
            /* Handle button presses while counting... */
            handle_button_press('c', lcd);
        }
        /* If no button presses, try to output counting to all. */
        else
        {
            count_all( lcd );
        }
        /*
         * If done counting, wait about 7 seconds...
         * detect button presses while waiting.
         */
        if( count == 0xff )
        {
            LCD_PRINTF(lcd, "%c%s %c%s %c%s Waiting...\n", ESC, ESC_TOP_LEFT,
                       ESC, ESC_CLEAR, ESC, ESC_COL1_INDENT5);
            printf("\nWaiting...");
            edge_capture = 0; /* Reset to 0 during wait/pause period. */

            /* Clear the 2nd. line of the LCD screen. */
            LCD_PRINTF(lcd, "%c%s, %c%s", ESC, ESC_COL2_INDENT5, ESC,
                       ESC_CLEAR);
            wait_time = 0;
            for (i = 0; i<70; ++i)
            {
                printf(".");
                wait_time = i/10;
                LCD_PRINTF(lcd, "%c%s %ds\n", ESC, ESC_COL2_INDENT5,
                    wait_time+1);

                if (edge_capture != 0) 
                {
                    printf( "\nYou pushed:  " );
                    handle_button_press('w', lcd);
                }
                usleep(100000); /* Sleep for 0.1s. */
            }
            /*  Output the "loop start" messages before looping, again.
             */
            initial_message();
            lcd_init( lcd );
        }
        count++;
    }
    LCD_CLOSE(lcd);
    return 0;
}
/******************************************************************************
 *                                                                             *
 * License Agreement                                                           *
 *                                                                             *
 * Copyright (c) 2006 Altera Corporation, San Jose, California, USA.           *
 * All rights reserved.                                                        *
 *                                                                             *
 * Permission is hereby granted, free of charge, to any person obtaining a     *
 * copy of this software and associated documentation files (the "Software"),  *
 * to deal in the Software without restriction, including without limitation   *
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,    *
 * and/or sell copies of the Software, and to permit persons to whom the       *
 * Software is furnished to do so, subject to the following conditions:        *
 *                                                                             *
 * The above copyright notice and this permission notice shall be included in  *
 * all copies or substantial portions of the Software.                         *
 *                                                                             *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR  *
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,    *
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE *
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER      *
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING     *
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER         *
 * DEALINGS IN THE SOFTWARE.                                                   *
 *                                                                             *
 * This agreement shall be governed in all respects by the laws of the State   *
 * of California and by the laws of the United States of America.              *
 * Altera does not recommend, suggest or require that this reference design    *
 * file be used in conjunction or combination with any other product.          *
 ******************************************************************************/
