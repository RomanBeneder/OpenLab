#include <stdint.h>

#ifndef SPI_H_
#define SPI_H_

void spi_init();
uint8_t send(uint8_t data);
void set_dac(uint8_t channel, uint8_t data);

#endif