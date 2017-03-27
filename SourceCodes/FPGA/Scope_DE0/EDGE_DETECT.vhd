library ieee;
use ieee.std_logic_1164.all;
use ieee.numeric_std.all;

entity edge_detect is

  port (async_sig : in std_logic;
        clk       : in std_logic;
        rise      : out std_logic;
        fall      : out std_logic;
		  EDGE_DETECT_SPEED : in std_logic_vector(1 downto 0);
		  FREQUENCY	: OUT std_logic_vector(31 downto 0));
end;

architecture RTL of edge_detect is

signal TRIG_FIRST_IMPULS : std_logic := '0';
signal TRIG_SECOND_IMPULS : std_logic := '0';
signal rise_s : std_logic := '0';
signal fall_s : std_logic := '0';

constant all_ones_slow_0 : std_logic_vector (100 downto 0) := (others => '1');
constant all_zeros_slow_0 : std_logic_vector (100 downto 0) := (others => '0');
constant all_ones_slow_1 : std_logic_vector (145 downto 0) := (others => '1');
constant all_zeros_slow_1 : std_logic_vector (145 downto 0) := (others => '0');
constant all_ones_slow_2 : std_logic_vector (360 downto 0) := (others => '1');
constant all_zeros_slow_2 : std_logic_vector (360 downto 0) := (others => '0');
constant all_ones_fast : std_logic_vector (7 downto 0) := (others => '1');			-- default 6 downto 0
constant all_zeros_fast : std_logic_vector (7 downto 0) := (others => '0');		-- default 6 downto 0
begin


process (clk)
VARIABLE input_s_slow_0 : std_logic_vector(101 downto 0);
VARIABLE input_s_slow_1 : std_logic_vector(146 downto 0);
VARIABLE input_s_slow_2 : std_logic_vector(361 downto 0);
VARIABLE input_s_fast : std_logic_vector(8 downto 0);
VARIABLE old_val : std_logic := '0';
VARIABLE new_val : std_logic := '0';

begin
 if rising_edge( clk ) then 
	 input_s_slow_0 := async_sig & input_s_slow_0(101 downto 1);
	 input_s_slow_1 := async_sig & input_s_slow_1(146 downto 1);
	 input_s_slow_2 := async_sig & input_s_slow_2(361 downto 1);  
	 input_s_fast := async_sig & input_s_fast(8 downto 1);
    rise_s <= '0';
	 fall_s <= '0';
	if((input_s_slow_2(360 downto 0) = all_ones_slow_2 and EDGE_DETECT_SPEED = "10") or (input_s_slow_1(145 downto 0) = all_ones_slow_1 and EDGE_DETECT_SPEED = "01") or (input_s_slow_0(100 downto 0) = all_ones_slow_0 and EDGE_DETECT_SPEED = "00") or (input_s_fast(7 downto 0) = all_ones_fast and EDGE_DETECT_SPEED = "11")) then
		old_val := new_val;
		new_val := '1';
		if(old_val = '0' and new_val = '1') then
			rise_s <= '1';
			fall_s <= '0';
		end if;
	elsif((input_s_slow_2(360 downto 0) = all_zeros_slow_2 and EDGE_DETECT_SPEED = "10") or (input_s_slow_1(145 downto 0) = all_zeros_slow_1 and EDGE_DETECT_SPEED = "01") or (input_s_slow_0(100 downto 0) = all_zeros_slow_0 and EDGE_DETECT_SPEED = "00") or (input_s_fast(7 downto 0) = all_zeros_fast and EDGE_DETECT_SPEED = "11")) then
		old_val := new_val;
		new_val := '0';
		if(old_val = '1' and new_val = '0') then
			rise_s <= '0';
			fall_s <= '1';
		end if;
	end if;
end if;	
end process ;

process(clk,rise_s,fall_s)
VARIABLE FREQ_CNT : unsigned(31 downto 0) := to_unsigned(0,32);
VARIABLE START_FREQ_CNT : std_logic := '0';
VARIABLE SIGNAL_FREQ_DONE : std_logic := '0';
VARIABLE TRIG_FLAG : std_logic := '0';
begin
if rising_edge (clk) then


if(rise_s = '1' and fall_s = '0' and TRIG_FLAG = '0') then
				TRIG_FLAG := '1';
end if;
if(SIGNAL_FREQ_DONE = '0') then
if(TRIG_FLAG = '1' and TRIG_FIRST_IMPULS = '0' and TRIG_SECOND_IMPULS = '0') then
				FREQ_CNT := to_unsigned(0,32);
				TRIG_FIRST_IMPULS <= '1';
				TRIG_FLAG := '0';
			elsif(TRIG_FLAG = '1' and TRIG_FIRST_IMPULS = '1' and TRIG_SECOND_IMPULS = '0') then
				FREQ_CNT := to_unsigned(0,32);
				START_FREQ_CNT := '1';
				TRIG_SECOND_IMPULS <= '1';
				TRIG_FLAG := '0';
			elsif(TRIG_FLAG = '1' and TRIG_FIRST_IMPULS = '1' and TRIG_SECOND_IMPULS = '1') then
				START_FREQ_CNT := '0';
				TRIG_FIRST_IMPULS <= '0';
				TRIG_SECOND_IMPULS <= '0';
				SIGNAL_FREQ_DONE := '1';
				TRIG_FLAG := '0';
	end if;
	
		if(TRIG_FLAG = '0' and FREQ_CNT /= 4294967295) then
			FREQ_CNT := FREQ_CNT + 1;
			elsif(TRIG_FLAG = '0' and FREQ_CNT = 4294967295) then
			START_FREQ_CNT := '0';
				TRIG_FIRST_IMPULS <= '0';
				TRIG_SECOND_IMPULS <= '0';
				SIGNAL_FREQ_DONE := '1';
			end if;	
		end if;
		if(START_FREQ_CNT = '1' and SIGNAL_FREQ_DONE = '0') then
			FREQ_CNT := FREQ_CNT + 1;
		end if;
	if(SIGNAL_FREQ_DONE = '1') then
	FREQUENCY <= std_logic_vector(FREQ_CNT);
	SIGNAL_FREQ_DONE := '0';
	end if;
	
end if;
end process;
rise <= rise_s;
fall <= fall_s;
end architecture;

