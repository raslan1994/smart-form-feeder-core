package com.raslan.sff.core.data.forms;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raslan.sff.core.Config;
import com.raslan.sff.core.util.JsonFinder;
import com.raslan.sff.core.util.Logger;

public class FormLayoutManager {
	private static final FormLayoutManager singletonObj = new FormLayoutManager();
	public static final FormLayoutManager getInstance(){return singletonObj;}
	ObjectMapper mapper = new ObjectMapper();
	Logger logger = Logger.getInstance();
	List<FormLayout> layouts = new ArrayList<FormLayout>();
	
	private FormLayoutManager(){
		
	}
	
	public List<FormLayout> getAvailableLayouts(){
		return layouts;
	}
	
	public FormLayout getFormLayout(String filePath){
		File file = new File(filePath);
		FormLayout fl = null;
		try {
			fl = mapper.readValue(file, FormLayout.class);
			
			for(List<FormField> p : fl.getPages()){
				for(FormField f: p){
					logger.log("Mapper", mapper.writeValueAsString(f));
				}
			}
		} catch (Exception e) {
			logger.error("Mapper - getFormLayout", e.toString());
		}
		
		return fl;
	}
	
	public void loadConfigs(){
		JsonFinder jf = new JsonFinder();
		try {
			Files.walkFileTree(new File(Config.FORM_LAYOUT_CONFIGURATION_BASE_PATH).toPath(), jf);
			logger.log("FormLayoutManager", "No of Configuration files loaded : " + jf.getJsonFiles().size());
			
			for(Path p : jf.getJsonFiles()){
				File file = p.toFile();
				FormLayout fl = mapper.readValue(file, FormLayout.class);
				layouts.add(fl);
			}
		} catch (IOException e) {
			logger.error("FormLayoutManager", e.toString());
		}
	}
	
	public BufferedImage cropField(BufferedImage img, FormField field){
		return img.getSubimage(field.getX(), field.getY(), field.getWidth(), field.getHeight());
	}
	
	public FormFieldWithImage extendFormField(FormField field, BufferedImage img){
		FormFieldWithImage f = new FormFieldWithImage();
		f.setField(field.getField());
		f.setX(field.getX());
		f.setY(field.getY());
		f.setWidth(field.getWidth());
		f.setHeight(field.getHeight());
		f.setColor(field.getColor());
		f.setImg(img);
		
		return f;
	}
	
	public List<FormFieldWithImage> convertToFieldList(FormLayout formLayout, List<BufferedImage> pages){
		List<FormFieldWithImage> fieldSet = new LinkedList<FormFieldWithImage>();
		
		int curPage = 0;
		
		for(List<FormField> page : formLayout.getPages()){
			BufferedImage curPageImg = pages.get(curPage);
			for(FormField field : page){
				FormFieldWithImage extendedField = extendFormField(field, cropField(curPageImg, field));
				fieldSet.add(extendedField);
			}
			curPage++;
		}
		
		return fieldSet;
	}
}
