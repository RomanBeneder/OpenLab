-- MC8051 IP Core Demo Design, Top-level Testbench Architecture 
-- Date: 2013-02-25
-- http://embsys.technikum-wien.at

architecture sim of testbench is

  signal osc : std_logic := '0'; -- clock oscillator
  signal button2 : std_logic := '0'; -- low-active reset coming from reset button  
  signal ledg_o : std_logic_vector(7 downto 0); -- LEDs LEDG0 - LEDG7
  
begin

  osc   <= not osc after 10 ns; -- 50 MHz clock
  button2  <= '0', '1' after 200 ns; -- low-active reset after power-up

  top : entity work.fpga_top
    port map (
      clk       => osc,
      reset_n_i => button2,
      ledg_o    => ledg_o
      );
    
end;
