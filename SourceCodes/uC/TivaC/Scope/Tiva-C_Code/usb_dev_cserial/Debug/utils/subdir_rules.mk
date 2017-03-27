################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Each subdirectory must supply rules for building sources it contributes
utils/cmdline.obj: ../utils/cmdline.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: ARM Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.5/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -Ooff --opt_for_speed=1 --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.5/include" --include_path="E:/fh/Projekt/workspace/usb_dev_cserial/utils" --include_path="E:/fh/Projekt/ti_data/SW-TM4C-2.1.0.12573_2/examples/boards/ek-tm4c1294xl" --include_path="E:/fh/Projekt/ti_data/SW-TM4C-2.1.0.12573_2" --include_path="C:/ti/SW-TM4C-2.1.0.12573_2" -g --gcc --define=ccs="ccs" --define=PART_TM4C1294NCPDT --define=UART_BUFFERED --define=TARGET_IS_TM4C129_RA0 --diag_wrap=off --diag_warning=225 --display_error_number --gen_func_subsections=on --ual --preproc_with_compile --preproc_dependency="utils/cmdline.pp" --obj_directory="utils" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '

utils/ustdlib.obj: ../utils/ustdlib.c $(GEN_OPTS) $(GEN_HDRS)
	@echo 'Building file: $<'
	@echo 'Invoking: ARM Compiler'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.5/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -Ooff --opt_for_speed=1 --include_path="C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.5/include" --include_path="E:/fh/Projekt/workspace/usb_dev_cserial/utils" --include_path="E:/fh/Projekt/ti_data/SW-TM4C-2.1.0.12573_2/examples/boards/ek-tm4c1294xl" --include_path="E:/fh/Projekt/ti_data/SW-TM4C-2.1.0.12573_2" --include_path="C:/ti/SW-TM4C-2.1.0.12573_2" -g --gcc --define=ccs="ccs" --define=PART_TM4C1294NCPDT --define=UART_BUFFERED --define=TARGET_IS_TM4C129_RA0 --diag_wrap=off --diag_warning=225 --display_error_number --gen_func_subsections=on --ual --preproc_with_compile --preproc_dependency="utils/ustdlib.pp" --obj_directory="utils" $(GEN_OPTS__FLAG) "$<"
	@echo 'Finished building: $<'
	@echo ' '


