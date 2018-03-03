package com.raslan.sff.core.data;

import java.nio.file.Path;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import com.raslan.sff.core.util.Logger;

public class LettersDataSetLoader extends AbstractDataSetLoader implements DatasetLoader{
	Logger logger = Logger.getInstance();
	private static final int INPUT_LAYER = 65;
    private static final int OUTPUT_LAYER = 27;

    public LettersDataSetLoader() {
		super(true);
	}
    
    @Override
    public DataSet loadDataSet(Path path) {
        List<InputImage> images = loadInputImages(path);
        DataSet dataSet = new DataSet(INPUT_LAYER, OUTPUT_LAYER);
        CharacterMapper mapper = new LetterCharacterMapper();
        for (InputImage image : images) {
            DataSetRow row = getDataSetRow(image, mapper);
            if (row == null) {
                continue;
            }
            dataSet.addRow(row);
        }
        return dataSet;
    }

    @Override
    public String toString() {
    	return "English";
    }
}
