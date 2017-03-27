################################################################################
# Automatically-generated file. Do not edit!
################################################################################

SHELL = cmd.exe

CG_TOOL_ROOT := C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7

GEN_OPTS__FLAG := 
GEN_CMDS__FLAG := 

ORDERED_OBJS += \
"./startup_ccs.obj" \
"./usb_dev_cserial.obj" \
"./usb_structs.obj" \
"./utils/cmdline.obj" \
"./utils/ustdlib.obj" \
"./drivers/pinout.obj" \
"../usb_dev_cserial_ccs.cmd" \
$(GEN_CMDS__FLAG) \
-l"libc.a" \
-l"C:/ti/SW-TM4C-2.1.0.12573_2/examples/boards/ek-tm4c1294xl/usb_dev_cserial/ccs/../../../../../driverlib/ccs/Debug/driverlib.lib" \
-l"C:/ti/SW-TM4C-2.1.0.12573_2/examples/boards/ek-tm4c1294xl/usb_dev_cserial/ccs/../../../../../usblib/ccs/Debug/usblib.lib" \

-include ../makefile.init

RM := DEL /F
RMDIR := RMDIR /S/Q

# All of the sources participating in the build are defined here
-include sources.mk
-include subdir_vars.mk
-include utils/subdir_vars.mk
-include drivers/subdir_vars.mk
-include subdir_rules.mk
-include utils/subdir_rules.mk
-include drivers/subdir_rules.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(S_DEPS)),)
-include $(S_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(S62_DEPS)),)
-include $(S62_DEPS)
endif
ifneq ($(strip $(C64_DEPS)),)
-include $(C64_DEPS)
endif
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(CC_DEPS)),)
-include $(CC_DEPS)
endif
ifneq ($(strip $(S55_DEPS)),)
-include $(S55_DEPS)
endif
ifneq ($(strip $(C67_DEPS)),)
-include $(C67_DEPS)
endif
ifneq ($(strip $(CLA_DEPS)),)
-include $(CLA_DEPS)
endif
ifneq ($(strip $(C??_DEPS)),)
-include $(C??_DEPS)
endif
ifneq ($(strip $(CPP_DEPS)),)
-include $(CPP_DEPS)
endif
ifneq ($(strip $(S??_DEPS)),)
-include $(S??_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
ifneq ($(strip $(C62_DEPS)),)
-include $(C62_DEPS)
endif
ifneq ($(strip $(CXX_DEPS)),)
-include $(CXX_DEPS)
endif
ifneq ($(strip $(C++_DEPS)),)
-include $(C++_DEPS)
endif
ifneq ($(strip $(ASM_UPPER_DEPS)),)
-include $(ASM_UPPER_DEPS)
endif
ifneq ($(strip $(K_DEPS)),)
-include $(K_DEPS)
endif
ifneq ($(strip $(C43_DEPS)),)
-include $(C43_DEPS)
endif
ifneq ($(strip $(INO_DEPS)),)
-include $(INO_DEPS)
endif
ifneq ($(strip $(S67_DEPS)),)
-include $(S67_DEPS)
endif
ifneq ($(strip $(SA_DEPS)),)
-include $(SA_DEPS)
endif
ifneq ($(strip $(S43_DEPS)),)
-include $(S43_DEPS)
endif
ifneq ($(strip $(OPT_DEPS)),)
-include $(OPT_DEPS)
endif
ifneq ($(strip $(PDE_DEPS)),)
-include $(PDE_DEPS)
endif
ifneq ($(strip $(S64_DEPS)),)
-include $(S64_DEPS)
endif
ifneq ($(strip $(C_UPPER_DEPS)),)
-include $(C_UPPER_DEPS)
endif
ifneq ($(strip $(C55_DEPS)),)
-include $(C55_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
EXE_OUTPUTS += \
usb_dev_cserial.out \

EXE_OUTPUTS__QUOTED += \
"usb_dev_cserial.out" \

BIN_OUTPUTS += \
usb_dev_cserial.bin \

BIN_OUTPUTS__QUOTED += \
"usb_dev_cserial.bin" \


# All Target
all: usb_dev_cserial.out

# Tool invocations
usb_dev_cserial.out: $(OBJS) $(CMD_SRCS) $(GEN_CMDS)
	@echo 'Building target: $@'
	@echo 'Invoking: ARM Linker'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armcl" -mv7M4 --code_state=16 --float_support=FPv4SPD16 --abi=eabi -me -O2 --opt_for_speed=3 -g --gcc --define=ccs="ccs" --define=PART_TM4C1294NCPDT --define=UART_BUFFERED --define=TARGET_IS_TM4C129_RA0 --diag_wrap=off --display_error_number --diag_warning=225 --gen_func_subsections=on --ual -z -m"usb_dev_cserial_ccs.map" --heap_size=0 --stack_size=1024 -i"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/lib" -i"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/include" --reread_libs --display_error_number --diag_wrap=off --warn_sections --xml_link_info="usb_dev_cserial_linkInfo.xml" --rom_model -o "usb_dev_cserial.out" $(ORDERED_OBJS)
	@echo 'Finished building target: $@'
	@echo ' '
	@$(MAKE) --no-print-directory post-build

usb_dev_cserial.bin: $(EXE_OUTPUTS)
	@echo 'Invoking: ARM Hex Utility'
	"C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armhex"  -o "usb_dev_cserial.bin" $(EXE_OUTPUTS__QUOTED)
	@echo 'Finished building: $@'
	@echo ' '
	@$(MAKE) --no-print-directory post-build

# Other Targets
clean:
	-$(RM) $(EXE_OUTPUTS__QUOTED)$(BIN_OUTPUTS__QUOTED)
	-$(RM) "startup_ccs.pp" "usb_dev_cserial.pp" "usb_structs.pp" "utils\cmdline.pp" "utils\ustdlib.pp" "drivers\pinout.pp" 
	-$(RM) "startup_ccs.obj" "usb_dev_cserial.obj" "usb_structs.obj" "utils\cmdline.obj" "utils\ustdlib.obj" "drivers\pinout.obj" 
	-@echo 'Finished clean'
	-@echo ' '

post-build:
	-"C:/ti/ccsv6/utils/tiobj2bin/tiobj2bin" "usb_dev_cserial.out" "usb_dev_cserial.bin" "C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armofd" "C:/ti/ccsv6/tools/compiler/ti-cgt-arm_5.2.7/bin/armhex" "C:/ti/ccsv6/utils/tiobj2bin/mkhex4bin"
	-@echo ' '

.PHONY: all clean dependents
.SECONDARY: post-build

-include ../makefile.targets

