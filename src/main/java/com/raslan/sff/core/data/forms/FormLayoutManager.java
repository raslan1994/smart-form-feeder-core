package com.raslan.sff.core.data.forms;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
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
}
