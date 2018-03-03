package com.raslan.sff.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class BinarizationFilter implements Filter {

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        OtsuBinarize binarization = new OtsuBinarize();
        return binarization.applyFilter(sourceImage);
    }

    @Override
    public String getName() {
        return "Binarization";
    }
    
    @Override
    public String toString() {
        return getName();
    }
}
