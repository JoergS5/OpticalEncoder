package com.joergs5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/*
 * 
 * 600 dpi are 40 micrometer per dot
 * radius 30: +-2350, 0.08 degree per dot (print 188 mm)
 * radius 40: +-2950, 0.06 degree per dot (print 251 mm)
 * radius 45: +-3300, 0.05 degree per dot (print 282 mm)
 * radius 50: +-3700, 0.048 degree per dot (print 314 mm => part printable)
 * 
 */

public class MainCreateLinearEncoder {
	
	public static void main(String[] args) {
		String filename = "c:\\_\\LinearEncoder600dpi.png";
		new MainCreateLinearEncoder(filename);
	}

	public MainCreateLinearEncoder(String filename) {
		int bitwidth = 5;		// how many pixel width for one encoded bit
		int maxvalue = 2350;
		int minvalue = -2350;

		drawScale(bitwidth, maxvalue, minvalue, filename);
	}

	private void drawScale(int bitwidth, int maxvalue, int minvalue, String filename) {
		int height = maxvalue + 1 - minvalue;
		
		int bitLength = getBitLength(maxvalue);
		int width = bitwidth * (bitLength + 6);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        GrayCode gc = new GrayCode();
        
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, bitwidth, height);
        
		g2d.setColor(Color.black);
		g2d.fillRect((bitLength + 5) * bitwidth, 0, bitwidth, height);
		
		int currow = 0;
		for(int value = maxvalue; value >= 0; value--) {
			drawGrayCode(gc, currow, bitLength, bitwidth, g2d, true, value);
			currow++;
		}
		int ctmin = - minvalue;
		for(int value = 1; value <= ctmin; value++) {
			drawGrayCode(gc, currow, bitLength, bitwidth, g2d, false, value);
			currow++;
		}
		
		File output = new File(filename);
	    try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void drawGrayCode(GrayCode gc, int row, int bitLength, int thicknessOfBit, Graphics2D g2d,
				boolean positive, int value) {
    	int graycode = gc.getBinaryToGray(value);
    	for(int j= 0; j < bitLength; j++) {
			int grayValue = graycode & (1 << j);
			if(grayValue > 0) {
				int x1 = (j + 2 ) * thicknessOfBit;
				g2d.setColor(Color.black);
				g2d.drawLine(x1, row, x1 + thicknessOfBit-1, row);
			}
    	}
    	
    	// add plus marker
    	if(positive) {
    		g2d.setColor(Color.black);
    		int x = (bitLength + 3) * thicknessOfBit;
    		g2d.drawLine(x, row, x + thicknessOfBit - 1, row);
    	}
	}
	
	private int getBitLength(int maxNumber) {
		for(int i=0; i < 30; i++) {
			double pow = Math.pow(2, i);
			if((double) maxNumber < pow) {
				return i;
			}
		}
		return 100;
	}

}
