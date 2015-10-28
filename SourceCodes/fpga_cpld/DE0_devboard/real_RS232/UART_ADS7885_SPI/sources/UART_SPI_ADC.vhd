-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         UART_SPI_ADC
--
-- FILENAME:       UART_SPI_ADC.vhd
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
-- DESCRIPTION:    Main component for handling ADC access, software flow control 
--                 and data transmission
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

library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity UART_SPI_ADC is
  port (CLOCK_50: in std_logic;
        SW: in std_logic_vector(9 downto 0);
        KEY: in std_logic_vector (2 downto 0);
        LED: out std_logic_vector (9 downto 0);
        UART_TXD: out STD_LOGIC;
        UART_RXD: in std_logic;
        UART_CTS: in std_logic;
        UART_RTS: out std_logic;

        sclk_SPI: out std_LOGIC;
        CS_SPI: out std_logic;
        sdio_SPI: in std_logic);

end UART_SPI_ADC;

ARCHITECTURE STRUC OF UART_SPI_ADC IS

    COMPONENT PLL
      PORT (inclk0: IN std_LOGIC;
            c0 : OUT std_LOGIC);
    END COMPONENT PLL;

    COMPONENT debounce
      PORT (clk : IN  STD_LOGIC;     --input clock
            button : IN  STD_LOGIC;  --input signal to be debounced
            result : OUT STD_LOGIC); --debounced signal
    END comPONENT debounce;

    COMPONENT TX
      PORT (CLK: IN std_logic;
            START: IN std_logic;
            BUSY: OUT std_logic;
            DATA: IN std_logic_vector(7 downto 0);
            TX_LINE: OUT STD_logic;
            FINISHED: OUT STD_LOGIC);
    END COMPONENT TX;

    COMPONENT RX
      PORT (CLK: IN std_logic;
            BUSY: OUT std_logic;
            DATA: OUT std_logic_vector(7 downto 0);
            RX_LINE: IN STD_logic;
            FINISHED: OUT STD_LOGIC);
    END COMPONENT RX;

    COMPONENT SPI_ADC
      PORT (clock : IN STD_LOGIC; --system clock
            sclk : OUT STD_LOGIC; --spi clock
            CS : OUT STD_LOGIC;   --slave select
            sdio : IN STD_LOGIC;  --serial data input output
            busy : OUT STD_LOGIC; --busy / data ready signal
            rx_data : OUT STD_LOGIC_VECTOR(7 DOWNTO 0)); --data received
    END COMPONENT SPI_ADC;

    SIGNAL TX_DATA: STD_logic_vector(7 downto 0);
    SIGNAL TX_START: STD_LOGIC:='0';
    SIGNAL TX_BUSY: STD_LOGIC;
    SIGNAL TX_FINISHED: std_LOGIC;
    SIGNAL OVERFLOW: std_LOGIC:='0';

    SIGNAL RX_DATA: STD_logic_vector(7 downto 0):="00000000";
    SIGNAL RX_BUSY: STD_LOGIC;
    SIGNAL RX_FINISHED: std_LOGIC;

    SIGNAL RX_STATUS: std_LOGIC:='0';

    SIGNAL s_SW: STD_LOGIC_vector (9 downto 0);
    --SIGNAL s_KEY: std_logic_vector (2 downto 0);

    SIGNAL s_ADC_CLK: std_logic:='0';
    SIGNAL valid_ADC: std_LOGIC_VECTOR (7 downto 0);

    TYPE MY_ARRAY is array (0 to 1) of  STD_LOGIC_VECTOR(7  downto 0);
    SHARED VARIABLE MY_DATA: MY_ARRAY;

    SIGNAL busy_SPI:std_LOGIC:='0';
    SIGNAL rx_data_SPI:std_LOGIC_VECTOR(7 downto 0);
    SIGNAL ADC_CLK_SPI:std_LOGIC:='0';

