/*
 * CRC16.c
 *
 *  Created on: 01.09.2016
 *      Author: HP
 */

#include "CRC16.h"

int text[2] = {0xFF,0x10};
unsigned short good_crc = 0;
unsigned short text_length = 0;

struct values crc16(struct values *pointer)
{
	int i = 0;
	int8_t low = 0;
	int8_t high = 0;

	good_crc=0;

	for(i = 0; i<pointer->size; i++)
	{
		text[0] = pointer->crc_header[i];// - '0';
		go();
	}

	low = *((uint8_t*)(&good_crc)); // Grab char from first byte of the pointer to the int
	high = *((uint8_t*)(&good_crc) + 1); // Offset one byte from the pointer and grab second char
	pointer->crc_low = low;
	pointer->crc_high = high;

	return *pointer;
}

void go(void)
{
	text[1] = 16;
    unsigned short ch=0, i=0;

    i = 0;
    text_length= 0;
    while((ch=text[i])!=0x10)
    {
        update_good_crc(ch);
        i++;
    }
}


void update_good_crc(unsigned short ch)
{
    unsigned short i=0, v=0, xor_flag=0;

    /*
    Align test bit with leftmost bit of the message byte.
    */
    v = 0x80;

    for (i=0; i<8; i++)
    {
        if ((good_crc & 0x8000) > 0)
        {
            xor_flag= 1;
        }
        else
        {
            xor_flag= 0;
        }
        good_crc = good_crc << 1;

        if ((ch & v) > 0)
        {
            /*
            Append next bit of message to end of CRC if it is not zero.
            The zero bit placed there by the shift above need not be
            changed if the next bit of the message is zero.
            */
            good_crc= good_crc + 1;
        }

        if (xor_flag)
        {
            good_crc = good_crc ^ poly;
        }

        /*
        Align test bit with next bit of the message byte.
        */
        v = v >> 1;
    }
}
