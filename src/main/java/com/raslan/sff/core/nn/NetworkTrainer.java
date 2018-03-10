package com.raslan.sff.core.nn;

import java.io.File;

import org.neuroph.core.data.DataSet;

import com.raslan.sff.core.Config;
import com.raslan.sff.core.data.DatasetLoader;
import com.raslan.sff.core.data.LettersDataSetLoader;

public class NetworkTrainer {
	public static void main(String[] args){
		//execute training operation
		DatasetLoader loader = new LettersDataSetLoader();
    	DataSet trainDs = loader.loadDataSet(new File(Config.DATASET_TRAIN_PATH).toPath());
    	DataSet testDs = loader.loadDataSet(new File(Config.DATASET_TEST_PATH).toPath());
    	Learner learner = new Learner(trainDs, testDs, Config.DEFAULT_LEARNING_RATE, Config.DEFAULT_MOMENTUM);
    	learner.startLearning();
	}
}
