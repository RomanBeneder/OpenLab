################################################################################
# Automatically-generated file. Do not edit!
################################################################################

# Add inputs and outputs from these tool invocations to the build variables 
C_SRCS += \
../commands.c \
../crc.c \
../gpio.c \
../main.c \
../pwm.c \
../timer.c \
../uart.c 

OBJS += \
./commands.o \
./crc.o \
./gpio.o \
./main.o \
./pwm.o \
./timer.o \
./uart.o 

C_DEPS += \
./commands.d \
./crc.d \
./gpio.d \
./main.d \
./pwm.d \
./timer.d \
./uart.d 


# Each subdirectory must supply rules for building sources it contributes
%.o: ../%.c
	@echo 'Building file: $<'
	@echo 'Invoking: Cross ARM C Compiler'
	arm-none-eabi-gcc -mcpu=cortex-m3 -mthumb -O0 -fmessage-length=0 -fsigned-char -ffunction-sections -fdata-sections  -g3 -I"/home/cmatar/workspace/LPC1343_Test/Libraries/SYS/inc" -std=gnu11 -MMD -MP -MF"$(@:%.o=%.d)" -MT"$(@)" -c -o "$@" "$<"
	@echo 'Finished building: $<'
	@echo ' '


