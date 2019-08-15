package com.joergs5;

public class GrayCode {

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
	

}
