package com.raslan.sff.core.cs;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import com.raslan.sff.core.ImageHelper;
import com.raslan.sff.core.util.Logger;

public class SegmentationTester {
	public static void main(String[] args){
		String importPath = "/home/raslanrauff/ML/new/test.png";
		String charExportPath = "/home/raslanrauff/ML/new/extract/";
		Logger logger = Logger.getInstance();
		Segmentation segmentation = new Segmentation();
		try {
			BufferedImage img = ImageIO.read(new File(importPath));
			List<BufferedImage> segmentedChars= segmentation.segment(img);
			
			//extract in same sequence
			int pointer = 1;
			for(BufferedImage char_ : segmentedChars){
				ImageHelper.writeImageToFile(char_, "png", charExportPath+"char"+pointer+++".png");
			}
		} catch (IOException e) {
			logger.log("SegmentationTester", e.toString());
		}
		
		
	}
}
