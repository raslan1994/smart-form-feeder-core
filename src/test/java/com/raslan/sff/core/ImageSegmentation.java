package com.raslan.sff.core;

import static org.junit.Assert.*;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.imageio.ImageIO;

import org.junit.Test;

import com.raslan.sff.core.cs.Segmentation;

public class ImageSegmentation {

	@Test
	public void test() {
		String rawImagePath = "/home/raslanrauff/smart-form-feeder-test/raw.png";
		BufferedImage img;
		try {
			img = ImageIO.read(new File(rawImagePath));
			Segmentation segmentation = new Segmentation();
	    	List<BufferedImage> chars = segmentation.segment(img);
	    	int expectedNoOfChars = 6;
	    	assertEquals(expectedNoOfChars, chars.size());
		} catch (IOException e) {
			fail("Error occrured while executing test");
			return;
		}
	}
}
