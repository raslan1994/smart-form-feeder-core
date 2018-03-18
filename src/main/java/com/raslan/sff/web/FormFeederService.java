package com.raslan.sff.web;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
@MultipartConfig(location="/tmp/upload", fileSizeThreshold=1024*1024, maxFileSize=1024*1024*50)
public class FormFeederService extends HttpServlet{
	static ObjectMapper mapper = new ObjectMapper();
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		WebHelper.writeServiceHeader(resp);
		Collection<Part> inputParts = req.getParts();
		String basePath = "/home/raslanrauff/smart-form-feeder-extraction/";
		if(inputParts.size() > 0){
			int pos = 0;
			for(Part part : inputParts){
				Image bimg = ImageIO.read(part.getInputStream());
				BufferedImage img = new BufferedImage(512,512,BufferedImage.TYPE_INT_RGB);
				Graphics2D g = img.createGraphics();
				g.drawImage(bimg, 0, 0, 512,512,null);
				g.dispose();
				ImageIO.write(img, "png", new File(basePath+pos+++".png"));
			}
		}
	}
}
