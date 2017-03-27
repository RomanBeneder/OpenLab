onerror {resume}
quietly WaveActivateNextPane {} 0
add wave -noupdate -format Logic /testbench/osc
add wave -noupdate -format Logic /testbench/button2
add wave -noupdate -format Logic /testbench/top/s_locked
add wave -noupdate -format Logic /testbench/top/s_reset_8051
add wave -noupdate -format Logic /testbench/top/i_mc8051_top/i_mc8051_rom/clock
add wave -noupdate -format Literal -radix hexadecimal /testbench/top/i_mc8051_top/i_mc8051_rom/address
add wave -noupdate -format Literal -radix hexadecimal /testbench/top/i_mc8051_top/i_mc8051_rom/q
add wave -noupdate -format Logic /testbench/ledg_o(0)
add wave -noupdate -format Logic /testbench/ledg_o(1)
add wave -noupdate -format Logic /testbench/ledg_o(2)
add wave -noupdate -format Logic /testbench/ledg_o(3)
add wave -noupdate -format Logic /testbench/ledg_o(4)
add wave -noupdate -format Logic /testbench/ledg_o(5)
add wave -noupdate -format Logic /testbench/ledg_o(6)
add wave -noupdate -format Logic /testbench/ledg_o(7)
TreeUpdate [SetDefaultTree]
WaveRestoreCursors {0 ps}
#WaveRestoreZoom {0 ps} {1 ns}
configure wave -namecolwidth 150
configure wave -valuecolwidth 100
configure wave -signalnamewidth 0
configure wave -justifyvalue left
WaveRestoreZoom {0 ps} {105 us}
