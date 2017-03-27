onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic /testbench/sys_clk
add wave -noupdate -format Logic /testbench/sys_rst_in
add wave -noupdate -format Logic /testbench/top/i_mc8051_top/i_mc8051_rom/clock
add wave -noupdate -format Literal -radix hexadecimal /testbench/top/i_mc8051_top/i_mc8051_rom/address
add wave -noupdate -format Literal -radix hexadecimal /testbench/top/i_mc8051_top/i_mc8051_rom/q
add wave -noupdate -format Logic /testbench/top/i_prescaler/locked
add wave -noupdate -format Logic /testbench/top/s_reset_8051
add wave -noupdate -format Logic /testbench/top/s_p2_o(0)
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {{Cursor 1} {96460000 ps} 0}
configure wave -namecolwidth 150
configure wave -valuecolwidth 100
configure wave -justifyvalue left
configure wave -signalnamewidth 1
configure wave -snapdistance 10
configure wave -datasetprefix 0
configure wave -rowmargin 4
configure wave -childrowmargin 2
configure wave -gridoffset 0
configure wave -gridperiod 1
configure wave -griddelta 40
configure wave -timeline 0
configure wave -timelineunits us
update
WaveRestoreZoom {0 ps} {105 us}
