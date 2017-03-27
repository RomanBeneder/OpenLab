#!/bin/bash -x

#Set path to your modelsim install directory
#modelsim_path="/cygdrive/d/Programme/altera/10.0/modelsim_ase/win32aloem/"
modelsim_path="/cygdrive/d/altera/10/modelsim_ase/win32aloem/"

simulate=$modelsim_path"vsim.exe"
$simulate -c   -do "do libs.do; quit"

#working
compile=$modelsim_path"vcom.exe"

$compile -quiet -93 ../../generate/cyclone_iii_ep3c16/mc8051_rom.vhd
$compile -quiet -93 ../../generate/cyclone_iii_ep3c16/mc8051_ram.vhd
$compile -quiet -93 ../../generate/cyclone_iii_ep3c16/mc8051_ramx.vhd
$compile -quiet -93 ../../generate/cyclone_iii_ep3c16/prescaler.vhd

$compile -quiet -93 ../../hdl/mc8051/mc8051_p.vhd
     
$compile -quiet -93 ../../hdl/mc8051/control_mem_.vhd
$compile -quiet -93 ../../hdl/mc8051/control_mem_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/control_mem_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/control_fsm_.vhd
$compile -quiet -93 ../../hdl/mc8051/control_fsm_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/control_fsm_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/mc8051_control_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_control_struc.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_control_struc_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/alucore_.vhd
$compile -quiet -93 ../../hdl/mc8051/alucore_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/alucore_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/alumux_.vhd
$compile -quiet -93 ../../hdl/mc8051/alumux_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/alumux_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/addsub_cy_.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_cy_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_cy_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/addsub_ovcy_.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_ovcy_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_ovcy_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/addsub_core_.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_core_struc.vhd
$compile -quiet -93 ../../hdl/mc8051/addsub_core_struc_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/comb_divider_.vhd
$compile -quiet -93 ../../hdl/mc8051/comb_divider_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/comb_divider_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/comb_mltplr_.vhd
$compile -quiet -93 ../../hdl/mc8051/comb_mltplr_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/comb_mltplr_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/dcml_adjust_.vhd
$compile -quiet -93 ../../hdl/mc8051/dcml_adjust_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/dcml_adjust_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/mc8051_alu_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_alu_struc.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_alu_struc_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/mc8051_siu_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_siu_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_siu_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/mc8051_tmrctr_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_tmrctr_rtl.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_tmrctr_rtl_cfg.vhd
     
$compile -quiet -93 ../../hdl/mc8051/mc8051_core_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_core_struc.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_core_struc_cfg.vhd
     
  
$compile -quiet -93 ../../hdl/mc8051/mc8051_top_.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_top_struc.vhd
$compile -quiet -93 ../../hdl/mc8051/mc8051_top_struc_cfg.vhd

$compile -quiet -93 ../../hdl/fpga_top_.vhd
$compile -quiet -93 ../../hdl/fpga_top_rtl.vhd
$compile -quiet -93 ../../hdl/fpga_top_rtl_cfg.vhd
     
$compile -quiet -93 ../../tb/testbench_.vhd
$compile -quiet -93 ../../tb/testbench_sim.vhd
$compile -quiet -93 ../../tb/testbench_sim_cfg.vhd

