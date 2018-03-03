package com.raslan.sff.filters;

import java.awt.image.BufferedImage;

/**
 * @author Filip Gulan
 */
public interface Filter {

    String getName();
    BufferedImage applyFilter(BufferedImage sourceImage);
}
