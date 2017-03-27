################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../Libraries/JTAG/src/jtag_functions.c 

OBJS += \
./Libraries/JTAG/src/jtag_functions.o 

C_DEPS += \
./Libraries/JTAG/src/jtag_functions.d 


# Each subdirectory must supply rules for building sources it contributes
Libraries/JTAG/src/%.o: ../Libraries/JTAG/src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -O0 -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g3 -I"/home/cmatar/workspace/LPC1343_Test/Libraries/SYS/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/USBCDC/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/USB/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/CMARM/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/JTAG/inc" -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


