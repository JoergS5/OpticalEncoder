/**
 * @author JoergS5
 *
 */

package com.joergs5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MainCreateRoundEncoder {

	public static void main(String[] args) {
		new MainCreateRoundEncoder();
	}
	
	public MainCreateRoundEncoder() {
		int widthAndHeight = 6001;	// 600 dpi 6001 => 26 cm (DIN A4, US Letter long, partly printable)
		
		BufferedImage image = new BufferedImage(widthAndHeight, widthAndHeight, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, widthAndHeight-1, widthAndHeight-1);

        int degreeFactor = 100;
        int bitLength = getBitLength(degreeFactor);
        int thicknessOfBit = 5;
		
		drawOuterRing(g2d, widthAndHeight, thicknessOfBit);		// with empty area between outer and gray code
		
		for(int i= 0; i < bitLength; i++) {
			int x = thicknessOfBit * i + thicknessOfBit * 2;		// bits and outer ring
			int width = widthAndHeight - thicknessOfBit * 2 * i - thicknessOfBit * 4;
			for(int degreeXX = 0; degreeXX < 360 * degreeFactor; degreeXX ++) {	// shall be int to be exact value and easy gray calculation
				int graycode = getBinaryToGray(degreeXX);
				
				double degree = (double) degreeXX / (double) degreeFactor;
				double degreeDiff = (double) 1.0 / (double) degreeFactor;
				
				System.out.println("degree " + degree);
				
				int value = graycode & (1 << i);		// most changing values will be outer ring
				if(value > 0) {
					g2d.setColor(Color.black);
			        g2d.fill(new Arc2D.Double(x, x, width, width, degree, degreeDiff, Arc2D.PIE));
				}
			}
	        // remove inner
	        g2d.setColor(Color.white);
	        int width2 = width - 2 * thicknessOfBit;
	        int x2 = x + thicknessOfBit;
	        g2d.fill(new Arc2D.Double(x2, x2, width2, width2, 0, 360, Arc2D.PIE));
		}
		
		drawInnerRing(g2d, widthAndHeight, thicknessOfBit, bitLength);
		
		int crossSize = widthAndHeight / 10;
		drawCross(g2d, widthAndHeight, crossSize);
        
		File output = new File("c:\\_\\opticalencoderround.png");
	    try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
		}
	    System.out.println("bitlength: " +bitLength);
	    
	    double degreeFrac = 1.0 / (double) degreeFactor;
	    System.out.println("every bit means degree: " + degreeFrac);
	}

	private void drawCross(Graphics2D g2d, int widthAndHeight, int crossSize) {
		int x = widthAndHeight / 2;
		g2d.setColor(Color.black);
		g2d.drawLine(x, x - crossSize, x, x + crossSize);
		g2d.drawLine(x - crossSize, x, x + crossSize, x);
	}

	private void drawInnerRing(Graphics2D g2d, int widthAndHeight, int thicknessOfBit, int bitLength) {
		int width1 = widthAndHeight - 2* (thicknessOfBit * (bitLength + 3));		// one empty space between inner ring and gray code
		int x1 = (widthAndHeight - width1) / 2;
		g2d.setColor(Color.black);
        g2d.fill(new Arc2D.Double(x1, x1, width1, width1, 0, 360, Arc2D.PIE));

        int x2 = x1 + thicknessOfBit;
        int width2 = width1 - thicknessOfBit * 2; 
		g2d.setColor(Color.white);
		g2d.fill(new Arc2D.Double(x2, x2, width2, width2, 0, 360, Arc2D.PIE));
	}

	private void drawOuterRing(Graphics2D g2d, int widthAndHeight, int thicknessOfBit) {
		g2d.setColor(Color.black);
        g2d.fill(new Arc2D.Double(0, 0, widthAndHeight, widthAndHeight, 0, 360, Arc2D.PIE));

        int x2 = 0 + thicknessOfBit;
        int width2 = widthAndHeight - thicknessOfBit * 2; 
		g2d.setColor(Color.white);
		g2d.fill(new Arc2D.Double(x2, x2, width2, width2, 0, 360, Arc2D.PIE));
	}

	// code from https://en.wikipedia.org/wiki/Gray_code
	int getBinaryToGray(int num)
	{
	    return num ^ (num >> 1);
	}
	
	// code from https://en.wikipedia.org/wiki/Gray_code
	int getGrayToBinary32(int num)
	{
	    num = num ^ (num >> 16);
	    num = num ^ (num >> 8);
	    num = num ^ (num >> 4);
	    num = num ^ (num >> 2);
	    num = num ^ (num >> 1);
	    return num;
	}
	
	private int getBitLength(int degreeFactor) {
		int maxDegree = 360 * degreeFactor;
		
		for(int i=0; i < 30; i++) {
			double pow = Math.pow(2, i);
			if((double) maxDegree < pow) {
				return i;
			}
		}
		return 100;
	}
}
