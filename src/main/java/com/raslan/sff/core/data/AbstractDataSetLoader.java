package com.raslan.sff.core.data;

import java.awt.image.BufferedImage;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import org.neuroph.core.data.DataSetRow;

import com.raslan.sff.core.nn.helper.DiagonalFeatureVector;
import com.raslan.sff.core.nn.helper.FeatureVector;
import com.raslan.sff.core.util.ImageFinder;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.filters.FiltersFactory;

public abstract class AbstractDataSetLoader {
	protected Logger logger = Logger.getInstance();
	protected boolean letterMode;
	
	public AbstractDataSetLoader(boolean isLetter){
		this.letterMode = isLetter;
	}
	
	protected List<InputImage> loadInputImages(Path inputPath) {
        ImageFinder finder = new ImageFinder();
        List<InputImage> inputImages = new ArrayList<InputImage>();

        try {
            Files.walkFileTree(inputPath, finder);
            List<Path> images = finder.getImages();
            for (Path path : images) {
                char name = path.getFileName().toString().charAt(0);
                BufferedImage image = ImageIO.read(path.toFile());
                BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
                inputImages.add(new InputImage(binImage, name));
            }
        } catch (Exception e) {
            logger.error("AbstractDataSetLoader","Unable to load images from given url");
        }
        return inputImages;
    }
	
	protected DataSetRow getDataSetRow(InputImage image, CharacterMapper mapper) {
        int index = mapper.mapCharacter(image.getSymbol());
        if (index == -1) {
            return null;
        }
        FeatureVector vector = new DiagonalFeatureVector(image,mapper);
        double[] output = new double[mapper.getSize()];
        output[index] = 1.0;
        return new DataSetRow(vector.getInputs(), output);
    }

}
