package com.raslan.sff.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.raslan.sff.core.Config;
import com.raslan.sff.core.ImageHelper;
import com.raslan.sff.core.cs.Segmentation;
import com.raslan.sff.core.data.forms.FormFieldWithImage;
import com.raslan.sff.core.data.forms.FormLayout;
import com.raslan.sff.core.data.forms.FormLayoutManager;
import com.raslan.sff.core.nn.CharacterRecognizer;
import com.raslan.sff.core.nn.RecognitionResult;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.filters.Filter;
import com.raslan.sff.filters.FiltersFactory;

@MultipartConfig(location="/tmp/upload", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*50)
public class FormFeederService extends HttpServlet{
	static ObjectMapper mapper = new ObjectMapper();
	static FormLayoutManager layoutManager = FormLayoutManager.getInstance();
	static Segmentation segmentation = new Segmentation();
	static List<Filter> defaultFilters = FiltersFactory.getDefaultFilters();
	static CharacterRecognizer characterRecognizer = new CharacterRecognizer(Config.DEFAULT_NEURAL_NETWORK_NAME);
	
	static String extractionBasePath = "/home/raslanrauff/smart-form-feeder-extraction/";
	Logger logger = Logger.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeServiceHeader(resp);
		String strLayoutIndex = req.getParameter("li");
		
		if(strLayoutIndex != null && !strLayoutIndex.equals("")){
			int layoutIndex = Integer.valueOf(strLayoutIndex);
			processInput(req.getParts(), layoutIndex,resp.getWriter());
		}
	}
	
	private void processInput(Collection<Part> inputParts, int layoutindex, Writer out){
		if(inputParts.size() > 0){
			List<BufferedImage> pages = new ArrayList<BufferedImage>();
			FormLayout formLayout= layoutManager.getAvailableLayouts().get(layoutindex);
			
			int pos = 0;
			for(Part part : inputParts){
				Image bimg;
				try {
					bimg = ImageIO.read(part.getInputStream());
					BufferedImage img = new BufferedImage(formLayout.getImageWidth(),formLayout.getImageHeight(),BufferedImage.TYPE_INT_RGB);
					Graphics2D g = img.createGraphics();
					g.drawImage(bimg, 0, 0, formLayout.getImageWidth(),formLayout.getImageHeight(),null);
					g.dispose();
					
					pages.add(img);
				} catch (IOException e) {
					logger.log("FormFeederService", e.toString());
				}
			}
			
			//convert to list
			List<FormFieldWithImage> extendedFieldSet = layoutManager.convertToFieldList(formLayout, pages);
			
			recognizeText(extendedFieldSet);
			try {
				out.write(mapper.writeValueAsString(extendedFieldSet));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			/*int loc = 0;
			for(FormFieldWithImage f : extendedFieldSet){
				try {
					ImageIO.write(f.getImg(), "png", new File(basePath+"img_"+loc+++".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}*/
		}
	}
	
	private void recognizeText(List<FormFieldWithImage> fieldSet){
		int fieldPos = 0;
		for(FormFieldWithImage field: fieldSet){
			//character segmentation
			List<BufferedImage> chars = segmentation.segment(field.getImg());
			String value = "";
			int charPos = 0;
			for(BufferedImage c : chars){
				//temp
				ImageHelper.writeImageToFile(c, "png", extractionBasePath + "char/bf"+fieldPos+"_"+charPos+".png");
				RecognitionResult result = characterRecognizer.recognieChar(c);
				
				if(result.getBestFitCharacter() != null){
					value += result.getBestFitCharacter().toString();
				}
				charPos++;
			}
			logger.log("FormFeederService","Recognized Text is " + value);
			fieldPos++;
			field.setValue(value);
			
			//clear image
			field.setImg(null);
		}
	}
}
