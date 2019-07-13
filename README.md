# OpticalEncoder
Creating an Optical Encoder for Scara (serial or parallel) 3D printers or robots. Firmware will be created so it will work with the Duet 3D printer controller. Current plan is to use a STM32 board for connection to Duet. To the STM32 will be connected 3 ESP32-CAM boards for endstops of three actuators (two rotary and one for Z axis).

MainCreateRoundEncoder.java is a program to create the OpticalEncoder printout, see the png image as an example. This printout will be used by a camery to analyze the current position.
