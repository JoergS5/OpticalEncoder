# OpticalEncoder
Creating an Optical Encoder for Scara (serial or parallel) 3D printers or robots. Firmware will be created so it will work with the Duet 3D printer controller. Current plan is to use a STM32 board for connection to Duet. To the STM32 will be connected 3 ESP32-CAM boards for endstops of three actuators (two rotary and one for Z axis). The software for STM32 and ESP32 will be published here also.

MainCreateRoundEncoder.java is a program to create the OpticalEncoder printout, see the png image as an example. This printout will be used by a camera to analyze the current position.
If there is need to change the properties of the png:
- widthAndHeight is the dimension of the image in dots
- degreeFactor is the sub degree resolution of the bit encoding, 100 is 1/100 degree, 1 would mean 1 degree for each encoding
- thicknessOfBit is how many dots a single bit encoding has. Calculation was 5 * 15 bits encoding for about 4 mm of sensor size OV2640 of the ESP32-CAM

The png has the format to be printed out 26 cm (DIN A 4 or US Letter long) with a 600 dpi printer. Resolution of the gray code encoding is 0.01 degree. Printout without resizing. On the right is the 0 degree position, so print out the right half on DIN A 4 (or print out on a A3 printer).
