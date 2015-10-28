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

entity TX is
  port (CLK: IN std_logic;
        START: IN std_logic;
        BUSY: OUT std_logic;
        DATA: IN std_logic_vector(7 downto 0);
        TX_LINE: OUT STD_logic;
        FINISHED: OUT STD_LOGIC);
END TX;

ARCHITECTURE STRUC OF TX IS

    --SIGNAL PRSCL: INTEGER RANGE 0 TO 5208:=0;     --9600  baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 434:=0;      --115200baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 195:=0;      --256000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 217:=0;      --230400baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 108:=0;      --460800baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 100:=0;      --500000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 83:=0;       --600000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 82:=0;       --609756baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 81:=0;       --617284baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 80:=0;       --625000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 79:=0;       --632911baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 54:=0;       --921600baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 50:=0;       --1000000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 40:=0;       --1250000baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 32:=0;       --1562500baud stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 24:=0;       --2083333baud stable
    SIGNAL PRSCL: INTEGER RANGE 0 TO 25:=0;     --2000000baud stable!
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 20:=0;       --2500000baud NOT stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 21:=0;       --2380952baud NOT stable
    --SIGNAL PRSCL: INTEGER RANGE 0 TO 23:=0;       --2173913baud NOT stable
    SIGNAL INDEX: INTEGER RANGE 0 TO 9:=0;          
    SIGNAL DATAFLL: STD_logic_vector(9 downto 0);
    SIGNAL TX_FLG: STD_logic:='0';
    SIGNAL S_BUSY: std_logic:='0';

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
                    --IF(PRSCL/=434) then               --115200baud
                    --IF(PRSCL < 194) then              --256000baud
                    --IF(PRSCL < 5207) then             --9600baud
                    --IF(PRSCL < 216) then              --230400baud
                    --IF(PRSCL < 107) then              --460800baud
                    --IF(PRSCL < 99) then               --500000baud stable
                    --IF(PRSCL < 82) then               --600000baud stable
                    --IF(PRSCL < 81) then               --609756baud stable
                    --IF(PRSCL < 80) then               --617284baud stable
                    --IF(PRSCL < 79) then               --625000baud stable
                    --IF(PRSCL < 78) then               --632911baud stable
                    --IF(PRSCL < 53) then               --921600baud stable
                    --IF(PRSCL < 49) then               --1000000baud stable
                    --IF(PRSCL < 39) then               --1250000baud
                    --IF(PRSCL < 31) then               --1562500baud
                    --IF(PRSCL < 23) then               --2083333baud stable!
                    IF(PRSCL < 24) then                 --2000000baud stable!
                    --IF(PRSCL < 19) then               --2500000baud NOT stable
                    --IF(PRSCL < 20) then               --2380952baud NOT stable
                    --IF(PRSCL < 22) then               --2173913baud NOT stable
                        PRSCL<=PRSCL+1;
                    ELSE
                        PRSCL<=0;
                    END IF;
                    --IF(PRSCL=217) then                --115200baud
                    --IF(PRSCL=97) then                 --256000baud
                    --IF(PRSCL=2607) then               --9600baud
                    --IF(PRSCL=108) then                --230400baud
                    --IF(PRSCL=54) then                 --460800baud
                    --IF(PRSCL=50) then                 --500000baud stable
                    --IF(PRSCL=41) then                 --600000baud stable
                    --IF(PRSCL=41) then                 --609756baud stable
                    --IF(PRSCL=40) then                 --617284baud stable
                    --IF(PRSCL=40) then                 --625000baud stable
                    --IF(PRSCL=39) then                 --632911baud stable
                    --IF(PRSCL=27) then                 --921600baud stable
                    --IF(PRSCL=25) then                 --1000000baud stable
                    --IF(PRSCL=20) then                 --1250000baud 
                    --IF(PRSCL=16) then                 --1562500baud
                    --IF(PRSCL=12) then                 --2083333baud student PC failed!
                    IF(PRSCL=12) then                   --2000000baud stable!
                    --IF(PRSCL=10) then                 --2500000baud NOT stable
                    --IF(PRSCL=10) then                 --2380952baud NOT stable
                    --IF(PRSCL=11) then                 --2173913baud NOT stable
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
            BUSY <= S_BUSY;
        END PROCESS;
END STRUC;