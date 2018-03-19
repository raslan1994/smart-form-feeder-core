package com.raslan.sff.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
import com.raslan.sff.core.data.forms.FormFieldWithImage;
import com.raslan.sff.core.data.forms.FormLayout;
import com.raslan.sff.core.data.forms.FormLayoutManager;
import com.raslan.sff.core.util.Logger;

@MultipartConfig(location="/tmp/upload", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*50)
public class FormFeederService extends HttpServlet{
	static ObjectMapper mapper = new ObjectMapper();
	static FormLayoutManager layoutManager = FormLayoutManager.getInstance();
	Logger logger = Logger.getInstance();
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeServiceHeader(resp);
		String strLayoutIndex = req.getParameter("li");
		
		if(strLayoutIndex != null && !strLayoutIndex.equals("")){
			int layoutIndex = Integer.valueOf(strLayoutIndex);
			processInput(req.getParts(), layoutIndex);
		}
	}
	
	private void processInput(Collection<Part> inputParts, int layoutindex){
		String basePath = "/home/raslanrauff/smart-form-feeder-extraction/";
		if(inputParts.size() > 0){
			List<BufferedImage> pages = new ArrayList<BufferedImage>();
			FormLayout formLayout= layoutManager.getAvailableLayouts().get(layoutindex);
			
			int pos = 0;
			for(Part part : inputParts){
				Image bimg;
				try {
					bimg = ImageIO.read(part.getInputStream());
					BufferedImage img = new BufferedImage(formLayout.getImageWidth(),formLayout.getImageHeight(),BufferedImage.TYPE_BYTE_GRAY);
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
			int loc = 0;
			for(FormFieldWithImage f : extendedFieldSet){
				try {
					ImageIO.write(f.getImg(), "png", new File(basePath+"img_"+loc+++".png"));
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
