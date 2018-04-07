package com.raslan.sff.filters;

import java.awt.image.BufferedImage;

public interface Filter {

    String getName();
    BufferedImage applyFilter(BufferedImage sourceImage);
}
