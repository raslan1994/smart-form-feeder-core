package com.raslan.sff.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.raslan.sff.core.util.Logger;

public class ImageHelper {
	
	static Logger logger = Logger.getInstance();
	
	/**
	 * Retrieve image pixel matrix
	 * @param img
	 * @return
	 */
	public static int[][] toPixelMatrix(BufferedImage img){
		int w = img.getWidth();
		int h = img.getHeight();
		int[][] pixelMatrix = new int[w][h];
		
		for(int wi = 0; wi < w; wi++){
			for(int hi = 0; hi < h; hi++){
				pixelMatrix[wi][hi] = Math.abs(img.getRGB(wi, hi));
			}
		}
		
		return pixelMatrix;
	}
	
	public static void writeImageToFile(BufferedImage img, String mime, String filename){
		try{
			ImageIO.write(img, mime, new File(filename));
		}catch(IOException ex){
			logger.error("ImageHelper", ex.getMessage());
		}
	}
	
	public static int countGreyPixels(int[][] matrix, int x, int y, int width, int height){
		int pixels = 0;
		
		for(int x_ = x; x_ < x+width; ++x_){
			for(int y_ = y; y_< y + height; ++y_){
				if(matrix[x_][y_] > Config.THRESHOLD){
					++pixels;
				}
			}
		}
		
		return pixels;
	}
}
