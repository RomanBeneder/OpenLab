-- MC8051 IP Core Demo Design, Top-level Entity
-- Date: 2013-02-25
-- http://embsys.technikum-wien.at

library IEEE; 
use IEEE.std_logic_1164.all; 

library work;
use work.mc8051_p.all;

entity fpga_top is
  port (
        clk       : in  std_logic;                   -- 50 MHz clock signal coming from clock oscillator
        reset_n_i : in  std_logic;                   -- low-active reset signal coming from BUTTON2
        ledg_o    : out std_logic_vector(7 downto 0); -- 8 green LEDs LEDG0 - LEDG7
		  rx			: in  std_logic_vector(C_IMPL_N_SIU-1 downto 0);
		  tx			: out std_logic_vector(C_IMPL_N_SIU-1 downto 0)
       );
end fpga_top;

--entity fpga_top is
--  port (
--        clk       : in  std_logic;                   -- 50 MHz clock signal coming from clock oscillator
--        reset_n_i : in  std_logic;                   -- low-active reset signal coming from BUTTON2
--        ledg_o    : out std_logic_vector(7 downto 0); -- 8 green LEDs LEDG0 - LEDG7
--		  rx			: in  std_logic;
--		  tx			: out std_logic
--       );
--end fpga_top;