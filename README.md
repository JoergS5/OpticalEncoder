# OpticalEncoder
Creating an Optical Encoder for Scara (serial or parallel) 3D printers or robots. Current plan is to use a Raspberry 3+ board for connection to Duet and a 5MP or 8 MP camera for every actuator. The Raspberry software will be published here also.

MainCreateRoundEncoder.java is a program to create the OpticalEncoder printout, see the png image as an example. This printout will be used by a camera to analyze the current position.
If there is need to change the properties of the png:
- widthAndHeight is the dimension of the image in dots
- degreeFactor is the sub degree resolution of the bit encoding, 100 is 1/100 degree, 1 would mean 1 degree for each encoding
- thicknessOfBit is how many dots a single bit encoding has. Calculation was 5 * 15 bits encoding for about 4 mm of sensor size OV2640 of the ESP32-CAM
- File destination and name needs to be changed
- the program runs with Java 8
The png has the format to be printed out 26 cm (DIN A 4 or US Letter long) with a 600 dpi printer. Resolution of the gray code encoding is 0.01 degree. Printout without resizing. On the right is the 0 degree position, so print out the right half on DIN A 4 (or print out on a A3 printer). The cross shall help centering the printout on the actuator.

MainCreateLinearEncoder.java is a program to create the OpticalEncoder printout to be printed at the side of a big plate which is the actuator. A 300 mm diameter plate has about 940 mm perimeter. Printout should be in two parts, the middle 0 position is free and will be replaced by a more detailed image for higher precision.
When printing, print without changing resolution (original size). Size should be about 2.6 cm x 25 cm. If format is different, change the dpi setting for the image before printing. In Irfanview I had to change (in menu information) dpi to width 150 dpi, height 600 dpi. Print resolution was ok then.
