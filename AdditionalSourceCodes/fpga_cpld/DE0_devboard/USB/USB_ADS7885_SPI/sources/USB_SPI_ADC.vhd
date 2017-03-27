-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer (USB) - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         USB_SPI_ADC
--
-- FILENAME:       USB_SPI_ADC.vhd
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
-- DESCRIPTION:    Main component for handling ADC access
--                 and data transmission (USB)
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


entity USB_SPI_ADC is
  port (CLOCK_50: in std_logic;
        SW: in std_logic_vector(9 downto 0);
        KEY: in std_logic_vector (2 downto 0);
        LED: out std_logic_vector (9 downto 0);
        USB_DATA: inout STD_LOGIC_vector(7 downto 0);
        USB_RXF:    in std_logic;
        USB_TXE: in std_logic;
        USB_RD:     out std_logic;
        USB_WR:  out std_logic;

        sclk_SPI: out std_LOGIC;
        CS_SPI: out std_logic;
        sdio_SPI: in std_logic);

end USB_SPI_ADC;

ARCHITECTURE MAIN OF USB_SPI_ADC IS

    COMPONENT PLL
      PORT (inclk0: IN std_LOGIC;
            c0: OUT std_LOGIC);
    END COMPONENT PLL;

    COMPONENT debounce
      PORT (clk: IN STD_LOGIC;      --input clock
            button: IN STD_LOGIC;   --input signal to be debounced
            result: OUT STD_LOGIC); --debounced signal
    END comPONENT debounce;

    COMPONENT SPI_ADC
      PORT (clock: IN STD_LOGIC;  --system clock
            sclk: OUT STD_LOGIC;  --spi clock
            CS: OUT STD_LOGIC;    --slave select
            sdio: IN STD_LOGIC;   --serial data input output
            busy: OUT STD_LOGIC;  --busy / data ready signal
            rx_data: OUT STD_LOGIC_VECTOR(7 DOWNTO 0)); --data received
    END COMPONENT SPI_ADC;

    COMPONENT USB_FTDI
      port (CLOCK_50: in std_logic;
            DATA: inout STD_LOGIC_vector(7 downto 0);
            RXF: in std_logic;
            TXE: in std_logic;
            RD: out std_logic;
            WR: out std_logic;
            READ_START: in std_logic;
            WRITE_START: in std_logic;
            READ_FINISHED: out std_logic;
            WRITE_FINISHED: out std_logic;
            DATA_SEND: in std_logic_vector(7 downto 0);
            DATA_RECEIVED: out std_logic_vector(7 downto 0);
            BUSY_FTDI: out std_logic);
    END COMPONENT USB_FTDI;
    
    SIGNAL BYTE_COUNT : integer := 0;

    SIGNAL TEMP: std_logic;

    SIGNAL s_SW: STD_LOGIC_vector (9 downto 0);
    --SIGNAL s_KEY: std_logic_vector (2 downto 0);
    SIGNAL COUNT:INTEGER:=0;

    TYPE MY_ARRAY is array (0 to 1) of  STD_LOGIC_VECTOR(7  downto 0);
    SIGNAL MY_DATA: MY_ARRAY;

    SIGNAL busy_SPI:std_LOGIC:='0';
    SIGNAL rx_data_SPI:std_LOGIC_VECTOR(7 downto 0);
    SIGNAL ADC_CLK_SPI:std_LOGIC:='0';

    SIGNAL READ_START_S:std_logic;
    SIGNAL WRITE_START_S:std_logic;
    SIGNAL READ_FINISHED_S:std_logic;
    SIGNAL WRITE_FINISHED_S:std_logic;
    SIGNAL DATA_SEND_S:std_logic_vector(7 downto 0);
    SIGNAL DATA_RECEIVED_S:std_logic_vector(7 downto 0);
    SIGNAL BUSY_FTDI_S:std_LOGIC;

    SIGNAL USB_RD_S:std_logic;
    SIGNAL USB_WR_S:std_logic;

BEGIN

    C0: PLL PORT MAP (CLOCK_50,ADC_CLK_SPI);

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

    C1: SPI_ADC PORT MAP (ADC_CLK_SPI,sclk_SPI,CS_SPI,sdio_SPI,busy_SPI,rx_data_SPI);
    C2: USB_FTDI PORT MAP (CLOCK_50,USB_DATA,USB_RXF,USB_TXE,USB_RD_S,USB_WR_S,
                            READ_START_S,WRITE_START_S,READ_FINISHED_S,
                            WRITE_FINISHED_S,DATA_SEND_S,DATA_RECEIVED_S,BUSY_FTDI_S);

    PROCESS(ADC_CLK_SPI)
    BEGIN
        IF(ADC_CLK_SPI'EVENT AND ADC_CLK_SPI='1')THEN
            IF(USB_TXE = '0' and USB_RXF = '1' and BUSY_FTDI_S = '0') then
                DATA_SEND_S <= rx_data_SPI;
            end if;

            LED(9 downto 2) <= rx_data_SPI;
            LED(0) <= USB_RD_S;
            LED(1) <= USB_WR_S;
        END IF;
    END PROCESS;

    USB_RD <= USB_RD_S;
    USB_WR <= USB_WR_S;

END MAIN;