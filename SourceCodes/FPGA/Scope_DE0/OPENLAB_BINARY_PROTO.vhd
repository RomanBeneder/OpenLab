-----------------------------------------------------------------------------------
--                                                                      
--                 OpenLab FPGA based low cost oscilloscope
--  
-----------------------------------------------------------------------------------
--                                                                      
-- ENTITY:         OPENLAB_BINARY_PROTO
--
-- FILENAME:       OPENLAB_BINARY_PROTO.vhd
-- 
-- ARCHITECTURE:   OPENLAB_BINARY_PROTO_ARCH
-- 
-- ENGINEER:       Patrick Schmitt
--
-- DATE:           17. September 2016
--
-- VERSION:        1.0
--
-----------------------------------------------------------------------------------
--                                                                      
-- DESCRIPTION:    Decoder/Encoder of the binary transfer protokoll (OpenLab device)
--
-----------------------------------------------------------------------------------
--
-- REFERENCES:     Serial communication originally by user "Anton Zero"
--                 under GNU license.
--                 Project page: https://github.com/AntonZero/UART
--                 Debounce component originally by Scott Larson - 
--                 Digi-Key Corporation
--                 Webpage: https://eewiki.net/
--                 
----------------------------------------------------------------------------------
--                                                                      
-- CHANGES:        Version 1.0 - PS - 17. September 2016
--
----------------------------------------------------------------------------------

LIBRARY IEEE;
USE ieee.std_logic_1164.all;
USE ieee.std_logic_unsigned.all;
USE ieee.numeric_std.all;

entity OPENLAB_BINARY_PROTO is
  port(
		  CLOCK_120   			 		: in  std_logic;
		  ACT_CMD	  			 		: out unsigned(3 downto 0);
		  SAMPLING_MODE   	 		: out std_logic;
		  AMP1_SEL				 		: out std_logic_vector (2 downto 0);
		  AMP2_SEL				 		: out std_logic_vector (2 downto 0);
		  TX_BUSY 				 		: in  std_logic;
		  TX_DATA 				 		: out std_logic_vector(7 downto 0);
		  RX_DATA 				 		: in  std_logic_vector(7 downto 0);
		  TX_START 						: out std_logic;
		  RX_FINISHED					: in  std_logic;
		  TX_FINISHED					: in  std_logic;
		  ETS_PACKET_SEND 			: out std_logic;
		  ETS_PACKET_READY_1  		: in  std_logic;
		  ETS_PACKET_READY_2  		: in  std_logic;
		  CHAN_1_ON				 		: out std_logic;
		  CHAN_2_ON				 		: out std_logic;
		  SAMPLE_DATA_RESET_1 		: in  std_logic;
		  SAMPLE_DATA_RESET_2 		: in  std_logic;
		  SAMPLERATE_CNTER 			: out unsigned(31 downto 0);
		  TRIGGER_TYPE       		: out unsigned(1 downto 0);
		  TRIGGER_CHAN					: out std_logic_vector(7 downto 0);
		  ACC_ROUNDS			 		: out unsigned(31 downto 0);
		  DUAL_CHANNEL_MODE_ON 		: out std_logic;
		  DUAL_CHANNEL_MODE_STATUS : out std_logic;
		  SAMPLES_PER_PACKET 		: out unsigned(15 downto 0);
		  PWM_RESET						: out std_logic;
		  PWM_DUTY						: out std_logic_vector(7 downto 0);
		  PWM_ENA						: out std_logic;
		  STATUS_LED					: out std_logic_vector(9 downto 0);
		  FIFO_CH1_RD					: out std_logic;
		  FIFO_CH2_RD					: out std_logic;
		  FIFO_CH1_EMPTY				: in  std_logic;
		  FIFO_CH2_EMPTY				: in  std_logic;
		  FIFO_CH1_DATAOUT			: in  std_logic_vector(8 downto 0);
		  FIFO_CH2_DATAOUT			: in  std_logic_vector(8 downto 0);
		  FREQUENCY_CH1_PORT			: in  std_logic_vector(31 downto 0);
		  FREQUENCY_CH2_PORT			: in  std_logic_vector(31 downto 0));
end OPENLAB_BINARY_PROTO;

