package com.raslan.sff.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public class ThinningFilter implements Filter {

    @Override
    public String getName() {
        return "Thinning";
    }

    @Override
    public BufferedImage applyFilter(BufferedImage sourceImage) {
        ZhangSuenThinFilter filter = new ZhangSuenThinFilter();
        return filter.processImage(sourceImage);
    }

    @Override
    public String toString() {
        return getName();
    }
}