BEGIN

    C0: PLL PORT MAP (CLOCK_50,ADC_CLK_SPI);
    --C2: TX PORT MAP (UART_CLOCK,TX_START,TX_BUSY,TX_DATA,UART_TXD);

    D0: debounce PORT MAP (CLOCK_50, SW(0),s_SW(0));
    D1: debounce PORT MAP (CLOCK_50, SW(1),s_SW(1));
    D2: debounce PORT MAP (CLOCK_50, SW(2),s_SW(2));
    D3: debounce PORT MAP (CLOCK_50, SW(3),s_SW(3));
    D4: debounce PORT MAP (CLOCK_50, SW(4),s_SW(4));
    D5: debounce PORT MAP (CLOCK_50, SW(5),s_SW(5));
    D6: debounce PORT MAP (CLOCK_50, SW(6),s_SW(6));
    D7: debounce PORT MAP (CLOCK_50, SW(7),s_SW(7));

    D8: debounce PORT MAP (CLOCK_50, SW(8),s_SW(8));
    D9: debounce PORT MAP (CLOCK_50, SW(9),s_SW(9));

    --D10: debounce PORT MAP (CLOCK_50, KEY(0),s_KEY(0));
    --D11: debounce PORT MAP (CLOCK_50, KEY(1),s_KEY(1));
    --D12: debounce PORT MAP (CLOCK_50, KEY(2),s_KEY(2));

    C1: TX PORT MAP (CLOCK_50,TX_START,TX_BUSY,TX_DATA,UART_TXD,TX_FINISHED);
    C2: RX PORT MAP (CLOCK_50,RX_BUSY,RX_DATA,UART_RXD,RX_FINISHED);
    C3: SPI_ADC PORT MAP (ADC_CLK_SPI,sclk_SPI,CS_SPI,sdio_SPI,busy_SPI,rx_data_SPI);

--############################################################################
--#                                                                          #
--#     This process controls the whole data flow of the FPGA design.        #
--#     It exactly sends 100000 bytes of ADC data per cycle.                 #
--#     It is possible to enter a continuous mode for sending ADC values     #
--#     without interruption by setting Switch 1 to off.                     #
--#     If one cycle is complete and BYTE_COUNT reaches the max value,       #
--#     the FPGA waits for the characters "sd" from the PC.                  #
--#                                                                          #
--#     This is used for communication in both directions, from PC to FPGA   #
--#     and from FPGA to PC. Only if the PC sends "sd" the FPGA will send    #
--#     the next values. So this function is currenty used as software       #
--#     flow control. (additional commands can be added)                     #
--#                                                                          #
--#     Currently the FPGA does not automatically reset the received "sd",   #
--#     so it will enter continuous transimission as long as the last data   #
--#     received equals "sd".                                                #
--#                                                                          #
--#     Switch 0 turns hardware flow control on or of                        #
--#                                                                          #
--############################################################################  
    
    PROCESS(CLOCK_50,TX_BUSY,TX_FINISHED,TX_START)
        VARIABLE BYTE_COUNT : INTEGER := 0;
        BEGIN
            IF(CLOCK_50'EVENT AND CLOCK_50='1')THEN
                OVERFLOW <= UART_CTS;
                UART_RTS <= '0';
                IF(((MY_DATA(0) = "01110011" AND MY_DATA(1) = "01100100") OR s_SW(1) = '0') AND BYTE_COUNT = 100000) THEN
                    BYTE_COUNT:= 0;
                end if;
                If(BYTE_COUNT < 100000) THEN
                    IF(TX_BUSY='0' AND (s_SW(0) = '0' OR OVERFLOW='0')) THEN
                        TX_DATA<=rx_DATA_SPI;
                        TX_START<='1';
                        BYTE_COUNT:= BYTE_COUNT + 1;
                    ELSIF(TX_FINISHED='1') THEN
                        TX_START<='0';
                    END IF; 
                ELSIF(TX_FINISHED='1') THEN
                    TX_START<='0';
                END IF;
            END IF;
        END PROCESS;

--############################################################################
--#                                                                          #
--#     If the FPGA receives data from the UART, this process checks for     #
--#     the sequence "sd" and saves the received data in a byte array.       #
--#                                                                          #
--############################################################################          
        
    PROCESS(CLOCK_50,RX_FINISHED)
        VARIABLE RX_INDEX: INTEGER:=0;
        BEGIN
            if(CLOCK_50'event and CLOCK_50 = '1') then
                if(RX_FINISHED = '1') then
                    If(RX_INDEX = 0) THEN
                        if(RX_DATA = "01110011") THEN
                            MY_DATA(0):=RX_DATA;
                            RX_INDEX := RX_INDEX + 1;
                        else
                            MY_DATA(0):="00000000";
                            MY_DATA(1):="00000000";
                            RX_INDEX := 0;
                        end if;
                    elsif(RX_INDEX = 1) THEN
                        if(RX_DATA = "01100100") THEN
                            MY_DATA(1):=RX_DATA;
                            RX_INDEX := 0;
                        else
                            RX_INDEX := 0;
                            MY_DATA(0):="00000000";
                            MY_DATA(1):="00000000";
                        end if; 
                    end if;
                    LED(9 downto 2)<=RX_DATA;
                end if;
            end if;
        END PROCESS;
END STRUC;