package com.raslan.sff.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import javax.imageio.ImageIO;

import com.raslan.sff.core.cs.Segmentation;
import com.raslan.sff.core.util.ImageFinder;
import com.raslan.sff.filters.Filter;
import com.raslan.sff.filters.FiltersFactory;

public class Tester {
	public static void main(String[]args) throws IOException{
		List<Filter> defaultFilters = FiltersFactory.getDefaultFilters();
		/*String charPath = "/home/raslanrauff/smart-form-feeder-extraction/char/";
		String extractedCharPath = "/home/raslanrauff/smart-form-feeder-extraction/extracted_char/";
		
		
		
		ImageFinder finder = new ImageFinder();
		Files.walkFileTree(Paths.get(charPath), finder);
		
		List<Path> charImagePaths = finder.getImages();
		
		int index = 0;
		for(Path p : charImagePaths){
			BufferedImage img = ImageIO.read(p.toFile());
			
			for(Filter f : defaultFilters){
				img = f.applyFilter(img);
			}
			
			String fileOut = extractedCharPath+ ++index + ".png";
			System.out.println("[Extracted] " + fileOut);
			ImageHelper.writeImageToFile(img, "png", fileOut);
		}*/
		
		String basePath = "/media/raslanrauff/HP My Files/BSC_SE/Final/test_img/papers/pack4/filtered/";
		String inputPart = "tmp/";
		String extractionPath = "/home/raslanrauff/ML/manual_inputs/ex1/";
		
		ImageFinder imgFinder = new ImageFinder();
		Files.walkFileTree(Paths.get(basePath+inputPart), imgFinder);
		List<Path> imgPaths = imgFinder.getImages();
		
		Segmentation segmentation = new Segmentation();
		
		for(Path p : imgPaths){
			System.out.println("[Recognizing] -> " + p.toString());
			File mainFile = p.toFile();
			BufferedImage mainImage = ImageIO.read(mainFile);
			String mainChar = removeFileType(mainFile.getName());
			
			List<BufferedImage> chars = segmentation.segment(mainImage);
			int pos = 0;
			for(BufferedImage simg : chars){
				String fileName = mainChar + ++pos + "B.png";
				
				for(Filter f : defaultFilters){
					simg = f.applyFilter(simg);
				}
				String outFileName = extractionPath + fileName;
				
				ImageHelper.writeImageToFile(simg, "png", outFileName);
				
				System.out.println("[Extracted] -> " + outFileName);
			}
		}
	}
	
	static String removeFileType(String fileName){
		return fileName.replaceFirst("[.][^.]+$", "");
	}
}
