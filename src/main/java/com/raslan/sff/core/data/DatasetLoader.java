package com.raslan.sff.core.data;

import java.nio.file.Path;

import org.neuroph.core.data.DataSet;

public interface DatasetLoader {
	public DataSet loadDataSet(Path dataSetPath);
}
