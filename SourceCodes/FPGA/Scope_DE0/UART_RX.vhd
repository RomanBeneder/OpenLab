-------------------------------------------------------------------------------
--                                                                      
--                 SPI ADC Interface + Data Transfer - Example
--  
-------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         RX
--
-- FILENAME:       RX.vhd
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
-- DESCRIPTION:    Component receiving UART data
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

entity UART_RX is
  port (CLK: IN std_logic;
        DATA: OUT std_logic_vector(7 downto 0);
        RX_LINE: IN STD_logic;
        FINISHED: OUT STD_LOGIC;
		  SPEED_SELECT: IN std_logic_vector(1 downto 0));
END UART_RX;

ARCHITECTURE STRUC OF UART_RX IS

    
--	 SIGNAL PRSCL: INTEGER RANGE 0 TO 59:=0;		 --2000000baud stable with "FHTW"-FTDI Cable

--	 SIGNAL PRSCL: INTEGER RANGE 0 TO 79:=0;		 --1500000baud stable

	 SIGNAL PRSCL: INTEGER RANGE 0 TO 99:=0;		 --1200000baud stable
   
    SIGNAL INDEX: INTEGER RANGE 0 TO 9:=0;
	 SIGNAL DATAFLL: STD_logic_vector(9 downto 0);
    SIGNAL RX_FLG: STD_LOGIC:='0';

BEGIN

--############################################################################
--#                                                                          #
--#     This process reads in the serial bit chain of one character send     #
--#     to the FPGA. The prescaler is used for correctly save every          #
--#     received bit according to the selected baudrate.                     #
--#                                                                          #
--#     The frame of the serial communication is fixed to 8 data bits,       #
--#     1 stop and 1 start bit.                                              #
--#                                                                          #
--############################################################################
    
    PROCESS(CLK)
        BEGIN
            IF(CLK'EVENT AND CLK='1')THEN
                IF(INDEX<9)THEN
                    FINISHED <= '0';
                end if;
    
                IF(RX_FLG='0' AND RX_LINE='0')THEN
                    INDEX<=0;
                    PRSCL<=0;
                    RX_FLG<='1';
                END IF;
    
                IF(RX_FLG='1') THEN
                    DATAFLL(INDEX)<=RX_LINE;
                  
						if(SPEED_SELECT = "10") then
						
						  IF(PRSCL < 58) then			 		  --2000000baud stable with "FHTW"-FTDI Cable
								PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
							
						  IF(PRSCL = 29) then					  --2000000baud stable with "FHTW"-FTDI Cable
							IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            IF(DATAFLL(0)='0'AND DATAFLL(9)='1')THEN
                                DATA<=DATAFLL(8 downto 1);
                            ELSE
                                DATA<=(OTHERS=>'0');
                            END IF;
                            RX_FLG<='0';
                            INDEX<=0;
                            FINISHED<='1';
                        END IF;
                    END IF;
						
						elsif(SPEED_SELECT = "01") then	
					
						  IF(PRSCL < 78) then					  --1500000baud stable
							PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
						  
						  IF(PRSCL = 39) then					  --1500000baud stable
							IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            IF(DATAFLL(0)='0'AND DATAFLL(9)='1')THEN
                                DATA<=DATAFLL(8 downto 1);
                            ELSE
                                DATA<=(OTHERS=>'0');
                            END IF;
                            RX_FLG<='0';
                            INDEX<=0;
                            FINISHED<='1';
                        END IF;
                    END IF;
						  
						elsif(SPEED_SELECT = "00") then	
						  
						  IF(PRSCL < 98) then					  --1200000baud stable
                        PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
						  
						  IF(PRSCL = 49) then					  --1200000baud stable
							IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            IF(DATAFLL(0)='0'AND DATAFLL(9)='1')THEN
                                DATA<=DATAFLL(8 downto 1);
                            ELSE
                                DATA<=(OTHERS=>'0');
                            END IF;
                            RX_FLG<='0';
                            INDEX<=0;
                            FINISHED<='1';
                        END IF;
                    END IF;
                  END IF;
						
                END IF;
            END IF;
        END PROCESS;
END STRUC;
            