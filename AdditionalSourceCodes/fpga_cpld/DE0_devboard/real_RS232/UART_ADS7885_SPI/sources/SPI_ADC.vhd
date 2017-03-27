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
USE ieee.std_logic_arith.all;
USE ieee.std_logic_unsigned.all;

ENTITY SPI_ADC IS
  PORT (clock   : IN     STD_LOGIC;                      --system clock
        sclk    : OUT    STD_LOGIC;                      --spi clock
        CS      : OUT    STD_LOGIC;                      --slave select
        sdio    : IN     STD_LOGIC;                      --serial data input output
        busy    : OUT    STD_LOGIC;                      --busy / data ready signal
        rx_data : OUT    STD_LOGIC_VECTOR(7 DOWNTO 0));  --data received
END SPI_ADC;

ARCHITECTURE STRUC OF SPI_ADC IS

    SIGNAL busy_s : std_logic:='0';
    SIGNAL finished : std_logic:='0';
    SIGNAL clock_enable: std_LOGIC:='0';
    SIGNAL s_SPI_CLK: std_logic:='1';
    SIGNAL CS_S: std_logic:='1';
    SIGNAL RAW_DATA: std_logic_vector(15 downto 0);
    SIGNAL rx_data_temp: std_LOGIC_VECTOR(7 downto 0);
    SIGNAL start: std_logic:='0';

BEGIN

--############################################################################
--#                                                                          #
--#     This process controls the clock generation for the SPI interface     #
--#     according to the datasheet of the ADC.                               #
--#     It also enables the data transfer from the ADC to the FPGA.          #
--#                                                                          #
--############################################################################

    PROCESS(clock)
        VARIABLE CS_counter: INTEGER:=0;
        begin
            if(clock'event and clock = '1') then
                if(CS_counter = 0) then
                    start <= '0';
                    CS_S <= '0';
                    clock_enable <= '0';
                    CS_counter := CS_counter + 1;
                elsif(CS_counter = 3) then
                    CS_S <= '0';
                    clock_enable <= '1';
                    start <= '1';
                    CS_counter := CS_counter + 1;
                elsif(CS_counter = 32) then
                    CS_S <= '1';
                    clock_enable <= '1';
                    CS_counter := CS_counter + 1;
                elsif(CS_counter = 34) then
                    CS_counter := 0;
                else
                    CS_counter := CS_counter + 1;
                end if;
            end if;
    end process;

--############################################################################
--#                                                                          #
--#     This process splits the input clock from the SPI_ADC module in the   #
--#     necessary clock frequency for the SPI interface.                     #
--#                                                                          #
--############################################################################  
    
    PROCESS(clock,clock_enable)
        VARIABLE SPI_SCALE: INTEGER :=0;
        BEGIN
            if(clock_enable = '1') then
                if(clock'event and clock = '1') then
                    s_SPI_CLK <= not s_SPI_CLK;
                end if;
            else
                s_SPI_CLK <= '1';
            end if;
        END PROCESS;

    sclk <= s_SPI_CLK;
    CS <= CS_S;

--############################################################################
--#                                                                          #
--#     This process controls the transfer of each data bit send by the ADC. #
--#     It stores all bits in a vector signal and sets a finish flag if      #
--#     all bits are stored.                                                 #
--#                                                                          #
--############################################################################  
    
    PROCESS(s_SPI_CLK)
        VARIABLE INDEX: INTEGER:=0;
        BEGIN
        if(s_SPI_CLK'event and s_SPI_CLK = '0') then
            if(start = '1') then
                if(INDEX < 15) then
                    RAW_DATA(INDEX) <= sdio;
                    INDEX := INDEX + 1;
                    finished <= '0';
                else
                    INDEX := 0;
                    finished <= '1';
                end if;
            end if;
        end if;
    end process;

--############################################################################
--#                                                                          #
--#     This process only stores the important data bits from the received   #
--#     ADC data in a different register.                                    #
--#                                                                          #
--############################################################################  
    
    PROCESS(finished)
        begin
        if(finished = '1') then
            rx_data_temp <= RAW_DATA(9 downto 2);
        end if;
    end process;

    rx_data(0) <= rx_data_temp(7);
    rx_data(1) <= rx_data_temp(6);
    rx_data(2) <= rx_data_temp(5);
    rx_data(3) <= rx_data_temp(4);
    rx_data(4) <= rx_data_temp(3);
    rx_data(5) <= rx_data_temp(2);
    rx_data(6) <= rx_data_temp(1);
    rx_data(7) <= rx_data_temp(0);

END STRUC;