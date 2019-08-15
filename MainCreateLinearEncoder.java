package com.joergs5;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public class MainCreateLinearEncoder {
	private boolean UP = true;
	private boolean DOWN = false;

	public static void main(String[] args) {
		new MainCreateLinearEncoder();
	}

	public MainCreateLinearEncoder() {
		int width = 150;		// 600 with 600 dpi => 2.6 cm
		int height = 6000;	// 600 dpi 6000 => 26 cm (DIN A4, US Letter long, partly printable)

		drawScale(width, height, "opticalencoderlinearLeft", DOWN);
		drawScale(width, height, "opticalencoderlinearRight", UP);
	}

	private void drawScale(int width, int height, String filename, boolean direction) {
		BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_BINARY);
        Graphics2D g2d = image.createGraphics();

        g2d.setColor(Color.white);
        g2d.fillRect(0, 0, width-1, height-1);

        GrayCode gc = new GrayCode();
        int bitLength = getBitLength(height);
        int thicknessOfBit = width / (bitLength + 4);
        
		g2d.setColor(Color.black);
		g2d.fillRect(0, 0, thicknessOfBit, height-1);
        
		g2d.setColor(Color.black);
		g2d.fillRect((bitLength + 3) * thicknessOfBit, 0, thicknessOfBit, height-1);
        
        for(int i=0; i < height; i++) {
        	int graycode = gc.getBinaryToGray(i);
        	for(int j= 0; j < bitLength; j++) {
				int value = graycode & (1 << j);
				if(value > 0) {
					int x1 = thicknessOfBit * j + 2 * thicknessOfBit;
					int y1 = 0;
					if(direction == UP) {
						y1 = i;
					}
					else {
						y1 = height - i;
					}
					g2d.setColor(Color.black);
					g2d.drawLine(x1, y1, x1 + thicknessOfBit, y1);
				}
        	}
        }
        
        
        
        
		File output = new File("c:\\_\\" + filename + ".png");
	    try {
			ImageIO.write(image, "png", output);
		} catch (IOException e) {
			e.printStackTrace();
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
