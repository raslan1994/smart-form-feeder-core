package com.raslan.sff.core.data;

import java.awt.image.BufferedImage;
import java.util.List;

import com.raslan.sff.core.Config;
import com.raslan.sff.core.ImageHelper;
import com.raslan.sff.core.TestExtraction;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.filters.Filter;
import com.raslan.sff.filters.ImageUtilities;

public class BinaryImage {
	Logger logger = Logger.getInstance();
	private int[][] pixels;
	private BufferedImage img;
	
	public BinaryImage(BufferedImage img){
		
	}
	
	public BinaryImage(BufferedImage img, List<Filter> filters){
		BufferedImage filteredImage = img;
		//Applying required filters
        if (filters != null) {
            for (Filter filter : filters) {
                filteredImage = filter.applyFilter(filteredImage);
            }
        }
        int width = filteredImage.getWidth();
        int height = filteredImage.getHeight();
        this.img = filteredImage;
        
        //Test extraction
        if(Config.IS_TEST_EXTRACTION_ENABLED){
        	TestExtraction.getInstancce().extractCurrentChar(filteredImage, true);
        }
        
        
        this.pixels = new int[width][height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                int pixel = ImageUtilities.getRed(filteredImage.getRGB(j, i));
                if (pixel != 255 && pixel != 0) {
                    throw new BinaryImageException("Given image is not binary image!");
                }
                this.pixels[i][j] = 1 - pixel/255;
            }
        }
	}
	
	public BufferedImage getImageRepresentation() {
        if (img == null) {
            int width = pixels.length;
            int height = pixels[0].length;
            img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    int pixel = pixels[i][j] == 1 ? 0 : 255;
                    int rgb = ImageUtilities.colorToRGB(1, pixel, pixel, pixel);
                    img.setRGB(i, j, rgb);
                }
            }
        }
        return img;
    }
    
    /*public String dumpToJBIC() {
        StringBuilder builder = new StringBuilder();
        int width = pixels.length;
        int height = pixels[0].length;
        builder.append("w " + width + "\n");
        builder.append("h " + height + "\n");
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (pixels[i][j] == 0) {
                    builder.append("p " + i + " " + j + "\n");
                }
            }
        }
        return builder.toString();
    }*/

    public double[] getVector() {
        int width = pixels.length;
        int height = pixels[0].length;
        double[] vector = new double[width*height];
        for (int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                vector[i*width + j] = pixels[i][j];
            }
        }
        return vector;
    }

    public int[][] getMatrix() {
        return pixels;
    }

    public int getWidth() {
        return pixels.length;
    }

    public int getHeight() {
        return pixels[0].length;
    }
}