architecture OPENLAB_BINARY_PROTO_ARCH of OPENLAB_BINARY_PROTO is
		
	 type HEADER_BUFFER_TYPE is array (0 to 4) of  std_logic_vector(7 downto 0);
	 type PAYLOAD_BUFFER_TYPE is array (0 to 10) of std_logic_vector(7 downto 0);
	 type PACKET_SEND_BUFFER_TYPE is array (0 to 532) of std_logic_vector(7 downto 0);
	 signal HEADER_DATA : HEADER_BUFFER_TYPE;
	 signal HEADER_DATA_SEND : HEADER_BUFFER_TYPE;
	 signal PAYLOAD_DATA : PAYLOAD_BUFFER_TYPE;
	 signal PAYLOAD_DATA_SEND : PAYLOAD_BUFFER_TYPE;
	 signal packet_send_buffer : PACKET_SEND_BUFFER_TYPE;
	 
	 type STATE_TYPE is (START,IDLE,CHECK_DATA,WAIT_PC_REC,CMD_STS,CMD_SCS,CMD_STB,CMD_SSM,REPLY_SD,REPLY_HWV,REPLY_SWV,ACK,NACK);
	 signal CMD_STATE,NEXT_STATE: STATE_TYPE:= START;
	 
	 constant CRC_POLY: std_logic_vector(15 downto 0) := x"1021";
	 signal TMP_CRC_HEAD : std_logic_vector(15 downto 0);
	 signal TMP_CRC_HEAD_SEND : std_logic_vector(15 downto 0);
	 signal TMP_CRC_PAYLOAD : std_logic_vector(15 downto 0);
	 signal TMP_CRC_PAYLOAD_SEND : std_logic_vector(15 downto 0);
	 signal START_CRC_HEADER : std_logic;
	 signal START_CRC_HEADER_SEND : std_logic;
	 signal START_CRC_PAYLOAD : std_logic;
	 signal START_CRC_PAYLOAD_SEND : std_logic;
	 signal HEADER_CRC_NOK : std_logic := '0';
	 signal PAYLOAD_CRC_NOK : std_logic := '0';
	 signal HEADER_CRC_OK: std_logic :='0';
	 signal PAYLOAD_CRC_OK: std_logic :='0';
	 
	 signal DUMMY_SENT : std_logic := '0';
	 signal CMD_CODE : std_logic_vector(7 downto 0);
	 signal CMD_CHAN : std_logic_vector(7 downto 0);
	 signal START_TRANSFER : std_logic := '0';
	 signal PACKET_READ : std_logic := '0';
	 signal PAYLOAD_SIZE : std_logic_vector(15 downto 0);
	 signal BYTE_COUNT : INTEGER := 0;
	 signal PACKET_AVAILABLE : std_logic := '0';
	 signal SAMPLES_PER_PACKET_S : unsigned(15 downto 0) := to_unsigned(512,16);
	 
	 signal DUAL_CHANNEL_MODE_READY : std_logic := '0';
	 signal DUAL_CHANNEL_MODE_ON_S : std_logic := '0';
	 signal DUAL_CHANNEL_MODE_STATUS_S : std_logic := '0';
	
	 signal header_complete : std_logic := '0';
	 signal header_send_complete : std_logic := '0';
	 signal payload_send_complete : std_logic := '0';
	 shared variable payload_complete : std_logic := '0';
	 shared variable reply_size : integer := 0;
	 
	 signal payload_rec_size: std_logic_vector(15 downto 0);
	 signal payload_send_size: std_logic_vector(15 downto 0);
	 signal CRC_header_calc_COMPLETE: std_logic := '0';
	 signal CRC_header_send_COMPLETE: std_logic := '0';
	 signal CRC_payload_calc_COMPLETE: std_logic := '0';
	 signal CRC_payload_send_COMPLETE: std_logic := '0';
	 
	 signal SAMPLERATE : std_logic_vector(31 downto 0);
	 signal SAMPLES_PER_PACKET_WH : unsigned(15 downto 0);
	 
	 signal TRIGGER_MODE: std_logic;
	 signal SAMPLING_MODE_S : std_logic := '0';
	 signal AMP1_SEL_SIG: std_logic_vector(2 downto 0) := "000";
	 signal AMP2_SEL_SIG: std_logic_vector(2 downto 0) := "000";
	 signal CHAN_1_ON_S : std_logic := '0';
	 signal CHAN_2_ON_S : std_logic := '0';
	 signal NACK_SEND :std_logic := '0';
	 signal ETS_PACKET_SEND_S : std_logic := '0';
	 signal PWM_DUTY_S : std_logic_vector(7 downto 0);
	 signal ACC_ROUNDS_S	: unsigned(31 downto 0);
	 signal FIFO_CH1_RD_S : std_logic := '0';
	 signal FIFO_CH2_RD_S : std_logic := '0';
	 
	 signal ERROR_CODE : std_logic_vector(7 downto 0);
	 
	 begin
	 
	process(CLOCK_120)
        begin
			if(CLOCK_120'event and CLOCK_120='1')then

				if((HEADER_CRC_NOK = '1' or PAYLOAD_CRC_NOK = '1') and NACK_SEND = '0') then
					if(BYTE_COUNT = reply_size) then
						CMD_STATE <= NACK;
					end if;
		else
				CMD_STATE <= NEXT_STATE;
				end if;
			end if;
			end process;
	 process(CLOCK_120)
	 
	 variable SD_OK : std_logic := '0';
	 variable ACK_OK : std_logic :='0';
	 variable ACK_READY : std_logic :='0';
	 variable HW_OK : std_logic :='0';
	 variable HW_READY : std_logic :='0';
	 variable SW_OK : std_logic :='0';
	 variable SW_READY : std_logic :='0';
	 variable WAIT_PC_BUFFER : integer range 0 to 5000000 := 0;
	 variable GET_SEND_DATA : std_logic := '0';
	 variable TRIGGER_FOUND_PACKET : std_logic := '0';
	 variable PAYLOAD_LOGIC_VECTOR_1 : std_logic_vector (15 downto 0) := x"0000";
	 variable PAYLOAD_LOGIC_VECTOR_2 : std_logic_vector (31 downto 0) := x"00000000";
	 
	 variable PACKET_BUILD_STATUS : std_logic := '1';
	 variable FIFO_EMPTY   : std_logic := '0';
	 variable FIFO_DATAOUT : std_logic_vector(8 downto 0) := "000000000";
	 variable PAYLOAD_FIXDATA_SEND : PAYLOAD_BUFFER_TYPE;
	 variable FIXED_PAYLOAD_CNT : integer range 0 to 12 := 0;
	 Variable SD_READY : std_logic := '0'; 
	 variable SAMPLE_INDEX : integer range 0 to 532 := 18;
	 variable PACKET_SEND_NUMBER: std_logic_vector(31 downto 0);
	 variable PACKET_SEND_NUMBER_CNT : unsigned(31 downto 0);
	 
	 begin
	 	if(CLOCK_120'event and CLOCK_120='1')then
		
		case CMD_STATE is
			when start =>
			ACT_CMD <= to_unsigned(0,4);
			if DUMMY_SENT = '0' then
				PWM_RESET <= '0';
				reply_size := 2;
				START_TRANSFER <= '1';
				if(BYTE_COUNT = reply_size) then
					DUMMY_SENT <= '1';
					START_TRANSFER <= '0';
					reply_size := 0;
					NEXT_STATE <= IDLE;
				end if;
			end if;
			
			when IDLE =>
			ERROR_CODE <= x"00";
			ACT_CMD <= to_unsigned(1,4);
			NACK_SEND <= '0';
			PWM_RESET <= '1';
			PACKET_READ <= '0';
			HW_READY := '0';
			SW_READY := '0';
			ACK_READY := '0';
			SD_READY := '0';
			START_TRANSFER <= '0';
			reply_size := 0;
			header_send_complete <= '0';
			payload_send_complete <= '0';
			START_CRC_HEADER_SEND <= '0';
			START_CRC_PAYLOAD_SEND <= '0';
			if(PACKET_AVAILABLE = '1') then
				NEXT_STATE <= CHECK_DATA;
			else
				NEXT_STATE <= REPLY_SD;
			end if;

			when CHECK_DATA =>
			ACT_CMD <= to_unsigned(2,4);
				if(CMD_CODE = x"01") then	
					NEXT_STATE <= CMD_STS;
				elsif(CMD_CODE = x"02") then
					NEXT_STATE <= CMD_SCS;
				elsif(CMD_CODE = x"03") then
					NEXT_STATE <= CMD_STB;
				elsif(CMD_CODE = x"04") then
					NEXT_STATE <= REPLY_HWV;
				elsif(CMD_CODE = x"05") then
					NEXT_STATE <= REPLY_SWV;
				elsif(CMD_CODE = x"06") then
					ERROR_CODE <= x"02";
					NEXT_STATE <= NACK;
				elsif(CMD_CODE = x"07") then
					NEXT_STATE <= CMD_SSM;
				else
					ERROR_CODE <= x"03";
					NEXT_STATE <= NACK;
			end if;	
			
			when WAIT_PC_REC =>
			ACT_CMD <= to_unsigned(3,4);
			PWM_RESET <= '1';
			PACKET_READ <= '0';
			HW_READY := '0';
			SW_READY := '0';
			ACK_READY := '0';
			SD_READY := '0';
			START_TRANSFER <= '0';
			reply_size := 0;
			START_CRC_HEADER_SEND <= '0';
			START_CRC_PAYLOAD_SEND <= '0';
			if(PACKET_AVAILABLE = '1') then
					WAIT_PC_BUFFER := 0;
				NEXT_STATE <= IDLE;
			elsif((SAMPLING_MODE_S = '0' and WAIT_PC_BUFFER /= 5000000) or 
			(SAMPLING_MODE_S = '1' and (ACC_ROUNDS_S = 8 or ACC_ROUNDS_S = 20) and  WAIT_PC_BUFFER /= 180000) or 
			(SAMPLING_MODE_S = '1' and (ACC_ROUNDS_S /= 8 and ACC_ROUNDS_S /= 20) and  WAIT_PC_BUFFER /= 600000)) then
			-- prevent PC high CPU & optimizations V1.2.5: RTS = 5115500, ETS 5-10MSa/s = 600300, ETS >10MSa/s = 180000 --> RC
			-- prevent Linux overflow & optimizations V1.2.6: RTS = 5615500, ETS 5-10MSa/s = 600300, ETS >10MSa/s = 180000 --> RC2
			-- prevent Linux overflow & optimizations v1.2.7: RTS = 6615500, ETS 5-10MSa/s = 600300, ETS >10MSa/s = 180000 --> RC3
			-- prevent Linux overflow & optimizations v1.2.8: RTS = 6500000, ETS 5-10MSa/s = 711000, ETS >10MSa/s = 310000 --> RC4
			-- more speed & optimizations v1.2.9: RTS = 5000000, ETS 5-10MSa/s = 600000, ETS >10MSa/s = 180000 --> RC5
			WAIT_PC_BUFFER := WAIT_PC_BUFFER + 1;
			else
			WAIT_PC_BUFFER := 0;
			NEXT_STATE <= REPLY_SD;
			end if;
			
		when CMD_STS =>
			ACT_CMD <= to_unsigned(5,4);
			TRIGGER_CHAN <= CMD_CHAN;
			if(PAYLOAD_DATA(0)(4 downto 0) = "00011") then
				TRIGGER_TYPE <= to_unsigned(3,2);
			elsif(PAYLOAD_DATA(0)(4 downto 0) = "00000") then
				TRIGGER_TYPE <= to_unsigned(0,2); 
				PWM_DUTY_S <= PAYLOAD_DATA(2)(7 downto 0);
				PWM_ENA <= '1';
			elsif(PAYLOAD_DATA(0)(4 downto 0) = "00001") then
				TRIGGER_TYPE <= to_unsigned(1,2);
				PWM_DUTY_S <= PAYLOAD_DATA(2)(7 downto 0);
				PWM_ENA <= '1';
			elsif(PAYLOAD_DATA(0)(4 downto 0) = "00010") then
				TRIGGER_TYPE <= to_unsigned(2,2);
				PWM_DUTY_S <= PAYLOAD_DATA(2)(7 downto 0);
				PWM_ENA <= '1';
			end if;
			
			if(PAYLOAD_DATA(0)(7 downto 5) = "000") then
				TRIGGER_MODE <= '0';
			elsif(PAYLOAD_DATA(0)(7 downto 5) = "001") then
				TRIGGER_MODE <= '1';
			end if;
			
			NEXT_STATE <= ACK;
	
		when CMD_SCS =>
			ACT_CMD <= to_unsigned(6,4);
			START_TRANSFER <= '0';
			if(CMD_CHAN = x"00" and (PAYLOAD_DATA(1) = x"00")) then
			CHAN_1_ON_S <= '0';
			AMP1_SEL_SIG(2 downto 0) <= PAYLOAD_DATA(0)(2 downto 0);
			STATUS_LED(7 downto 5) <= PAYLOAD_DATA(0)(2 downto 0);
			end if;
			if(CMD_CHAN = x"00" and (PAYLOAD_DATA(1) = x"01" or PAYLOAD_DATA(1) = x"02")) then
			CHAN_1_ON_S <= '1';
			AMP1_SEL_SIG(2 downto 0) <= PAYLOAD_DATA(0)(2 downto 0);
			STATUS_LED(7 downto 5) <= PAYLOAD_DATA(0)(2 downto 0);
			end if;
			if(CMD_CHAN = x"01" and (PAYLOAD_DATA(1) = x"00")) then
			CHAN_2_ON_S <= '0';
			AMP2_SEL_SIG(2 downto 0) <= PAYLOAD_DATA(0)(2 downto 0);
			end if;
			if(CMD_CHAN = x"01" and (PAYLOAD_DATA(1) = x"01" or PAYLOAD_DATA(1) = x"02")) then
			CHAN_2_ON_S <= '1';
			AMP2_SEL_SIG(2 downto 0) <= PAYLOAD_DATA(0)(2 downto 0);
			end if;
			
			NEXT_STATE <= ACK;
	
		when CMD_STB =>
			ACT_CMD <= to_unsigned(7,4);
			START_TRANSFER <= '0';
			SAMPLERATE(7 downto 0) <= PAYLOAD_DATA(3);
			SAMPLERATE(15 downto 8) <= PAYLOAD_DATA(2);
			SAMPLERATE(23 downto 16) <= PAYLOAD_DATA(1);
			SAMPLERATE(31 downto 24) <= PAYLOAD_DATA(0);
			samplerate_cnter <= unsigned(SAMPLERATE);
			NEXT_STATE <= ACK;
		
		when CMD_SSM =>
			ACT_CMD <= to_unsigned(8,4);
			START_TRANSFER <= '0';
			
			PAYLOAD_LOGIC_VECTOR_1(15 downto 8) := PAYLOAD_DATA(1);
			PAYLOAD_LOGIC_VECTOR_1(7 downto 0) := PAYLOAD_DATA(2);
			PAYLOAD_LOGIC_VECTOR_2(15 downto 8) := PAYLOAD_DATA(3);
			PAYLOAD_LOGIC_VECTOR_2(7 downto 0) := PAYLOAD_DATA(4);
			PAYLOAD_LOGIC_VECTOR_2(23 downto 16) := x"00";
			PAYLOAD_LOGIC_VECTOR_2(31 downto 24) := x"00";
			
			ACC_ROUNDS_S <= unsigned(PAYLOAD_LOGIC_VECTOR_2);
			
			if(PAYLOAD_LOGIC_VECTOR_1 = "0000000000000000") then
			ERROR_CODE <= x"03";
			NEXT_STATE <= NACK;
			else
			SAMPLES_PER_PACKET_S <= unsigned(PAYLOAD_LOGIC_VECTOR_1);
			NEXT_STATE <= ACK;
			end if;
			
			if(PAYLOAD_DATA(0) = x"01" or PAYLOAD_DATA(0) = x"02") then
				SAMPLING_MODE_S <= '1';
				samplerate_cnter <= to_unsigned(48,32);
			else
				SAMPLING_MODE_S <= '0';
				SAMPLES_PER_PACKET_S <= to_unsigned(512,16);
			end if;
		
	when REPLY_SD =>	
		ACT_CMD <= to_unsigned(9,4);
		if(ETS_PACKET_SEND_S = '1') then
		ETS_PACKET_SEND_S <= '0';
		end if;
		
		if((SAMPLE_INDEX = SAMPLES_PER_PACKET_WH) and CRC_header_send_COMPLETE = '1') then
					GET_SEND_DATA := '1';
					PACKET_BUILD_STATUS := '0';
		end if;
	
		if(PACKET_SEND_NUMBER_CNT = 0 and SAMPLING_MODE_S = '1') then
					PACKET_SEND_NUMBER_CNT := to_unsigned(1,32); 
				elsif(SAMPLING_MODE_S = '0') then
					PACKET_SEND_NUMBER_CNT := to_unsigned(0,32); 
				end if;
		PACKET_SEND_NUMBER := std_logic_vector(PACKET_SEND_NUMBER_CNT);		
		PAYLOAD_LOGIC_VECTOR_1 := std_logic_vector(SAMPLES_PER_PACKET_S);
		PAYLOAD_FIXDATA_SEND(0) := PACKET_SEND_NUMBER(31 downto 24);
		PAYLOAD_FIXDATA_SEND(1) := PACKET_SEND_NUMBER(23 downto 16);
		PAYLOAD_FIXDATA_SEND(2) := PACKET_SEND_NUMBER(15 downto 8);
		PAYLOAD_FIXDATA_SEND(3) := PACKET_SEND_NUMBER(7 downto 0);
		
		if(CHAN_1_ON_S = '1' and CHAN_2_ON_S = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '0') then
				PAYLOAD_FIXDATA_SEND(4) := FREQUENCY_CH1_PORT(31 downto 24);
				PAYLOAD_FIXDATA_SEND(5) := FREQUENCY_CH1_PORT(23 downto 16);
				PAYLOAD_FIXDATA_SEND(6) := FREQUENCY_CH1_PORT(15 downto 8);
				PAYLOAD_FIXDATA_SEND(7) := FREQUENCY_CH1_PORT(7 downto 0);
		elsif(CHAN_1_ON_S = '0' and CHAN_2_ON_S = '1') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '1') then
				PAYLOAD_FIXDATA_SEND(4) := FREQUENCY_CH2_PORT(31 downto 24);
				PAYLOAD_FIXDATA_SEND(5) := FREQUENCY_CH2_PORT(23 downto 16);
				PAYLOAD_FIXDATA_SEND(6) := FREQUENCY_CH2_PORT(15 downto 8);
				PAYLOAD_FIXDATA_SEND(7) := FREQUENCY_CH2_PORT(7 downto 0);
		end if;
		
		PAYLOAD_FIXDATA_SEND(8) := x"08";
		PAYLOAD_FIXDATA_SEND(9) := PAYLOAD_LOGIC_VECTOR_1(15 downto 8);
		PAYLOAD_FIXDATA_SEND(10):= PAYLOAD_LOGIC_VECTOR_1(7 downto 0); 
		
	if(PACKET_AVAILABLE = '1' and BYTE_COUNT = reply_size) then
				ETS_PACKET_SEND_S <= '1';
				PACKET_SEND_NUMBER_CNT := to_unsigned(0,32);
				SD_OK := '0';
				PACKET_BUILD_STATUS := '1';
				FIXED_PAYLOAD_CNT := 0;
				START_TRANSFER <= '0';
				GET_SEND_DATA := '0';
				reply_size := 0;
				PACKET_READ <= '1';
				START_CRC_HEADER_SEND <= '0';
				START_CRC_PAYLOAD_SEND <= '0';
				SD_READY := '1';
				payload_send_complete <= '0';
				header_send_complete <= '0';
				SAMPLE_INDEX := 18;
				TRIGGER_FOUND_PACKET := '0';
				FIFO_CH1_RD_S <= '0';
				FIFO_CH2_RD_S <= '0';
		
		
		elsif(CHAN_1_ON_S = '0' and CHAN_2_ON_S = '0' and PACKET_AVAILABLE = '0') then
			PACKET_SEND_NUMBER_CNT := to_unsigned(0,32);
			SD_OK := '0';
			header_send_complete <= '0';
			payload_send_complete <= '0';
			START_CRC_HEADER_SEND <= '0';
			START_CRC_PAYLOAD_SEND <= '0';
			FIXED_PAYLOAD_CNT := 0;
			STATUS_LED(9) <= '0';
			STATUS_LED(8) <= '0';
			reply_size := 0;
			START_TRANSFER <= '0';
			DUAL_CHANNEL_MODE_ON_S <= '0';
			DUAL_CHANNEL_MODE_STATUS_S <= '0';
			TRIGGER_FOUND_PACKET := '0';
			FIFO_CH1_RD_S <= '0';
			FIFO_CH2_RD_S <= '0';
			GET_SEND_DATA := '0';
			SAMPLE_INDEX := 18;
			NEXT_STATE <= IDLE;	
		else		
			if(CHAN_1_ON_S = '1' and CHAN_2_ON_S = '1' and PACKET_AVAILABLE = '0') then
				DUAL_CHANNEL_MODE_ON_S <= '1';
			else
				DUAL_CHANNEL_MODE_ON_S <= '0';
			end if;
					packet_send_buffer(0) <= x"0B";
					packet_send_buffer(1) <= x"01";
					PAYLOAD_LOGIC_VECTOR_1 := std_logic_vector(SAMPLES_PER_PACKET_S+11);
					packet_send_buffer(3) <= PAYLOAD_LOGIC_VECTOR_1(15 downto 8);
					packet_send_buffer(4) <= PAYLOAD_LOGIC_VECTOR_1(7 downto 0);
					
					if((CHAN_1_ON_S = '1' and CHAN_2_ON_S = '0' and PACKET_AVAILABLE = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '0' and PACKET_AVAILABLE = '0')) then
					STATUS_LED(9) <= '1';
					STATUS_LED(8) <= '0';
					if(DUAL_CHANNEL_MODE_READY = '1') then
					DUAL_CHANNEL_MODE_STATUS_S <= '1';
					DUAL_CHANNEL_MODE_READY <= '0';
					end if;
					packet_send_buffer(2) <= x"00";
					START_CRC_HEADER_SEND <= '1';
					header_send_complete <= '1';
					elsif((CHAN_1_ON_S = '0' and CHAN_2_ON_S = '1' and PACKET_AVAILABLE = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '1' and PACKET_AVAILABLE = '0')) then
					STATUS_LED(8) <= '1';
					STATUS_LED(9) <= '0';
					if(DUAL_CHANNEL_MODE_READY = '1') then
					DUAL_CHANNEL_MODE_STATUS_S <= '0';
					DUAL_CHANNEL_MODE_READY <= '0';
					end if;
					packet_send_buffer(2) <= x"01";
					START_CRC_HEADER_SEND <= '1';
					header_send_complete <= '1';
					end if;
					payload_send_size(7 downto 0) <= packet_send_buffer(4);
					payload_send_size(15 downto 8) <= packet_send_buffer(3);
				
				if(CHAN_1_ON_S = '1' and CHAN_2_ON_S = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '0') then
					FIFO_EMPTY := FIFO_CH1_EMPTY;
					
					FIFO_DATAOUT := FIFO_CH1_DATAOUT;
				elsif(CHAN_1_ON_S = '0' and CHAN_2_ON_S = '1') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '1') then
					FIFO_EMPTY := FIFO_CH2_EMPTY;
				
					FIFO_DATAOUT := FIFO_CH2_DATAOUT;
				end if;
				
				if(CRC_header_send_COMPLETE = '1' and FIXED_PAYLOAD_CNT /= 11) then
					PAYLOAD_DATA_SEND(0) <= PAYLOAD_FIXDATA_SEND(FIXED_PAYLOAD_CNT);
					packet_send_buffer(FIXED_PAYLOAD_CNT + 7) <= PAYLOAD_FIXDATA_SEND(FIXED_PAYLOAD_CNT);
					START_CRC_PAYLOAD_SEND <= '1';
					payload_send_complete <= '1';
					FIXED_PAYLOAD_CNT := FIXED_PAYLOAD_CNT + 1;
				elsif(FIXED_PAYLOAD_CNT = 11) then
				
				
				if(SAMPLE_DATA_RESET_1 = '1' or SAMPLE_DATA_RESET_2 = '1') then
				PACKET_BUILD_STATUS := '1';
				TRIGGER_FOUND_PACKET := '0';
				FIFO_CH1_RD_S <= '0';
				FIFO_CH2_RD_S <= '0';
				GET_SEND_DATA := '0';
				START_CRC_PAYLOAD_SEND <= '0';
				payload_send_complete <= '0';
				SAMPLE_INDEX := 18;
				
				elsif(START_CRC_HEADER_SEND = '1' and PACKET_BUILD_STATUS = '1' and SAMPLING_MODE_S = '0') then
					
					if(FIFO_EMPTY = '0') then
						if(CHAN_1_ON_S = '1' and CHAN_2_ON_S = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '0') then
							FIFO_CH1_RD_S <= '1';
						elsif(CHAN_1_ON_S = '0' and CHAN_2_ON_S = '1') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '1') then
							FIFO_CH2_RD_S <= '1';
						end if;
						START_CRC_PAYLOAD_SEND <= '0';
						if(FIFO_CH1_RD_S = '1' or FIFO_CH2_RD_S = '1') then
								FIFO_CH1_RD_S <= '0';
								FIFO_CH2_RD_S <= '0';
							if(FIFO_DATAOUT(8) = '1' and TRIGGER_FOUND_PACKET = '0' and (PWM_DUTY_S /= x"FF" and PWM_DUTY_S /= x"3E")) then
								FIXED_PAYLOAD_CNT := 0;
								TRIGGER_FOUND_PACKET := '1';
								FIFO_CH1_RD_S <= '0';
								FIFO_CH2_RD_S <= '0';
								GET_SEND_DATA := '0';
								START_CRC_PAYLOAD_SEND <= '0';
								payload_send_complete <= '0';
								SAMPLE_INDEX := 18;
							elsif(TRIGGER_MODE = '0' or (TRIGGER_MODE = '1' and TRIGGER_FOUND_PACKET = '1' and (PWM_DUTY_S /= x"FF" and PWM_DUTY_S /= x"3E"))) then
							packet_send_buffer(SAMPLE_INDEX) <= FIFO_DATAOUT(7 downto 0);
							PAYLOAD_DATA_SEND(0) <= FIFO_DATAOUT(7 downto 0);
							START_CRC_PAYLOAD_SEND <= '1';
							payload_send_complete <= '1';
							SAMPLE_INDEX := SAMPLE_INDEX + 1;	
							end if;
						end if;
					elsif(FIFO_EMPTY = '1') then
					START_CRC_PAYLOAD_SEND <= '0';
					end if;
				
				elsif(START_CRC_HEADER_SEND = '1' and PACKET_BUILD_STATUS = '1' and SAMPLING_MODE_S = '1' ) then
						if(FIFO_EMPTY = '0') then
							if(CHAN_1_ON_S = '1' and CHAN_2_ON_S = '0') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '0' and ETS_PACKET_READY_1 = '1') then
								FIFO_CH1_RD_S <= '1';
							elsif(CHAN_1_ON_S = '0' and CHAN_2_ON_S = '1') or (DUAL_CHANNEL_MODE_ON_S = '1' and DUAL_CHANNEL_MODE_STATUS_S = '1' and ETS_PACKET_READY_2 = '1') then
								FIFO_CH2_RD_S <= '1';
							end if;
							START_CRC_PAYLOAD_SEND <= '0';
							if(FIFO_CH1_RD_S = '1' or FIFO_CH2_RD_S = '1') then
								FIFO_CH1_RD_S <= '0';
								FIFO_CH2_RD_S <= '0';
								packet_send_buffer(SAMPLE_INDEX) <= FIFO_DATAOUT(7 downto 0);
								PAYLOAD_DATA_SEND(0) <= FIFO_DATAOUT(7 downto 0);
								START_CRC_PAYLOAD_SEND <= '1';
								payload_send_complete <= '1';
								SAMPLE_INDEX := SAMPLE_INDEX + 1;	
							end if;	
						elsif(FIFO_EMPTY = '1') then
								START_CRC_PAYLOAD_SEND <= '0';
						end if;
				end if;
			end if;

				if(GET_SEND_DATA = '1' and CRC_payload_send_COMPLETE = '1' and SD_OK = '0' and START_TRANSFER = '0') then	
				packet_send_buffer(5) <= TMP_CRC_HEAD_SEND(15 downto 8);
				packet_send_buffer(6) <= TMP_CRC_HEAD_SEND(7 downto 0);
				packet_send_buffer(to_integer(SAMPLES_PER_PACKET_WH)) <= TMP_CRC_PAYLOAD_SEND(15 downto 8);
				packet_send_buffer(to_integer(SAMPLES_PER_PACKET_WH+1)) <= TMP_CRC_PAYLOAD_SEND(7 downto 0);
				reply_size := to_integer(SAMPLES_PER_PACKET_WH+2);
				START_TRANSFER <= '1';
				SD_OK := '1';
			end if;
			
			if (BYTE_COUNT = reply_size and SD_OK = '1' and PACKET_AVAILABLE = '0') then
					START_TRANSFER <= '0';
					SD_OK := '0';
					SD_READY := '1';
					
			end if;

		end if;	
			if(SD_READY = '1') then
					if((PACKET_SEND_NUMBER_CNT < ACC_ROUNDS_S) and SAMPLING_MODE_S = '1') then
					PACKET_SEND_NUMBER_CNT := PACKET_SEND_NUMBER_CNT + 1;
					elsif (SAMPLING_MODE_S = '1') then
					PACKET_SEND_NUMBER_CNT := to_unsigned(0,32);
					ETS_PACKET_SEND_S <= '1';
					DUAL_CHANNEL_MODE_READY <= '1';
					elsif (SAMPLING_MODE_S = '0') then
					DUAL_CHANNEL_MODE_READY <= '1';
					end if;
					SD_READY := '0';
					FIXED_PAYLOAD_CNT := 0;
					PACKET_BUILD_STATUS := '1';
					TRIGGER_FOUND_PACKET := '0';
					FIFO_CH1_RD_S <= '0';
					FIFO_CH2_RD_S <= '0';
					GET_SEND_DATA := '0';
					START_CRC_PAYLOAD_SEND <= '0';
					START_CRC_HEADER_SEND <= '0';
					SAMPLE_INDEX := 18;
					payload_send_complete <= '0';
					header_send_complete <= '0';
					START_CRC_PAYLOAD_SEND <= '0';
					if(PACKET_AVAILABLE = '1') then
					NEXT_STATE <= CHECK_DATA;
					else
					NEXT_STATE <= WAIT_PC_REC;
					end if;
			end if;
	
			when REPLY_HWV =>
			CHAN_1_ON_S <= '0';
			CHAN_2_ON_S <= '0';
			ACT_CMD <= to_unsigned(10,4);
			reply_size := 13;
			START_TRANSFER <= '0';				
			if(HW_READY = '0' and HW_OK = '0' and START_TRANSFER = '0') then
			
				if(START_CRC_PAYLOAD_SEND = '0') then	
					packet_send_buffer(0) <= x"09";
					packet_send_buffer(1) <= x"00";
					packet_send_buffer(2) <= x"00";
					packet_send_buffer(3) <= x"00";
					packet_send_buffer(4) <= x"04";
					payload_send_size(7 downto 0) <= packet_send_buffer(4);
					payload_send_size(15 downto 8) <= packet_send_buffer(3);
					packet_send_buffer(5) <= x"9E";
					packet_send_buffer(6) <= x"95";
					packet_send_buffer(7) <= x"01";
					packet_send_buffer(8) <= x"00";
					packet_send_buffer(9) <= x"02";
					packet_send_buffer(10) <= x"00";
					PAYLOAD_DATA_SEND(0) <= packet_send_buffer(7);
					PAYLOAD_DATA_SEND(1) <= packet_send_buffer(8);
					PAYLOAD_DATA_SEND(2) <= packet_send_buffer(9);
					PAYLOAD_DATA_SEND(3) <= packet_send_buffer(10);
					START_CRC_PAYLOAD_SEND <= '1';
					payload_send_complete <= '1';
				end if;
				
				if(CRC_payload_send_COMPLETE = '1' and HW_OK = '0' and START_CRC_PAYLOAD_SEND = '1') then
			
					START_CRC_PAYLOAD_SEND <= '0';
					packet_send_buffer(11) <= TMP_CRC_PAYLOAD_SEND(15 downto 8);
					packet_send_buffer(12) <= TMP_CRC_PAYLOAD_SEND(7 downto 0);
					payload_send_complete <= '0';
					
					START_TRANSFER <= '1';
					HW_OK := '1';
				end if;
			end if;
			if (BYTE_COUNT = reply_size and HW_OK = '1') then
					START_TRANSFER <= '0';
					HW_OK := '0';
					HW_READY := '1';
			end if;
			if(HW_READY = '1') then
					START_CRC_PAYLOAD_SEND <= '0';
					START_CRC_HEADER_SEND <= '0';
					PACKET_READ <= '1';
					NEXT_STATE <= IDLE;
			end if;
			
			when REPLY_SWV =>
			CHAN_1_ON_S <= '0';
			CHAN_2_ON_S <= '0';
			ACT_CMD <= to_unsigned(11,4);
			reply_size := 13;
			START_TRANSFER <= '0';				
			if(SW_READY = '0' and SW_OK = '0' and START_TRANSFER = '0') then
	
				if(START_CRC_HEADER_SEND = '0' and START_CRC_PAYLOAD_SEND = '0') then
					
					packet_send_buffer(0) <= x"0A";
					packet_send_buffer(1) <= x"00";
					packet_send_buffer(2) <= x"00";
					packet_send_buffer(3) <= x"00";
					packet_send_buffer(4) <= x"04";
					payload_send_size(7 downto 0) <= packet_send_buffer(4);
					payload_send_size(15 downto 8) <= packet_send_buffer(3);
					packet_send_buffer(5) <= x"C7";
					packet_send_buffer(6) <= x"C5";
					packet_send_buffer(7) <= x"01";
					packet_send_buffer(8) <= x"02";
					packet_send_buffer(9) <= x"09";
					packet_send_buffer(10) <= x"00";
					PAYLOAD_DATA_SEND(0) <= packet_send_buffer(7);
					PAYLOAD_DATA_SEND(1) <= packet_send_buffer(8);
					PAYLOAD_DATA_SEND(2) <= packet_send_buffer(9);
					PAYLOAD_DATA_SEND(3) <= packet_send_buffer(10);
					START_CRC_PAYLOAD_SEND <= '1';
					payload_send_complete <= '1';
				end if;
				
				if(CRC_payload_send_COMPLETE = '1' and SW_OK = '0' and START_CRC_PAYLOAD_SEND = '1') then
				
					START_CRC_PAYLOAD_SEND <= '0';
					packet_send_buffer(11) <= TMP_CRC_PAYLOAD_SEND(15 downto 8);
					packet_send_buffer(12) <= TMP_CRC_PAYLOAD_SEND(7 downto 0);
					payload_send_complete <= '0';
					
					START_TRANSFER <= '1';
					SW_OK := '1';
				end if;
			end if;
			if (BYTE_COUNT = reply_size and SW_OK = '1') then
					START_TRANSFER <= '0';
					SW_OK := '0';
					SW_READY := '1';
			end if;
			if(SW_READY = '1') then
					START_CRC_PAYLOAD_SEND <= '0';
					START_CRC_HEADER_SEND <= '0';
					PACKET_READ <= '1';
					NEXT_STATE <= IDLE;
			end if;
			
			
			
			when ACK =>
			ACT_CMD <= to_unsigned(12,4);
			PWM_ENA <= '0';
			reply_size := 7;
			START_TRANSFER <= '0';
			header_send_complete <= '0';
			payload_send_complete <= '0';
			START_CRC_HEADER_SEND <= '0';
			START_CRC_PAYLOAD_SEND <= '0';		
			if(ACK_READY = '0' and ACK_OK = '0' and START_TRANSFER = '0') then
					
					packet_send_buffer(0) <= x"7F";
					packet_send_buffer(1) <= x"00";
					packet_send_buffer(2) <= CMD_CHAN;
					packet_send_buffer(3) <= x"00";
					packet_send_buffer(4) <= x"00";

					packet_send_buffer(7) <= x"7F";
					packet_send_buffer(8) <= x"00";
					packet_send_buffer(9) <= CMD_CHAN;
					packet_send_buffer(10) <= x"00";
					packet_send_buffer(11) <= x"00";
					
					packet_send_buffer(14) <= x"7F";
					packet_send_buffer(15) <= x"00";
					packet_send_buffer(16) <= CMD_CHAN;
					packet_send_buffer(17) <= x"00";
					packet_send_buffer(18) <= x"00";

					if(CMD_CHAN = x"00") then
					packet_send_buffer(5)  <= x"F4";
					packet_send_buffer(12) <= x"F4";
					packet_send_buffer(6)  <= x"39";
					packet_send_buffer(13) <= x"39";
					packet_send_buffer(19) <= x"F4";
					packet_send_buffer(20) <= x"39";
					end if;
					if(CMD_CHAN = x"01") then
					packet_send_buffer(5)  <= x"E4";
					packet_send_buffer(12) <= x"E4";
					packet_send_buffer(6)  <= x"18";
					packet_send_buffer(13) <= x"18";
					packet_send_buffer(19) <= x"E4";
					packet_send_buffer(20) <= x"18";
					end if;
				
				if(ACK_OK = '0') then
				
					
					START_TRANSFER <= '1';
					ACK_OK := '1';
				end if;
			end if;
			if (BYTE_COUNT = reply_size and ACK_OK = '1') then
					START_TRANSFER <= '0';
					ACK_OK := '0';
					ACK_READY := '1';
			end if;
			if(ACK_READY = '1') then
						PACKET_READ <= '1';
						NEXT_STATE <= IDLE;
			end if;

			
		when NACK =>
	ACT_CMD <= to_unsigned(13,4);	
			PWM_ENA <= '0';
			reply_size := 10;
			START_TRANSFER <= '0';
			if(ACK_READY = '0' and ACK_OK = '0' and START_TRANSFER = '0') then
			if(HEADER_CRC_NOK = '1' or PAYLOAD_CRC_NOK = '1') then
					packet_send_buffer(7) <= x"04";
			else
				packet_send_buffer(7) <= ERROR_CODE;
			end if;
					packet_send_buffer(0) <= x"7E";
					packet_send_buffer(1) <= x"01";
					packet_send_buffer(2) <= CMD_CHAN;
					packet_send_buffer(3) <= x"00";
					packet_send_buffer(4) <= x"01";
					payload_send_size(7 downto 0) <= packet_send_buffer(4);
					payload_send_size(15 downto 8) <= packet_send_buffer(3);
					START_CRC_HEADER_SEND <= '1';
					header_send_complete <= '1';
				if(CRC_header_send_COMPLETE = '1' and CRC_payload_send_COMPLETE = '0') then
					packet_send_buffer(5) <= TMP_CRC_HEAD_SEND(15 downto 8);
					packet_send_buffer(6) <= TMP_CRC_HEAD_SEND(7 downto 0);
					PAYLOAD_DATA_SEND(0) <= packet_send_buffer(7);
					START_CRC_PAYLOAD_SEND <= '1';
					payload_send_complete <= '1';
					
				elsif(CRC_payload_send_COMPLETE = '1' and ACK_OK = '0') then
					packet_send_buffer(8) <= TMP_CRC_PAYLOAD_SEND(15 downto 8);
					packet_send_buffer(9) <= TMP_CRC_PAYLOAD_SEND(7 downto 0);
					
					START_TRANSFER <= '1';
					ACK_OK := '1';
				end if;
			end if;
			if (BYTE_COUNT = reply_size and ACK_OK = '1') then
					START_TRANSFER <= '0';
					ACK_OK := '0';
					ACK_READY := '1';
			end if;
			if(ACK_READY = '1') then
					NACK_SEND <= '1';
					HW_READY := '0';
					SW_READY := '0';
					ACK_READY := '0';
					SD_READY := '0';
					START_TRANSFER <= '0';
					reply_size := 0;
					START_CRC_HEADER_SEND <= '0';
					START_CRC_PAYLOAD_SEND <= '0';
					header_send_complete <= '0';
					payload_send_complete <= '0';
					NEXT_STATE <= IDLE;
			end if;
		end case;
		end if;
    end process;
	 
	process(CLOCK_120)
	  variable V: std_logic_vector(15 downto 0) := x"0080";
	  variable V_SEND: std_logic_vector(15 downto 0) := x"0080";
	  variable XOR_FLAG: std_logic := '0';
	  variable XOR_FLAG_SEND: std_logic := '0';
	  variable HEADER_INDEX: integer := 0;
	  variable HEADER_INDEX_SEND: integer := 0;
	  variable CRC_HEAD : std_logic_vector(15 downto 0);
	  variable CRC_HEAD_SEND : std_logic_vector(15 downto 0);
	  
		begin
		if(CLOCK_120'event and CLOCK_120 = '1') then
		if(START_CRC_HEADER = '1') then
		
		if(HEADER_INDEX < 5 and CRC_header_calc_COMPLETE = '0') then
			
			V := x"0080";
			for i in 0 to 7 loop
				if ((CRC_HEAD and x"8000") > 0) then
					XOR_FLAG := '1';
				else
					XOR_FLAG := '0';
				end if;
				CRC_HEAD := std_logic_vector(shift_left(unsigned(CRC_HEAD), 1));
				
				if((HEADER_DATA(HEADER_INDEX) and V) > 0) then
					CRC_HEAD := CRC_HEAD + '1';
				end if;
				
				if(XOR_FLAG = '1') then
					CRC_HEAD := CRC_HEAD xor CRC_POLY;
				end if;
				
				V := std_logic_vector(shift_right(unsigned(V), 1));
			end loop;
		end if;
				
		if(HEADER_INDEX = 5 and CRC_header_calc_COMPLETE = '0') then
						HEADER_INDEX := 0;
						
						TMP_CRC_HEAD(15 downto 0) <= CRC_HEAD(15 downto 0);
						CRC_HEAD := x"0000";
					
						CRC_header_calc_COMPLETE <= '1';
					elsif(START_CRC_HEADER = '1' and HEADER_INDEX < 5 and CRC_header_calc_COMPLETE = '0') then
						HEADER_INDEX := HEADER_INDEX + 1;
					end if;
		end if;
		
		if(START_CRC_HEADER_SEND = '1') then
		
		if(HEADER_INDEX_SEND < 5 and CRC_header_send_COMPLETE = '0') then
			
			V_SEND := x"0080";
			for i in 0 to 7 loop
				if ((CRC_HEAD_SEND and x"8000") > 0) then
					XOR_FLAG_SEND := '1';
				else
					XOR_FLAG_SEND := '0';
				end if;
				CRC_HEAD_SEND := std_logic_vector(shift_left(unsigned(CRC_HEAD_SEND), 1));
				
				if((HEADER_DATA_SEND(HEADER_INDEX_SEND) and V_SEND) > 0) then
					CRC_HEAD_SEND := CRC_HEAD_SEND + '1';
				end if;
				
				if(XOR_FLAG_SEND = '1') then
					CRC_HEAD_SEND := CRC_HEAD_SEND xor CRC_POLY;
				end if;
				
				V_SEND := std_logic_vector(shift_right(unsigned(V_SEND), 1));
			end loop;
		end if;
		
		if(HEADER_INDEX_SEND = 5 and CRC_header_send_COMPLETE = '0') then
						HEADER_INDEX_SEND := 0;
						
						TMP_CRC_HEAD_SEND(15 downto 0) <= CRC_HEAD_SEND(15 downto 0);
						CRC_HEAD_SEND := x"0000";
					
						CRC_header_send_COMPLETE <= '1';
					elsif(START_CRC_HEADER_SEND = '1' and HEADER_INDEX_SEND < 5 and CRC_header_send_COMPLETE = '0') then
						HEADER_INDEX_SEND := HEADER_INDEX_SEND + 1;
					end if;
		end if;
	end if;
		
		if(header_complete = '0') then
			CRC_header_calc_COMPLETE <= '0';
			HEADER_INDEX := 0;
		end if;
		
		if(header_send_complete = '0') then
			CRC_header_send_COMPLETE <= '0';
			HEADER_INDEX_SEND := 0;
		end if;
		
	end process;
		
		
	process(CLOCK_120)
	  variable V_PAY: std_logic_vector(15 downto 0) := x"0080";
	  variable V_PAY_SEND: std_logic_vector(15 downto 0) := x"0080";
	  variable XOR_FLAG_PAY: std_logic := '0';
	  variable XOR_FLAG_PAY_SEND: std_logic := '0';
	  variable PAYLOAD_INDEX: integer := 0;
	  variable PAYLOAD_INDEX_SEND: integer := 0;
	  variable CRC_PAYLOAD : std_logic_vector(15 downto 0);
	  variable CRC_PAYLOAD_SEND : std_logic_vector(15 downto 0); 
	  
		begin
		if(CLOCK_120'event and CLOCK_120 = '1') then
		if(START_CRC_PAYLOAD = '1') then
		
		if(PAYLOAD_INDEX < payload_rec_size and CRC_payload_calc_COMPLETE = '0') then
			
			V_PAY := x"0080";
			for i in 0 to 7 loop
				if ((CRC_PAYLOAD and x"8000") > 0) then
					XOR_FLAG_PAY := '1';
				else
					XOR_FLAG_PAY := '0';
				end if;
				CRC_PAYLOAD := std_logic_vector(shift_left(unsigned(CRC_PAYLOAD), 1));
				
				if((PAYLOAD_DATA(PAYLOAD_INDEX) and V_PAY) > 0) then
					CRC_PAYLOAD := CRC_PAYLOAD + '1';
				end if;
				
				if(XOR_FLAG_PAY = '1') then
					CRC_PAYLOAD := CRC_PAYLOAD xor CRC_POLY;
				end if;
				
				V_PAY := std_logic_vector(shift_right(unsigned(V_PAY), 1));
			end loop;
		end if;
		
		if(PAYLOAD_INDEX = payload_rec_size and CRC_payload_calc_COMPLETE = '0') then
						PAYLOAD_INDEX := 0;
						
						TMP_CRC_PAYLOAD(15 downto 0) <= CRC_PAYLOAD(15 downto 0);
						CRC_PAYLOAD := x"0000";
					
						CRC_payload_calc_COMPLETE <= '1';
					elsif(START_CRC_PAYLOAD = '1' and PAYLOAD_INDEX < payload_rec_size and CRC_payload_calc_COMPLETE = '0') then
						PAYLOAD_INDEX := PAYLOAD_INDEX + 1;
					end if;
		end if;
		
		if(START_CRC_PAYLOAD_SEND = '1' and payload_send_complete = '1') then
		
		if(PAYLOAD_INDEX_SEND < payload_send_size and CRC_payload_send_COMPLETE = '0' and payload_send_complete = '1') then
			
			V_PAY_SEND := x"0080";
			for i in 0 to 7 loop
				if ((CRC_PAYLOAD_SEND and x"8000") > 0) then
					XOR_FLAG_PAY_SEND := '1';
				else
					XOR_FLAG_PAY_SEND := '0';
				end if;
				CRC_PAYLOAD_SEND := std_logic_vector(shift_left(unsigned(CRC_PAYLOAD_SEND), 1));
				if(CMD_STATE /= REPLY_SD) then
				if((PAYLOAD_DATA_SEND(PAYLOAD_INDEX_SEND) and V_PAY_SEND) > 0) then
					CRC_PAYLOAD_SEND := CRC_PAYLOAD_SEND + '1';
				end if;
				else
				if((PAYLOAD_DATA_SEND(0) and V_PAY_SEND) > 0) then
					CRC_PAYLOAD_SEND := CRC_PAYLOAD_SEND + '1';
				end if;
				end if;
				if(XOR_FLAG_PAY_SEND = '1') then
					CRC_PAYLOAD_SEND := CRC_PAYLOAD_SEND xor CRC_POLY;
				end if;
				
				V_PAY_SEND := std_logic_vector(shift_right(unsigned(V_PAY_SEND), 1));
			end loop;
		end if;
		
		if(PAYLOAD_INDEX_SEND = payload_send_size and CRC_payload_send_COMPLETE = '0' and payload_send_complete = '1') then
						PAYLOAD_INDEX_SEND := 0;
						
						TMP_CRC_PAYLOAD_SEND(15 downto 0) <= CRC_PAYLOAD_SEND(15 downto 0);
						CRC_PAYLOAD_SEND := x"0000";
						CRC_payload_send_COMPLETE <= '1';
						
					elsif(START_CRC_PAYLOAD_SEND = '1' and PAYLOAD_INDEX_SEND < payload_send_size and CRC_payload_send_COMPLETE = '0' and payload_send_complete = '1') then
						PAYLOAD_INDEX_SEND := PAYLOAD_INDEX_SEND + 1;
					end if;
		end if;
		end if;
		
		if(payload_complete = '0') then
			CRC_payload_calc_COMPLETE <= '0';
			PAYLOAD_INDEX := 0;
		end if;
		
		if(payload_send_complete = '0') then
			CRC_PAYLOAD_SEND := x"0000";
			CRC_payload_send_COMPLETE <= '0';
			PAYLOAD_INDEX_SEND := 0;
		end if;
		
	end process;
		
	process(CLOCK_120,RX_FINISHED,PACKET_READ)
		variable RX_INDEX: INTEGER:=0;
		variable PAY_COUNTER : integer := 0;
	   variable PAY_CRC_POS : std_logic := '0';
		variable CRC16_HEADER_REC : std_logic_vector(15 downto 0);
	   variable CRC16_PAYLOAD_REC : std_logic_vector(15 downto 0);
		
	begin
	if(CLOCK_120'event and CLOCK_120 ='1') then
				
		if(DUMMY_SENT = '1') then

			if(CRC_header_calc_COMPLETE = '1') then
						header_complete <= '0';
			end if;
			
			if(CRC_payload_calc_COMPLETE = '1') then
						payload_complete := '0';
			end if;
			
			if(PACKET_READ = '1' or NACK_SEND = '1') then
					HEADER_CRC_NOK <= '0';
					PAYLOAD_CRC_NOK <= '0';
					header_complete <= '0';
					payload_complete := '0';
					PAYLOAD_SIZE(15 downto 0) <= x"0000";
				   PACKET_AVAILABLE <= '0';
					PAY_COUNTER := 0;
				   PAY_CRC_POS := '0';
					payload_rec_size <= x"0000";
				
			elsif(RX_FINISHED = '1' and PACKET_AVAILABLE = '0' and HEADER_CRC_OK = '0') then
					
					if((RX_DATA = x"01" or RX_DATA = x"02" or RX_DATA = x"03" or RX_DATA = x"04" or RX_DATA = x"05" or RX_DATA = x"06" or RX_DATA = x"07") and RX_INDEX = 0 and header_complete = '0') then
						PAYLOAD_CRC_NOK <= '0';
						HEADER_CRC_NOK <= '0';
						CMD_CODE <= RX_DATA;
						RX_INDEX := 1;
						HEADER_DATA(0) <= RX_DATA;
					elsif(RX_INDEX = 1 and (RX_DATA = x"01" or RX_DATA = x"00")) then
						RX_INDEX := 2;
						HEADER_DATA(1) <= RX_DATA;
					elsif(RX_INDEX = 2 and RX_DATA = x"00") then	
						CMD_CHAN <= x"00";
						RX_INDEX := 3;
						HEADER_DATA(2) <= RX_DATA;
					elsif(RX_INDEX = 2 and RX_DATA = x"01") then
						CMD_CHAN <= x"01";
						RX_INDEX := 3;
						HEADER_DATA(2) <= RX_DATA;
			
					elsif(RX_INDEX = 3) then
						PAYLOAD_SIZE(7 downto 0) <= RX_DATA;
						RX_INDEX := 4;
						HEADER_DATA(3) <= RX_DATA;

					elsif(RX_INDEX = 4) then
						PAYLOAD_SIZE(15 downto 8) <= RX_DATA;
						RX_INDEX := 5;
						HEADER_DATA(4) <= RX_DATA;
			
					elsif(RX_INDEX = 5) then
						CRC16_HEADER_REC(7 downto 0) := RX_DATA;
						RX_INDEX := 6;

					elsif(RX_INDEX = 6) then
						CRC16_HEADER_REC(15 downto 8) := RX_DATA;
						RX_INDEX := 7;
						header_complete <= '1';
					else
						RX_INDEX := 0;
						payload_complete := '0';
						header_complete <= '0';
						START_CRC_PAYLOAD <= '0';
						START_CRC_HEADER <= '0';
					end if;
				end if;
		if(PACKET_AVAILABLE = '0' and HEADER_CRC_OK = '1' and PAYLOAD_SIZE(15 downto 0) = x"0000") then
			RX_INDEX := 0;
			PAYLOAD_CRC_OK <= '0';
			HEADER_CRC_OK <= '0';
			payload_complete := '0';
			PACKET_AVAILABLE <= '1';		
		end if;
		
		if(PACKET_AVAILABLE = '0' and PAYLOAD_CRC_OK = '1') then
		RX_INDEX := 0;
		PAYLOAD_CRC_OK <= '0';
		HEADER_CRC_OK <= '0';
		payload_complete := '0';
		PACKET_AVAILABLE <= '1';
		end if;
		
		payload_rec_size(7 downto 0) <= PAYLOAD_SIZE(15 downto 8);
		payload_rec_size(15 downto 8) <= PAYLOAD_SIZE(7 downto 0);
	
		if(RX_FINISHED = '1' and HEADER_CRC_OK = '1' and PAYLOAD_SIZE(15 downto 0) /= x"0000") then
			
			if(PAY_CRC_POS = '1') then
				PAY_CRC_POS := '0';
				PAY_COUNTER := 0;
				CRC16_PAYLOAD_REC(15 downto 8) := RX_DATA;
				HEADER_CRC_OK <= '0';
				payload_complete := '1';
				START_CRC_PAYLOAD <= '1';
			end if;
			
			if(PAY_COUNTER = payload_rec_size) then
				CRC16_PAYLOAD_REC(7 downto 0) := RX_DATA;
				PAY_CRC_POS := '1';
			elsif((PAY_COUNTER < payload_rec_size) and PAY_CRC_POS = '0' and payload_complete = '0') then
				PAYLOAD_DATA(PAY_COUNTER) <= RX_DATA;
				PAY_COUNTER := PAY_COUNTER + 1;
			end if;		
		end if;
			
		if(CRC_payload_calc_COMPLETE = '1') then
			START_CRC_PAYLOAD <= '0';
			if(TMP_CRC_PAYLOAD(15 downto 8) = CRC16_PAYLOAD_REC(7 downto 0) and TMP_CRC_PAYLOAD(7 downto 0) = CRC16_PAYLOAD_REC(15 downto 8)) then
			STATUS_LED(2) <= '0';
			PAYLOAD_CRC_OK <= '1';
			PAYLOAD_CRC_NOK <= '0';
			else
			STATUS_LED(2) <= '1';
			PAYLOAD_CRC_NOK <= '1';
			PAYLOAD_CRC_OK <= '0';
			end if;
			payload_complete := '0';
		end if;

			if(header_complete = '1') then
			START_CRC_HEADER <= '1';
			end if;
		
		if(CRC_header_calc_COMPLETE = '1') then
			START_CRC_HEADER <= '0';

			if(TMP_CRC_HEAD(15 downto 8) = CRC16_HEADER_REC(7 downto 0) and TMP_CRC_HEAD(7 downto 0) = CRC16_HEADER_REC(15 downto 8)) then
			STATUS_LED(1) <= '0';
			HEADER_CRC_OK <= '1';
			HEADER_CRC_NOK <= '0';
			else
			STATUS_LED(1) <= '1';
			HEADER_CRC_NOK <= '1';
			HEADER_CRC_OK <= '0';
			end if;
			header_complete <= '0';
		end if;	
	end if;	
			
		if(PACKET_AVAILABLE = '0' and PAYLOAD_CRC_NOK = '1') then
			CMD_CODE <= x"00";
			RX_INDEX := 0;
			PAYLOAD_CRC_OK <= '0';
			HEADER_CRC_OK <= '0';
			payload_complete := '0';
			header_complete <= '0';
			START_CRC_HEADER <= '0';
		end if;
		
		if(PACKET_AVAILABLE = '0' and HEADER_CRC_NOK = '1') then
			CMD_CODE <= x"00";
			RX_INDEX := 0;
			PAYLOAD_CRC_OK <= '0';
			HEADER_CRC_OK <= '0';
			payload_complete := '0';
			header_complete <= '0';
			START_CRC_HEADER <= '0';
		end if;	
			
	end if;
end process;

process(CLOCK_120)
        
        begin
            if(CLOCK_120'event and CLOCK_120='1')then
                if(BYTE_COUNT = reply_size) then
						  STATUS_LED(0) <= '0';
                end if;
					 if(START_TRANSFER = '1' and BYTE_COUNT = reply_size) then
					 BYTE_COUNT <= 0;
					 end if;
					 if(BYTE_COUNT > reply_size) then
					 BYTE_COUNT <= 0;
					 end if;
					 If(BYTE_COUNT /= reply_size) then
						  STATUS_LED(0) <= '1';
						  if(TX_BUSY='0') then
                        TX_DATA<=packet_send_buffer(BYTE_COUNT);
                        TX_START<='1';
                        BYTE_COUNT<= BYTE_COUNT + 1;
                    elsif(TX_FINISHED='1') then
                        TX_START<='0';
                    end if; 
                elsif(TX_FINISHED='1') then
                    TX_START<='0';
                end if;
            end if;
        end process;


AMP1_SEL <= AMP1_SEL_SIG;
AMP2_SEL <= AMP2_SEL_SIG;
SAMPLING_MODE <= SAMPLING_MODE_S;
ETS_PACKET_SEND <= ETS_PACKET_SEND_S;
SAMPLES_PER_PACKET <= SAMPLES_PER_PACKET_S;
CHAN_1_ON <= CHAN_1_ON_S;
CHAN_2_ON <= CHAN_2_ON_S;
DUAL_CHANNEL_MODE_ON <= DUAL_CHANNEL_MODE_ON_S;
DUAL_CHANNEL_MODE_STATUS <= DUAL_CHANNEL_MODE_STATUS_S;
FIFO_CH1_RD <= FIFO_CH1_RD_S;
FIFO_CH2_RD <= FIFO_CH2_RD_S;
PWM_DUTY <= PWM_DUTY_S;
ACC_ROUNDS <= ACC_ROUNDS_S;

HEADER_DATA_SEND(0) <= packet_send_buffer(0);
HEADER_DATA_SEND(1) <= packet_send_buffer(1);
HEADER_DATA_SEND(2) <= packet_send_buffer(2);
HEADER_DATA_SEND(3) <= packet_send_buffer(3);
HEADER_DATA_SEND(4) <= packet_send_buffer(4);
samples_per_packet_WH <= SAMPLES_PER_PACKET_S + 18;
 
end OPENLAB_BINARY_PROTO_ARCH;