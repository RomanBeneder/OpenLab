-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         TX
--
-- FILENAME:       TX.vhd
-- 
-- ARCHITECTURE:   STRUC
-- 
-- ENGINEER:       Patrick Schmitt
--
-- DATE:           16. September 2015
--
-- VERSION:        1.0
--
-------------------------------------------------------------------------------
--                                                                      
-- DESCRIPTION:    Component handling UART data transmission
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

entity UART_TX is
  port (CLK: IN std_logic;
        START: IN std_logic;
        BUSY: OUT std_logic;
        DATA: IN std_logic_vector(7 downto 0);
        TX_LINE: OUT STD_logic;
        FINISHED: OUT STD_LOGIC;
		  SPEED_SELECT: IN std_logic_vector(1 downto 0));
END UART_TX;

ARCHITECTURE STRUC OF UART_TX IS


--	 SIGNAL PRSCL: INTEGER RANGE 0 TO 59:=0;		 --2000000baud stable with "FHTW"-FTDI Cable

--	 SIGNAL PRSCL: INTEGER RANGE 0 TO 79:=0;		 --1500000baud stable

	 SIGNAL PRSCL: INTEGER RANGE 0 TO 99:=0;		 --1200000baud stable
	 
    SIGNAL INDEX: INTEGER RANGE 0 TO 9:=0;          
    SIGNAL DATAFLL: STD_logic_vector(9 downto 0);
    SIGNAL TX_FLG: STD_logic:='0';
    SIGNAL S_BUSY: std_logic:='0';
	 SIGNAL START_S: std_logic:='0';

BEGIN

--############################################################################
--#                                                                          #
--#     This process splits the, to be sent, byte into its bits and adds     #
--#     the control bits (stop and start) to the data.                       #
--#                                                                          #
--#     Each bit is then sent according to the selected baudrate.            #
--#     The frame of the serial communication is fixed to 8 data bits,       #
--#     1 stop and 1 start bit.                                              #
--#                                                                          #
--############################################################################

    PROCESS(CLK,S_BUSY)
        BEGIN
            IF(CLK'EVENT AND CLK ='1') then
                IF(INDEX<9)THEN
                    FINISHED <= '0';
                end if;
    
                IF(TX_FLG='0' AND START = '1') then
                    TX_FLG<='1';
                    S_BUSY<='1';
                    DATAFLL(0)<='0';
                    DATAFLL(9)<='1';
                    DATAFLL(8 downto 1) <=DATA;
                END IF;
            
                if(TX_FLG='1') then

						if(SPEED_SELECT = "10") then
					 
						  IF(PRSCL < 58) then			 		  --2000000baud stable with "FHTW"-FTDI Cable
								PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
						  
						  IF(PRSCL = 29) then					  --2000000baud stable with "FHTW"-FTDI Cable
							TX_LINE<=DATAFLL(INDEX);
                        IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            TX_FLG<='0';
                            S_BUSY<='0';
                            INDEX<=0;
                            FINISHED <= '1';
                        END IF;
						  END IF;
						  
						elsif(SPEED_SELECT = "01") then	
						
						  IF(PRSCL < 78) then					  --1500000baud stable
								PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
						  
						  IF(PRSCL = 39) then					  --1500000baud stable
						  TX_LINE<=DATAFLL(INDEX);
                        IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            TX_FLG<='0';
                            S_BUSY<='0';
                            INDEX<=0;
                            FINISHED <= '1';
                        END IF;
						  END IF;
						  
						elsif(SPEED_SELECT = "00") then	
						
						  IF(PRSCL < 98) then					  --1200000baud stable
							
                        PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;

						  IF(PRSCL = 49) then					  --1200000baud stable
							TX_LINE<=DATAFLL(INDEX);
                        IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            TX_FLG<='0';
                            S_BUSY<='0';
                            INDEX<=0;
                            FINISHED <= '1';
                        END IF;
						  END IF;      
                END IF;
					 
            END IF;
			END IF;
            BUSY <= S_BUSY;
        END PROCESS;
END STRUC;