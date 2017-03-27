--Lothar Miller / Am Bläsistock 8 / 88483 Rot / Lothar@Lothar-Miller.de

library IEEE;
use IEEE.STD_LOGIC_1164.ALL;
use IEEE.NUMERIC_STD.ALL;

entity OPENLAB_FIFO is
    Generic (
           Addrbreite  : natural := 12;  -- Speicherlänge = 2^Addrbreite
           Wortbreite  : natural := 9
           );
    Port ( Din   : in  STD_LOGIC_VECTOR (Wortbreite-1 downto 0);
           Wr    : in  STD_LOGIC;
           Dout  : out STD_LOGIC_VECTOR (Wortbreite-1 downto 0);
           Rd    : in  STD_LOGIC;
           Empty : out STD_LOGIC;
           Full  : out STD_LOGIC;
           CLK   : in  STD_LOGIC;
			  Reset : in STD_LOGIC;
			  mode : in std_logic
           );
end OPENLAB_FIFO;

architecture Verhalten of OPENLAB_FIFO is

type speicher is array(0 to (2**Addrbreite)-1) of unsigned(Wortbreite-1 downto 0);
signal memory : speicher;   
signal full_loc  : std_logic;
signal empty_loc : std_logic;
signal wrcnt : unsigned (Addrbreite-1 downto 0) := (others => '0');
signal rdcnt : unsigned (Addrbreite-1 downto 0) := (others => '0');

begin
  process(CLK)
 VARIABLE wrcnt_VR : unsigned (Addrbreite-1 downto 0) := (others => '0');
 VARIABLE rdcnt_VR : unsigned (Addrbreite-1 downto 0) := (others => '0'); 
  begin
  if(CLK'event and CLK = '1') then
	  if(Reset = '1') then
		wrcnt_VR := (others => '0');
		rdcnt_VR := (others => '0');
	  else
	  
		if(full_loc = '1' and mode = '1') then
			wrcnt_VR := (others => '0');
			rdcnt_VR := (others => '0');
		else
			if (Wr='1' and (full_loc='0' or mode = '1')) then
				memory(to_integer(wrcnt_VR)) <= unsigned(Din);
				wrcnt_VR := wrcnt_VR+1;
			end if;
			if (Rd='1' and (empty_loc='0' or mode = '1')) then
				rdcnt_VR := rdcnt_VR+1;
			end if;
		end if;
	  end if;
	 wrcnt <= wrcnt_VR;
	 rdcnt <= rdcnt_VR;
	end if;
  end process;
  Dout <= std_logic_vector(memory(to_integer(rdcnt)));
  full_loc  <= '1' when rdcnt = wrcnt+1 else '0';
  empty_loc <= '1' when rdcnt = wrcnt   else '0';
  Full  <= full_loc;
  Empty <= empty_loc;
end Verhalten;