#include "crc.h"

// init value 0
// 0x1021 polynomial
// non standard ccitt crc, worse..
uint16_t crc_custom(uint8_t* data, uint16_t len)
{
	uint16_t a, b;
	uint16_t value = 0;
	for(uint16_t i=0; i < len; ++i)
	{
		a = data[i];
		for (int8_t count = 7; count >=0; count--)
		{
			a = a << 1; // nächstes bit
			b = (a >> 8) & 1; // input msb ganz an den anfang und selektieren
			if ((value & 0x8000) != 0)	// crc msb set?
				value = ((value << 1) + b) ^ 0x1021;
			else
				value = (value << 1) + b;
		}
		value = value & 0xffff;
	}
	
	return value;
}
