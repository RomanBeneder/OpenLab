-------------------------------------------------------------------------------
--                                                                      
--                 Parallel ADC Interface + Data Transfer - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         tb_UART
--
-- FILENAME:       tb_UART.vhd
-- 
-- ARCHITECTURE:   sim
-- 
-- ENGINEER:       Patrick Schmitt
--
-- DATE:           16. September 2015
--
-- VERSION:        1.0
--
-------------------------------------------------------------------------------
--                                                                      
-- DESCRIPTION:    Simulation of the UART data transfer
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
-- CHANGES:        Version 1.0 - PS - 16. September 2015
--
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity tb_UART is
end tb_UART;

architecture sim of tb_UART is

    component UART
        port(
                CLOCK_50: in std_logic;
                SW: in std_logic_vector(9 downto 0);
                KEY: in std_logic_vector (2 downto 0);
                LED: out std_logic_vector (9 downto 0);
                UART_TXD: out STD_LOGIC;
                UART_RXD: in std_logic;  
                UART_CTS: in std_logic;
                UART_RTS: out std_logic;
                
                ADC_OE: out std_logic;
                ADC_DATA: in std_logic_vector(7 downto 0);
                ADC_CLK: out std_logic
                );

    end component;
    
signal CLOCK_50 : std_lOGIC;
signal SW           : std_LOGIC_vector(9 downto 0);
signal UART_TXD : std_LOGIC;
signal ADC_DATA : std_logic_vector(7 downto 0);
signal ADC_CLK      : std_logic;
signal KEY          : std_LOGIC_vector(2 downto 0);
signal UART_RXD : std_LOGIC;
signal UART_CTS : std_LOGIC;
signal ADC_count  : integer := 0;

begin

    u1 : UART
        port map(CLOCK_50 => CLOCK_50,
                    SW => SW,
                    UART_TXD => UART_TXD,
                    ADC_DATA => ADC_DATA,
                    ADC_CLK => ADC_CLK,
                    KEY => KEY,
                    UART_RXD => UART_RXD,
                    UART_CTS => UART_CTS);
    
--############################################################################
--#                                                                          #
--#     Simulating the physical clock impuls attached to the FPGA            #
--#                                                                          #
--############################################################################
    
    clockgen : process
    begin
        CLOCK_50 <= '0';
        wait for 10 ns;
        CLOCK_50 <= '1';
        wait for 10 ns;
    end process;

--############################################################################
--#                                                                          #
--#     Configuring the switches and buttons of the FPGA dev board           #
--#     as well as setting the flow control lines correctly                  #
--#                                                                          #
--############################################################################  
    
    config : process
    begin
        KEY <="000";
        SW <= "0000000000";
        UART_RXD <= '1';
        UART_CTS <= '0';
        wait for 10000 ms;
    end process;
    
--############################################################################
--#                                                                          #
--#     Simulating the behavior of the parallel ADC by counting 8 bits up    #
--#     to the maximum value.                                                #
--#                                                                          #
--#     The frequency of the counter is 50MHz which                          #
--#     is way higher than the real ADC is capable of sampling.              #
--#     The higher frequency is used to test the stability of the FPGA       # 
--#     design regarding data corruption.                                    #
--#                                                                          #
--############################################################################  
    
    ADC : process(CLOCK_50)
    begin
        if(CLOCK_50'event and CLOCK_50 = '1') then
            if(ADC_count < 256) then
            ADC_count <= ADC_count + 1;
            else
            ADC_count <= 0;
            end if;
        end if;
        ADC_DATA <= std_logic_vector(to_unsigned(ADC_count, 8));
--      ADC_DATA <= "01010101";
        end process;
    end sim;
        