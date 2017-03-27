/**
 * 	@file 	crc.c
 * 	@author	Christian Matar
 */

#include "crc.h"

uint16_t gen_crc16(const uint8_t *data, uint16_t size)
{
    uint16_t out = 0;
    int bits_read = 0, bit_flag;

    /* Sanity check: */
    if(!data)
        return 0;

    while(size > 0)
    {
        bit_flag = out >> 15;

        /* Get next bit: */
        out <<= 1;
        out |= (*data >> (7 - bits_read)) & 1;

        /* Increment bit counter: */
        bits_read++;
        if(bits_read > 7)
        {
            bits_read = 0;
            data++;
            size--;
        }

        /* Cycle check: */
        if(bit_flag)
            out ^= CRC16;

    }
    return out;
}

uint8_t check_crc16(const uint8_t *data, uint16_t size)
{
	if(!data)
		return 0;

	if(gen_crc16(&data[0], HEADER_LEN) == 0 && gen_crc16(&data[size - HEADER_LEN], size - HEADER_LEN) == 0)
		return 1;

	return 0;
}

