################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../Libraries/SYS/src/cr_startup_lpc13.c \
../Libraries/SYS/src/system_LPC13xx.c 

OBJS += \
./Libraries/SYS/src/cr_startup_lpc13.o \
./Libraries/SYS/src/system_LPC13xx.o 

C_DEPS += \
./Libraries/SYS/src/cr_startup_lpc13.d \
./Libraries/SYS/src/system_LPC13xx.d 


# Each subdirectory must supply rules for building sources it contributes
Libraries/SYS/src/%.o: ../Libraries/SYS/src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -O0 -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g3 -I"/home/cmatar/workspace/LPC1343_Test/Libraries/SYS/inc" -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


