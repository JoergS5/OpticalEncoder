package com.joergs5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MainCreateLinearEncoder {
	
	public static void main(String[] args) {
		new MainCreateLinearEncoder();
	}

	public MainCreateLinearEncoder() {
		int width = 150;		// 600 with 600 dpi => 2.6 cm
		int maxvalue = 2350;
		int minvalue = -2350;

		drawScale(width, maxvalue, minvalue, "LinearEncoder600dpi");
	}

	private void drawScale(int width, int maxvalue, int minvalue, String filename) {
		int height = maxvalue + 1 - minvalue;
		
		int bitLength = getBitLength(maxvalue);
        int thicknessOfBit = width / (bitLength + 6);
        int imageWidth = (bitLength + 6) * thicknessOfBit;
		
		BufferedImage image = new BufferedImage(imageWidth, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, imageWidth, height);

        GrayCode gc = new GrayCode();
        
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, thicknessOfBit, height);
        
		g2d.setColor(Color.black);
		g2d.fillRect((bitLength + 5) * thicknessOfBit, 0, thicknessOfBit, height);
		
		int currow = 0;
		for(int value = maxvalue; value >= 0; value--) {
			drawGrayCode(gc, currow, bitLength, thicknessOfBit, g2d, true, value);
			currow++;
		}
		int ctmin = - minvalue;
		for(int value = 1; value <= ctmin; value++) {
			drawGrayCode(gc, currow, bitLength, thicknessOfBit, g2d, false, value);
			currow++;
		}
		
		File output = new File("c:\\_\\" + filename + ".png");
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
