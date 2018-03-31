package com.raslan.sff.core; 

import java.awt.image.BufferedImage;
import java.util.List;

import com.raslan.sff.core.data.forms.FormFieldWithImage;

public class TestExtraction {
	private static final TestExtraction singletonObj = new TestExtraction();
	public static TestExtraction getInstancce(){
		return singletonObj;
	}
	private TestExtraction(){
		
	}
	
	public void extractPages(List<BufferedImage> pages){
		int pagePos = 0;
		for(BufferedImage page : pages){
			String pageExtractionPath = Config.TEST_EXTRACTION_PATH + "pages/"; 
			ImageHelper.writeImageToFile(page, "png", pageExtractionPath + "page_" + pagePos++ + ".png");
		}
	}
	
	public void extractFields(List<FormFieldWithImage> fieldSet){
		int fieldPos = 0;
		for(FormFieldWithImage field : fieldSet){
			String fieldExtractionPath = Config.TEST_EXTRACTION_PATH + "fields/"+"field_"+ ++fieldPos + "/";
			ImageHelper.writeImageToFile(field.getImg(), "png", fieldExtractionPath+"raw.png");
		}
	}
	
	public void extractCurrentChar(BufferedImage img, boolean isFiltered){
		extractFieldChars(Config.TEST_EXTRACTION_FIELD_POS, Config.TEST_EXTRACTION_CHAR_POS, isFiltered, img);
	}
	
	public void extractFieldChars(int fieldPos, int charPos, boolean isFiltered, BufferedImage charImg){
		String dirPath = isFiltered ? "after_filter/" : "before_filter/";
		String fieldCharExtractionPath = Config.TEST_EXTRACTION_PATH + "fields/" +"field_"+ (fieldPos+1) + "/" + dirPath;
		ImageHelper.writeImageToFile(charImg, "png", fieldCharExtractionPath + "char_"+charPos+".png");
	}
}
