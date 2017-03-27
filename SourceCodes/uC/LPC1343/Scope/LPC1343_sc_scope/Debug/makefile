################################################################################
# Automatically-generated file. Do not edit!
################################################################################

-include ../makefile.init

RM := rm -rf

# All of the sources participating in the build are defined here
-include sources.mk
-include Libraries/SYS/src/subdir.mk
-include subdir.mk
-include objects.mk

ifneq ($(MAKECMDGOALS),clean)
ifneq ($(strip $(ASM_DEPS)),)
-include $(ASM_DEPS)
endif
ifneq ($(strip $(S_UPPER_DEPS)),)
-include $(S_UPPER_DEPS)
endif
ifneq ($(strip $(C_DEPS)),)
-include $(C_DEPS)
endif
endif

-include ../makefile.defs

# Add inputs and outputs from these tool invocations to the build variables 
SECONDARY_FLASH += \
LPC1343_Test.hex \

SECONDARY_SIZE += \
LPC1343_Test.siz \


# All Target
all: LPC1343_Test.elf secondary-outputs

# Tool invocations
LPC1343_Test.elf: $(OBJS) $(USER_OBJS)
	@echo 'Building target: $@'
	@echo 'Invoking: Cross ARM C Linker'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -O0 -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g3 -T "/home/cmatar/workspace/LPC1343_Test/Linker/LPC1343.ld" -Xlinker --gc-sections -L"/home/cmatar/workspace/LPC1343_Test/Linker" -Wl,-Map,"LPC1343_Test.map" -o "LPC1343_Test.elf" $(OBJS) $(USER_OBJS) $(LIBS)
	@echo 'Finished building target: $@'
	@echo ' '

LPC1343_Test.hex: LPC1343_Test.elf
	@echo 'Invoking: Cross ARM GNU Create Flash Image'
	arm-none-eabi-objcopy -O ihex -j .text -j .data "LPC1343_Test.elf"  "LPC1343_Test.hex"
	@echo 'Finished building: $@'
	@echo ' '

LPC1343_Test.siz: LPC1343_Test.elf
	@echo 'Invoking: Cross ARM GNU Print Size'
	arm-none-eabi-size --format=berkeley -x --totals "LPC1343_Test.elf"
	@echo 'Finished building: $@'
	@echo ' '

# Other Targets
clean:
	-$(RM) $(OBJS)$(SECONDARY_FLASH)$(SECONDARY_SIZE)$(ASM_DEPS)$(S_UPPER_DEPS)$(C_DEPS) LPC1343_Test.elf
	-@echo ' '

secondary-outputs: $(SECONDARY_FLASH) $(SECONDARY_SIZE)

.PHONY: all clean dependents
.SECONDARY:

-include ../makefile.targets
