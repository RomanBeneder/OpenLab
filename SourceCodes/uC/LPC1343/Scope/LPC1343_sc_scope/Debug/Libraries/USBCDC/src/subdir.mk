################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../Libraries/USBCDC/src/cdcuser.c \
../Libraries/USBCDC/src/serial.c 

OBJS += \
./Libraries/USBCDC/src/cdcuser.o \
./Libraries/USBCDC/src/serial.o 

C_DEPS += \
./Libraries/USBCDC/src/cdcuser.d \
./Libraries/USBCDC/src/serial.d 


# Each subdirectory must supply rules for building sources it contributes
Libraries/USBCDC/src/%.o: ../Libraries/USBCDC/src/%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -O0 -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g3 -I"/home/cmatar/workspace/LPC1343_Test/Libraries/SYS/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/USBCDC/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/USB/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/CMARM/inc" -I"/home/cmatar/workspace/LPC1343_Test/Libraries/JTAG/inc" -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


