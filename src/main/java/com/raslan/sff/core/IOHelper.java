package com.raslan.sff.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;

public class IOHelper {
	private static IOHelper singleton = new IOHelper();
	public static IOHelper getInstance(){
		return singleton;
	}
	
	public BufferedImage getBufferedImage(String filePath) throws URISyntaxException, IOException{
		File f = new File(getClass().getClassLoader().getResource(filePath).toURI());
		return ImageIO.read(f);
	}
}
