package com.raslan.sff.filters;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Filip Gulan
 */
public class FiltersFactory {

    public static List<Filter> filters;

    public static List<Filter> getAvailableFilters() {
        filters = new ArrayList<>();
        filters.add(new GrayscaleFilter());
        filters.add(new GrayscaleUnifromFilter());
        filters.add(new GrayscaleBT601Filter());
        filters.add(new BinarizationFilter());
        filters.add(new CropFilter());
        filters.add(new DilationFilter());
        filters.add(new ThinningFilter());
        filters.add(new ResizeFilter(30));
        filters.add(new ResizeFilter(60));
        filters.add(new ResizeFilter(45));
        filters.add(new BilinearScaleFilter(45, 45));
        filters.add(new NearestNeighborScaleFilter(200, 200));
        filters.add(new NearestNeighborScaleFilter(30, 30));
        return filters;
    }

    public static List<Filter> getDefaultFilters() {
        filters = new ArrayList<>();
        filters.add(new GrayscaleFilter());
        filters.add(new BinarizationFilter());
        filters.add(new CropFilter());
        filters.add(new ResizeFilter(30));
        filters.add(new ThinningFilter());
        return filters;
    }
}
