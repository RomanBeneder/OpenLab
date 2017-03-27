-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         SPI_ADC
--
-- FILENAME:       SPI_ADC.vhd
-- 
-- ARCHITECTURE:   STRUC
-- 
-- ENGINEER:       Patrick Schmitt
--
-- DATE:           22. October 2015
--
-- VERSION:        1.0
--
-------------------------------------------------------------------------------
--                                                                      
-- DESCRIPTION:    Component interfacing the ADS7885 SPI ADC
--
-------------------------------------------------------------------------------
--
-- REFERENCES:     Serial communication originally by user "Anton Zero"
--                 under GNU license.
--                 Project page: https://github.com/AntonZero/UART
--                 Debounce component originally by Scott Larson - 
--                 Digi-Key Corporation
--                 Webpage: https://eewiki.net/
--                 
-------------------------------------------------------------------------------
--                                                                      
-- CHANGES:        Version 1.0 - PS - 22. October 2015
--
-------------------------------------------------------------------------------

LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_unsigned.all;

ENTITY ADS7885_IPCORE IS
  PORT (clock_120   : IN     STD_LOGIC;                      --system clock
		  clock_80	  : IN	  STD_LOGIC;
        sclk    	  : OUT    STD_LOGIC;                      --spi clock
        CS      	  : OUT    STD_LOGIC;                      --slave select
        sdio    	  : IN     STD_LOGIC;                      --serial data input output
        rx_data 	  : OUT    STD_LOGIC_VECTOR(7 DOWNTO 0);
		  fin_SPI 	  : OUT 	 STD_LOGIC;
		  START_STOP  : in std_logic);
END ADS7885_IPCORE;

ARCHITECTURE STRUC OF ADS7885_IPCORE IS

    SIGNAL finished : std_logic:='0';
    SIGNAL clock_enable: std_LOGIC:='0';
    SIGNAL s_SPI_CLK: std_logic:='1';
    SIGNAL CS_S: std_logic:='1';
    SIGNAL RAW_DATA: std_logic_vector(9 downto 0);
    SIGNAL rx_data_temp: std_LOGIC_VECTOR(7 downto 0);
    SIGNAL start: std_logic:='0';
	 SIGNAL CS_counter: INTEGER range 0 to 40 :=0;
	 SIGNAL INDEX: INTEGER range 0 to 16 :=0;
BEGIN

--############################################################################
--#                                                                          #
--#     This process controls the clock generation for the SPI interface     #
--#     according to the datasheet of the ADC.                               #
--#     It also enables the data transfer from the ADC to the FPGA.          #
--#                                                                          #
--############################################################################

    PROCESS(clock_120,START_STOP)
        
        begin
            if(clock_120'event and clock_120 = '1') then
               if(START_STOP = '1') then
					 if(CS_counter = 0) then
                    fin_SPI <= '0';
						  start <= '0';
                    CS_S <= '0';
                    clock_enable <= '0';
                    CS_counter <= CS_counter + 1;
                elsif(CS_counter = 1) then
                    fin_SPI <= '0';
						  CS_S <= '0';
                    clock_enable <= '1';
                    start <= '1';
                    CS_counter <= CS_counter + 1;
                elsif(CS_counter = 37) then
                    fin_SPI <= '0';
						  CS_S <= '1';
                    clock_enable <= '1';
                    CS_counter <= CS_counter + 1;
                elsif(CS_counter = 40) then
                    fin_SPI <= '1';
						  CS_counter <= 0;
						  clock_enable <= '0';
						  start <= '0';
						  CS_S <= '1';
                else
                    CS_counter <= CS_counter + 1;
						  fin_SPI <= '0';
                end if;
					else
						CS_counter <= 0;
						CS_S <= '1';
						start <= '0';
						clock_enable <= '0';
						fin_SPI <= '0';
					end if;
            end if;
    end process;

--############################################################################
--#                                                                          #
--#     This process splits the input clock from the SPI_ADC module in the   #
--#     necessary clock frequency for the SPI interface.                     #
--#                                                                          #
--############################################################################  
    
PROCESS(clock_80,clock_enable,START_STOP)
       
        BEGIN
            if(clock_enable = '1' and START_STOP = '1') then
                if(clock_80'event and clock_80 = '1') then
						  s_SPI_CLK <= not s_SPI_CLK;
                end if;
            else
                s_SPI_CLK <= '1';
            end if;
        END PROCESS;

--############################################################################
--#                                                                          #
--#     This process controls the transfer of each data bit send by the ADC. #
--#     It stores all bits in a vector signal and sets a finish flag if      #
--#     all bits are stored.                                                 #
--#                                                                          #
--############################################################################  
    
    PROCESS(s_SPI_CLK, start, CS_S)
       
        BEGIN
        if(s_SPI_CLK'event and s_SPI_CLK = '0') then
            if(start = '1') then
                if(INDEX < 10 and CS_S = '0') then
                    RAW_DATA(INDEX) <= sdio;
                    INDEX <= INDEX + 1;
                    --fin_SPI <= '0';
                elsif(CS_S = '0') then
                    rx_data_temp <= RAW_DATA(9 downto 2);
						  --fin_SPI <= '1';
					 elsif(CS_S = '1') then
						  INDEX <= 0;
                end if;
            else
					--fin_SPI <= '0';
					INDEX <= 0;
				end if;
        end if;
    end process;

	 
		sclk <= s_SPI_CLK;
		CS <= CS_S;
	 
	 	rx_data(0) <= rx_data_temp(7);
		rx_data(1) <= rx_data_temp(6);
		rx_data(2) <= rx_data_temp(5);
		rx_data(3) <= rx_data_temp(4);
		rx_data(4) <= rx_data_temp(3);
		rx_data(5) <= rx_data_temp(2);
		rx_data(6) <= rx_data_temp(1);
		rx_data(7) <= rx_data_temp(0);
END STRUC;