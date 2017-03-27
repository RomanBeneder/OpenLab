-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer (USB) - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         USB_FTDI
--
-- FILENAME:       USB_FTDI.vhd
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
-- DESCRIPTION:    Component for transmitting data from the FPGA to the PC using
--                 the FT232H USB IC (currently only FPGA --> PC)
--
-------------------------------------------------------------------------------
--
-- REFERENCES:     
--                 Debounce component originally by Scott Larson - 
--                 Digi-Key Corporation
--                 Webpage: https://eewiki.net/
--                 
-------------------------------------------------------------------------------
--                                                                      
-- CHANGES:        Version 1.0 - PS - 22. October 2015
--
-------------------------------------------------------------------------------

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity USB_FTDI is
  port (CLOCK_50: in std_logic;
        DATA: inout STD_LOGIC_vector(7 downto 0);
        RXF:    in std_logic;
        TXE:  in std_logic;
        RD:  out std_logic;
        WR:  out std_logic;

        READ_START: in std_logic;
        WRITE_START: in std_logic;
        READ_FINISHED: out std_logic;
        WRITE_FINISHED: out std_logic;
        DATA_SEND: in std_logic_vector(7 downto 0);
        DATA_RECEIVED: out std_logic_vector(7 downto 0);
        BUSY_FTDI: out std_logic);
end USB_FTDI;

ARCHITECTURE USB_FTDI_a OF USB_FTDI IS

    SIGNAL SEND_NOW : std_logic := '0';
    SIGNAL WR_S      : std_logic := '1';
    SIGNAL RD_S      : std_logic := '1';
    SIGNAL WRITE_FINISHED_S: std_logic := '0';
    SIGNAL BUSY_FTDI_S: std_LOGIC := '0';

BEGIN

--############################################################################
--#                                                                          #
--#     This process controls the transmit path of the USB_FTDI interface.   #
--#     According to the datasheet of the FTDI component, the signals are    #
--#     set in attention to the necessary timings. Delays are done by a      #
--#     counter.                                                             #
--#                                                                          #
--############################################################################

    PROCESS(CLOCK_50)

    VARIABLE WR_counter: INTEGER:=0;
    VARIABLE WR_counter_2: INTEGER:=0;
    VARIABLE WR_active: INTEGER:=0;
    begin
        IF(CLOCK_50'EVENT AND CLOCK_50='1')THEN
            IF(TXE = '0' and RXF = '1' and SEND_NOW = '0' and BUSY_FTDI_S = '0') then
                DATA <= DATA_SEND;
                SEND_NOW <= '1';
                WRITE_FINISHED_S <= '0';
                BUSY_FTDI_S <= '1';
            end if;
    
            IF(SEND_NOW <= '1') then
                if(WR_active = 0) then
                    WR_counter_2 := WR_counter_2 + 1;
                end if;
                if(WR_active = 1 OR WR_counter_2 = 1) then
                    WR_active := 1;
                    WR_counter_2 := 0;
                    WR_S <= '0';
                    WR_counter := WR_counter + 1;
                    WRITE_FINISHED_S <= '0';
                end if;
            end if;
    
            IF(WR_counter = 2) then
                WR_counter := 0;
                WR_active := 0;
                WR_S <= '1';
                SEND_NOW <= '0';
                WRITE_FINISHED_S <= '1';
                BUSY_FTDI_S <= '0';
            end if;
        end if;
    end process;

    WR <= WR_S;
    RD <= RD_S;
    WRITE_FINISHED <= WRITE_FINISHED_S;
    BUSY_FTDI <= BUSY_FTDI_S;

end USB_FTDI_a;