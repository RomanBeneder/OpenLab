LIBRARY ieee;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.all;

ENTITY SERIAL_COM_8N1_SPEEDSEL IS
	PORT(CLOCK_120 : IN STD_LOGIC;
		  UART_TXD : OUT STD_LOGIC;
		  UART_RXD : IN STD_LOGIC;
		  TX_DATA : IN STD_LOGIC_VECTOR(7 DOWNTO 0);
		  RX_DATA : OUT STD_LOGIC_VECTOR(7 DOWNTO 0);
		  TX_BUSY : OUT STD_LOGIC;
		  TX_START : IN STD_LOGIC;
		  TX_FINISHED : OUT STD_LOGIC;
		  RX_FINISHED : OUT STD_LOGIC;
		  COMSPEED_SEL_SW : IN STD_LOGIC_VECTOR(1 downto 0));
END SERIAL_COM_8N1_SPEEDSEL;

ARCHITECTURE STRUC OF SERIAL_COM_8N1_SPEEDSEL IS

	 COMPONENT UART_TX
      PORT (CLK: IN std_logic;
            START: IN std_logic;
            BUSY: OUT std_logic;
            DATA: IN std_logic_vector(7 downto 0);
            TX_LINE: OUT STD_logic;
            FINISHED: OUT STD_LOGIC;
				SPEED_SELECT: IN std_logic_vector(1 downto 0));
    END COMPONENT UART_TX;
	 
	 COMPONENT UART_RX
      PORT (CLK: IN std_logic;
            DATA: OUT std_logic_vector(7 downto 0);
            RX_LINE: IN STD_logic;
				FINISHED: OUT STD_LOGIC;
				SPEED_SELECT: IN std_logic_vector(1 downto 0));
    END COMPONENT UART_RX;

BEGIN	
	 C0: UART_TX PORT MAP (CLOCK_120,TX_START,TX_BUSY,TX_DATA,UART_TXD,TX_FINISHED,COMSPEED_SEL_SW(1 downto 0));
	 C1: UART_RX PORT MAP (CLOCK_120,RX_DATA,UART_RXD,RX_FINISHED,COMSPEED_SEL_SW(1 downto 0));
	 
END STRUC;
	 