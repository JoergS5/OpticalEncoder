package com.joergs5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
/* 
 * the camera can capture only 3 mm width, so calibration is difficult.
 * A possible solution is to printout multiple encodings side by side, so
 * the camera need not exact calibration.
 * The graycodes are separated by a colored or gray bar and space. 
 * 
 * 
 */
public class MainLinearColorMultiple {
	private String filename = "c:\\_\\Linear_600dpi_5width_10multi.png";
	private int bitWidth = 5;	// how many pixel width for one encoded bit
	private Color colorBits = Color.black;	// color of graycode bits
	private Color colorSign = Color.black;	// + or - of value
	private int separatorWidth = 5;
	private int spaceWidth = 2;		// space between separator and bits
	private Color colorSeparator = Color.red;
	private int maxvalue = 2350;
	private int minvalue = -2350;
	private int howManyMultiple = 3;		// how many graycoded sections

	public static void main(String[] args) {
		new MainLinearColorMultiple();
	}
	
	public MainLinearColorMultiple() {
		drawScale();
	}
	private void drawScale() {
		int height = maxvalue + 1 - minvalue;
		
		int bitLength = getBitLength(maxvalue);
		
		int width = getWidth(bitLength);
		
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width, height);

        GrayCode gc = new GrayCode();
        
        int encodingWidth = getWidthOneEncoding(bitLength);
        for(int i = 0; i < howManyMultiple; i++) {
        	 int offset = i * encodingWidth;
        	 
     		g2d.setColor(colorSeparator);
    		g2d.fillRect(offset, 0, separatorWidth, height);
            
    		int offsetGray = offset + separatorWidth + spaceWidth;
    		
    		int currow = 0;
    		for(int value = maxvalue; value >= 0; value--) {
    			drawGrayCode(gc, currow, bitLength, g2d, true, value, offsetGray);
    			currow++;
    		}
    		int ctmin = - minvalue;
    		for(int value = 1; value <= ctmin; value++) {
    			drawGrayCode(gc, currow, bitLength, g2d, false, value, offsetGray);
    			currow++;
    		}
        }
        
        // last separator
        int offsetLast = howManyMultiple * encodingWidth;
 		g2d.setColor(colorSeparator);
		g2d.fillRect(offsetLast, 0, separatorWidth, height);
        
		
		File output = new File(filename);
	    try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/*
	 * multiplied(separator, space to grayscale, grayscale) + space + last separator
	 */
	private int getWidth(int bitLength) {
		int grayWidth = bitWidth * (bitLength + 1);		// +1 for +- sign
		return howManyMultiple * (separatorWidth + spaceWidth + grayWidth + spaceWidth)
				+ spaceWidth + separatorWidth;
	}
	
	// width of separator+space+grayencoding
	private int getWidthOneEncoding(int bitLength) {
		int grayWidth = bitWidth * (bitLength + 1);		// +1 for +- sign
		return separatorWidth + spaceWidth + grayWidth + spaceWidth;
	}

	private void drawGrayCode(GrayCode gc, int row, int bitLength, Graphics2D g2d,
				boolean positive, int value, int offset) {
    	int graycode = gc.getBinaryToGray(value);
    	for(int j= 0; j < bitLength; j++) {
			int grayValue = graycode & (1 << j);
			if(grayValue > 0) {
				int x1 = offset + j * bitWidth;
				g2d.setColor(colorBits);
				g2d.drawLine(x1, row, x1 + bitWidth - 1, row);
			}
    	}
    	
    	// add plus marker
    	if(positive) {
    		g2d.setColor(colorSign);
    		int x = offset + bitLength * bitWidth;
    		g2d.drawLine(x, row, x + bitWidth - 1, row);
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
