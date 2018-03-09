package com.raslan.sff.core.cs;

import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.List;

import com.raslan.sff.core.Config;

public class Segmentation {
	private static boolean rowHasBlackPixel(BufferedImage img, int row){
		for(int x = 0; x < img.getWidth(); x++){
			int p = Math.abs(img.getRGB(x, row));
			if(p > Config.THRESHOLD){return true;}
		}
		return false;
	}
	
	private static boolean columnHasBlackPixel(BufferedImage img, int col){
		for(int y = 0; y < img.getHeight(); y++){
			int p = Math.abs(img.getRGB(col, y));
			if(p > Config.THRESHOLD){return true;}
		}
		return false;
	}
	
	private static List<BufferedImage> lineSegmentation(BufferedImage img){
		int width = img.getWidth()-1;
		int y0 = -1;
		int y1 = -1;
		
		List<BufferedImage> lines = new LinkedList<BufferedImage>();
		
		for(int y = 0; y <img.getHeight(); y++){
			if(rowHasBlackPixel(img, y)){
				if(y0 == -1){
					y0 =y;
				}
				y1 = y;
			}else if(y0 != -1 && y0 != y1){
				BufferedImage subImg = img.getSubimage(0, y0, width, y1-y0);
				lines.add(subImg);
				
				//reset
				y0 = -1;
				y1 = -1;
			}
		}
		
		//In case image ends on black pixel
		if(y0 != -1 && y0 != y1){
			BufferedImage subImg = img.getSubimage(0, y0, width, y1 - y0);
			lines.add(subImg);
		}
		return lines;
	}
	
	private static List<BufferedImage> characterSegmentation(BufferedImage img){
		int height = img.getHeight()-1;
		int x0 = -1;
		int x1 = -1;
		List<BufferedImage> chars = new LinkedList<BufferedImage>();
		
		for(int x = 0; x< img.getWidth(); x++){
			if(columnHasBlackPixel(img, x)){
				if(x0 == -1){x0=x;}
				x1 = x;
			}else if(x0 != -1 && x0 != x1){
				BufferedImage subImg = img.getSubimage(x0, 0, x1-x0, height);
				chars.add(subImg);
				
				//reset
				x0 = -1;
				x1 = -1;
			}
		}
		
		//In case image ends on black pixel
		if(x0 != -1 && x0 != x1){
			BufferedImage subImage = img.getSubimage(x0, 0, x1-x0, height);
			chars.add(subImage);
		}
		
		
		return chars;
	}
	
	private static BufferedImage trim(BufferedImage img){
		int width = img.getWidth()-1;
		int y0 = -1;
		int y1 = -1;
		
		for(int y = 0; y < img.getHeight(); y++){
			if(rowHasBlackPixel(img, y)){
				if(y0 == -1){y0 =y;}
				y1 = y;
			}else if(y0 != -1 && y0 != y1){
				return img.getSubimage(0, y0, width, y1 - y0);
			}
		}
		
		return img.getSubimage(0, y0, width, y1-y0);
	}
	
	public List<BufferedImage> segment(BufferedImage img){
		List<BufferedImage> seperatedChars = new LinkedList<BufferedImage>();
		List<BufferedImage> lines = lineSegmentation(img);
		
		System.out.println("no of lines " + lines.size());
		
		for(BufferedImage line : lines){
			List<BufferedImage> chars = characterSegmentation(line);
			
			System.out.println("no of chars " + chars.size());
			
			for(BufferedImage c : chars){
				BufferedImage tc = trim(c);
				seperatedChars.add(tc);
			}
		}
		
		return seperatedChars;
	}
}
