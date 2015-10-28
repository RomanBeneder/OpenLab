-------------------------------------------------------------------------------
--                                                                      
--                 Parallel ADC Interface + Data Transfer - Example
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

entity RX is
  port (CLK: IN std_logic;
        BUSY: OUT std_logic;
        DATA: OUT std_logic_vector(7 downto 0);
        RX_LINE: IN STD_logic;
        FINISHED: OUT STD_LOGIC);
END RX;

ARCHITECTURE STRUC OF RX IS

    SIGNAL DATAFLL: STD_logic_vector(9 downto 0);
    SIGNAL RX_FLG: STD_LOGIC:='0';
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 50:=0;   --1000000baud stable
    SIGNAL PRSCL: INTEGER RANGE 0 TO 25:=0;     --2000000baud stable!
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 54:=0;   --921600baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 434:=0;  --115200baud
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 217:=0;  --230400baud
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 434:=0;  --115200baud
    SIGNAL INDEX: INTEGER RANGE 0 TO 9:=0;
    SIGNAL S_BUSY: STD_LOGIC:='0';

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

    PROCESS(CLK,S_BUSY)
        BEGIN
            IF(CLK'EVENT AND CLK='1')THEN
                IF(INDEX<9)THEN
                    FINISHED <= '0';
                end if;
    
                IF(RX_FLG='0' AND RX_LINE='0')THEN
                    INDEX<=0;
                    PRSCL<=0;
                    S_BUSY<='1';
                    RX_FLG<='1';
                END IF;
    
                IF(RX_FLG='1') THEN
                    DATAFLL(INDEX)<=RX_LINE;
                    --IF(PRSCL < 49) then           --1000000baud stable
                    IF(PRSCL < 24) then             --2000000baud stable!
                    --IF(PRSCL < 53) then           --921600baud stable
                    --IF(PRSCL/=434) then           --115200baud
                    --IF(PRSCL < 216) then          --230400baud
                    --IF(PRSCL/=434) then           --115200baud
                        PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
                    --IF(PRSCL=25)THEN              --1000000baud stable
                    IF(PRSCL=12) then               --2000000baud stable!
                    --IF(PRSCL=27) then             --921600baud stable
                    --IF(PRSCL=217) then            --115200baud
                    --IF(PRSCL=108) then            --230400baud
                    --IF(PRSCL=217) then            --115200baud
                        IF(INDEX<9)THEN
                            INDEX<=INDEX+1;
                        ELSE
                            IF(DATAFLL(0)='0'AND DATAFLL(9)='1')THEN
                                DATA<=DATAFLL(8 downto 1);
                            ELSE
                                DATA<=(OTHERS=>'0');
                            END IF;
                            RX_FLG<='0';
                            S_BUSY<='0';
                            INDEX<=0;
                            FINISHED<='1';
                        END IF;
                    END IF;
                END IF;
            END IF;
        BUSY <= S_BUSY;
        END PROCESS;
END STRUC;
            