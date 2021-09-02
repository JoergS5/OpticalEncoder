# OpticalEncoder
Creating an Optical Encoder for 3D printers or robots, e. g. for closed loop motors. Current plan is to use a camera to read the encoder information and let a ESP32 or Raspi create quadrature encoder signals.

MainCreateLinearEncoder.java is a program to create the OpticalEncoder printout to be printed at the side of a plate which is the actuator (e.g. gear of a robot arm). A 600 dpi printer can printout:
- values from +2350 to -2350, total of 4701 values
- the number of necessarcy bits are calculated by the needed numbers (here +-2350 < 4096 => 12 bits needed)
- the created png should be tagges with dpi values 600 dpi resolution in both directions
- the printout is 20 cm long, which is 360° for a 30 mm radius circle
- the printer setup shall print the original size with dpi settings without resizing
- The resoltuion can be higher than printer's resolution using the camera to analyze dot positions. The 600 dpi dots are 40 micrometers each, camera pixel are often 2 micrometer or less.

Parameters of the program:
- filename (change to linux syntax if needed)
- bitwidth: how many dots for every bit, the +- marker and the edge lines left and right
- maxvalue and minvalue for values which shall be gray encoded

MainLinearColorMultiple creates a printout of multiple graycoded
- if the camera cannot be placed exactly, this allows reading of the signals, because they are redundant
- the code is parametrized more than before, eg colors, bit and separator widths, number of the multiplicity

MainCreateRoundEncoder creates round printouts. The dot positions are not exact, so this method is deprecated, the program remains for archiving and contains bugs.
