package com.raslan.sff.filters;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import javax.imageio.ImageIO;

import com.raslan.sff.core.ImageHelper;
import com.raslan.sff.core.util.ImageFinder;
import com.raslan.sff.core.util.Logger;

public class FilterTester {
	public static void main(String [] args){
		Logger logger = Logger.getInstance();
		ImageFinder imgFinder = new ImageFinder();
		String baseDirPath = "/home/raslanrauff/ML/new/extract/";
		String exportPath = "/home/raslanrauff/ML/new/filtered/";
		List<Filter> defaultFilters = FiltersFactory.getDefaultFilters();
		try {
			Files.walkFileTree(new File(baseDirPath).toPath(), imgFinder);
			List<Path> imgPaths = imgFinder.getImages();
			
			for(Path p : imgPaths){
				BufferedImage img = ImageIO.read(p.toFile());
				
				for(Filter f : defaultFilters){
					f.applyFilter(img);
				}
				
				ImageHelper.writeImageToFile(img, "png", exportPath+p.getFileName().toString());
				logger.info("FilterTester", "[EXPORTED FILE] -> " + exportPath+p.getFileName().toString());
			}
			
		} catch (IOException e) {
			logger.log("FilterTester", e.toString());
		}
		
	}
}
