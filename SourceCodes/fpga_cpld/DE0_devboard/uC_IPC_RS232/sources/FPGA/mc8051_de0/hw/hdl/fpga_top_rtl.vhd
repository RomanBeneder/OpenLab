-- MC8051 IP Core Demo Design, Top-level Architecture
-- Date: 2013-02-25
-- http://embsys.technikum-wien.at

architecture rtl of fpga_top is
       
  signal s_p2_o : std_logic_vector(7 downto 0); -- 8-bit port P2 of mc8051
  signal s_tx	 : std_logic_vector(C_IMPL_N_SIU-1 downto 0);
  
  signal s_locked : std_logic; -- becomes high when PLL is locked
  signal s_sync_locked : std_logic_vector(2 downto 0); -- sync shift register

  signal s_reset : std_logic; -- high-active global reset signal
  signal s_reset_8051 : std_logic; -- high-active reset for mc8051
  signal s_clk_8051 : std_logic; -- 25 MHz clock for mc8051
   
  begin

  -- invert low-actice reset signal coming from push button:
  s_reset <= not reset_n_i;

  -- Generates reset signal for the mc8051:
  -- Synchronizes signal "locked" from the PLL to the 25 MHz domain
  -- and deassert mc8051 reset at the falling clock edge  
  p_reset_generator : process (s_reset, s_clk_8051)
  begin
    if s_reset = '1' then
      s_reset_8051 <= '1';
      s_sync_locked <= (others => '0');
    elsif s_clk_8051'event and s_clk_8051='0' then
      s_sync_locked(0) <= s_locked;
      s_sync_locked(1) <= s_sync_locked(0);
      s_sync_locked(2) <= s_sync_locked(1);
        
      if (s_sync_locked(1)='1') and (s_sync_locked(2)='0') then
        s_reset_8051 <= '0';
      end if;
    end if;  
  end process p_reset_generator;

  -- PLL divides clock frequency by 2:
  i_prescaler : prescaler PORT MAP (
		areset	 => s_reset,
		inclk0	 => clk,        -- PLL input, 50 MHz
		c0	     => s_clk_8051, -- PLL output, 25 MHz
		locked	 => s_locked    -- becomes high if PLL is locked
	);
   
  -- instantiation of the mc8051 IP core:
  i_mc8051_top : mc8051_top
    port map (
      reset     => s_reset_8051,
      int0_i    => (others => '1'), -- not used in this design
      int1_i    => (others => '1'), -- not used in this design
      all_t0_i  => (others => '0'), -- not used in this design
      all_t1_i  => (others => '0'), -- not used in this design
      all_rxd_i => rx, 					-- SERIAL
      all_rxd_o => open,            -- not used in this design
      all_txd_o => s_tx,            	-- SERIAL  
      clk       => s_clk_8051,
      p0_i      => (others => '0'), -- not used in this design
      p1_i      => (others => '0'), -- not used in this design
      p2_i      => (others => '0'), -- not used in this design
      p3_i      => (others => '0'), -- not used in this design
      p0_o      => open,            -- not used in this design
      p1_o      => open,            -- not used in this design
      p2_o      => s_p2_o,
      p3_o      => open,            -- not used in this design
      test_o    => open);           -- not used in this design

  -- Only LEDG0 is used in this design:
  ledg_o(0) <= s_p2_o(0);
  ledg_o(7 downto 1) <= (others => '0');
  tx <= s_tx;
end rtl;

---- MC8051 IP Core Demo Design, Top-level Architecture
---- Date: 2013-02-25
---- http://embsys.technikum-wien.at
--
--architecture rtl of fpga_top is
--       
--  signal s_p2_o : std_logic_vector(7 downto 0); -- 8-bit port P2 of mc8051
--  --signal s_tx	 : std_logic_vector(C_IMPL_N_SIU-1 downto 0);
--  signal s_rx	 : std_logic;
--  signal s_tx	 : std_logic;
--  signal s_par_en : std_logic;
--  signal s_tx_req : std_logic;
--  signal s_tx_end : std_logic;
--  signal s_tx_data : std_logic_vector(7 downto 0);
--  signal s_rx_ready : std_logic;
--  signal s_rx_data  : std_logic_vector(7 downto 0);
--  
--  signal s_locked : std_logic; -- becomes high when PLL is locked
--  signal s_sync_locked : std_logic_vector(2 downto 0); -- sync shift register
--
--  signal s_reset : std_logic; -- high-active global reset signal
--  signal s_reset_8051 : std_logic; -- high-active reset for mc8051
--  signal s_clk_8051 : std_logic; -- 25 MHz clock for mc8051
--   
--  begin
--
--  -- invert low-actice reset signal coming from push button:
--  s_reset <= not reset_n_i;
--
--  -- Generates reset signal for the mc8051:
--  -- Synchronizes signal "locked" from the PLL to the 25 MHz domain
--  -- and deassert mc8051 reset at the falling clock edge  
--  p_reset_generator : process (s_reset, s_clk_8051)
--  begin
--    if s_reset = '1' then
--      s_reset_8051 <= '1';
--      s_sync_locked <= (others => '0');
--    elsif s_clk_8051'event and s_clk_8051='0' then
--      s_sync_locked(0) <= s_locked;
--      s_sync_locked(1) <= s_sync_locked(0);
--      s_sync_locked(2) <= s_sync_locked(1);
--        
--      if (s_sync_locked(1)='1') and (s_sync_locked(2)='0') then
--        s_reset_8051 <= '0';
--      end if;
--    end if;  
--  end process p_reset_generator;
--
--  -- PLL divides clock frequency by 2:
--  i_prescaler : prescaler PORT MAP (
--		areset	 => s_reset,
--		inclk0	 => clk,        -- PLL input, 50 MHz
--		c0	     => s_clk_8051, -- PLL output, 25 MHz
--		locked	 => s_locked    -- becomes high if PLL is locked
--	);
--   
--  i_uart : uart PORT MAP (
--		clk => clk,
--		rst => s_reset,
--		rx  => s_rx,
--		tx  => s_tx,
--		par_en => s_par_en,
--		tx_req => s_tx_req,
--		tx_end => s_tx_end,
--		tx_data => s_tx_data,
--		rx_ready => s_rx_ready,
--		rx_data => s_rx_data
--  );
--  
--  
--	
--  -- instantiation of the mc8051 IP core:
--  i_mc8051_top : mc8051_top
--    port map (
--      reset     => s_reset_8051,
--      int0_i    => (others => '1'), -- not used in this design
--      int1_i    => (others => '1'), -- not used in this design
--      all_t0_i  => (others => '0'), -- not used in this design
--      all_t1_i  => (others => '0'), -- not used in this design
--      all_rxd_i => s_rx, 					-- SERIAL
--      all_rxd_o => open,            -- not used in this design
--      all_txd_o => s_tx,            	-- SERIAL  
--      clk       => s_clk_8051,
--      p0_i      => (others => '0'), -- not used in this design
--      p1_i      => s_rx_data, 		-- not used in this design
--      p2_i      => (others => '0'), -- not used in this design
--      p3_i      => (others => '0'), -- not used in this design
--      p0_o      => s_tx_data,       -- not used in this design
--      p1_o      => open,            -- not used in this design
--      p2_o      => s_p2_o,
--      p3_o      => open,            -- not used in this design
--      test_o    => open);           -- not used in this design
--
--  -- Only LEDG0 is used in this design:
--  ledg_o(0) <= s_p2_o(0);
--  ledg_o(7 downto 1) <= (others => '0');
--  tx <= s_tx;
--end rtl;