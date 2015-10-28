/*
#   fsm_app.v - Finite state machine to send out an example test, and process inputs
#
#   Description: Finite state machine to exercise the JTAG UART.  
#		Note that this ignores the full/empty signals, so don't fill the buffer!
#  
#   Copyright (C) 2014  Binary Logic (nhi.phan.logic at gmail.com).
#
#   This file is part of the Virtual JTAG UART toolkit
#   
#   Virtual UART is free software: you can redistribute it and/or modify
#   it under the terms of the GNU General Public License as published by
#   the Free Software Foundation, either version 3 of the License, or
#   (at your option) any later version.
#   
#   This program is distributed in the hope that it will be useful,
#   but WITHOUT ANY WARRANTY; without even the implied warranty of
#   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
#   GNU General Public License for more details.
#   
#   You should have received a copy of the GNU General Public License
#   along with this program.  If not, see <http://www.gnu.org/licenses/>.
#
*/

//=======================================================
//  Application to put characters in the output stream and echo input
//=======================================================
`include "system_include.v"

module fsm_app(
	input clk_i,
	input nreset_i
);

// FSM States
parameter S_INIT = 0, S_START = 1, S_WRITE = 2, S_CNT = 3, S_WAIT = 4, S_READ = 5;

//=======================================================
//  REG/WIRE declarations
//=======================================================
reg [2:0] state;
reg [7:0] data_out;
reg [15:0] count;
reg wr, rd;
wire [7:0] data_in;
wire txmt, txfl, rxmt, rxfl;

//=======================================================
//  Outputs
//=======================================================

//=======================================================
//  Structural coding
//=======================================================
// JTag Interface
jtag_uart uart(
	.clk_i( clk_i ),
	.nreset_i(nreset_i),
	.nwr_i(wr),
	.data_i(data_out),
	.rd_i(rd),
	.data_o(data_in),
	.txmt(txmt),
	.txfl(txfl),
	.rxmt(rxmt),
	.rxfl(rxfl)
	);

//=======================================================
//  Procedural coding
//=======================================================

always @(posedge clk_i)
begin
	if( !nreset_i )
	begin
		state = S_INIT;
		wr = 1'b1;
		rd = 1'b0;
		count = 16'b0;
	end else begin
		case( state )
			S_INIT :
				begin
					wr = 1'b1;
					rd = 1'b0;
					state = S_START;
				end
			S_START :
				begin
					data_out = 83;
					wr = 1'b0;
					state = S_WAIT;
				end
			S_WAIT :
				begin
				wr = 1'b1;
				count = 16'b0;
				if( !rxmt )
				begin
					rd = 1'b1;
					state = S_READ;
				end
				else begin
					wr = 1'b1;
					rd = 1'b0;
					state = S_WAIT;
				end
				end
			S_READ :
				begin
					rd = 1'b0;
					if(data_in == 97)
					begin
					state = S_CNT;
					end
					else
					begin
					state = S_WAIT;
					end
				end
			S_WRITE:
				begin
					wr = 1'b0;
					state = S_CNT;
				end
			S_CNT:
				begin
					wr = 1'b1;
					data_out = 66;
					if( count != 16'hFFF )
					begin
						state = S_WRITE;
						count = count + 16'h1;
					end
					else
					begin
						data_out = 67;
						wr = 1'b0;
						state = S_WAIT;
					end
				end	
			default:
				state = S_INIT;
		endcase
	end
end
	
endmodule