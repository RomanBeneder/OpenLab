#include "spi.h"
#include "util.h"
#include <avr/io.h>


void spi_init()
{
	/* Setup required pins
	DDRB |= (1 << PINB2) | (1 << PINB1) | (1 << PINB0);	// Output:	MOSI, SCLK, CS
	DDRB &= ~(1 << PINB3);								// Input:	MISO
	
	PORTB |= (1 << PINB3) | (1 << PINB0);				// Pullup for PINB3 (MISO) and 
														// PINB0 (CS) high = inactive.
	*/
														
	/* Setup SPI */
	SPCR =	(1<<SPE)  |	// SPI Enable 
			(1<<MSTR) |	// SPI Master
			(0<<CPOL) |	// SPI Mode '00'
			(0<<CPHA) | // SPI Mode '00'
			(1<<SPR1) |	// f_spi = f_osc / 64
			(0<<SPR0);	// f_spi = 16MHz / 64 = 250kHz
}

uint8_t send(uint8_t data)
{
	SPDR = data;
	while(!(SPSR & (1<<SPIF)));
	return SPDR;
}

void set_dac(uint8_t channel, uint8_t level)
{
	if(channel > 1)
		return;
	
	// datasheet 7.0.1
	uint8_t command = (channel << 3) |		// channel (0-1) <> address (non volatile memory)
					  (0 << 1) | (0 << 1);	// write command
	
	uint16_t data = level;
			
	cb(PORTB,PINB0);			// cs low => active
	send(command);				// cmd byte
	send((data >> 8) & 0xFF);	// data msb
	send(data & 0xFF);			// data lsb
	sb(PORTB,PINB0);			// cs high => inactive
}