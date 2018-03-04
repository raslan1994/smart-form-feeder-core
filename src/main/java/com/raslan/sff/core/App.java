package com.raslan.sff.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import com.raslan.sff.core.data.DatasetLoader;
import com.raslan.sff.core.data.LettersDataSetLoader;
import com.raslan.sff.core.nn.CharacterRecognizer;
import com.raslan.sff.core.nn.Learner;
import com.raslan.sff.core.util.Logger;

/**
 * Primary execution class
 *
 */
public class App 
{
    public static void main( String[] args ) throws URISyntaxException, IOException
    {
    	IOHelper ioHelper = IOHelper.getInstance();
    	
    	/*DatasetLoader loader = new LettersDataSetLoader();
    	DataSet trainDs = loader.loadDataSet(new File(Config.DATASET_TRAIN_PATH).toPath());
    	DataSet testDs = loader.loadDataSet(new File(Config.DATASET_TEST_PATH).toPath());
    	Learner learner = new Learner(trainDs, testDs, Config.DEFAULT_LEARNING_RATE, Config.DEFAULT_MOMENTUM, Config.DEFAULT_NEURAL_NETWORK_NAME);
    	learner.startLearning();*/
    	
    	/*String trainPath = "/home/raslanrauff/ML/Dataset/english_bk/in/1";
		DatasetLoader loader = new LettersDataSetLoader();
		DataSet dataset = loader.loadDataSet(new File(trainPath).toPath());
		
		Iterator<DataSetRow> i = dataset.iterator();
		
		while(i.hasNext()){
			DataSetRow ds = i.next();
			System.out.println(ds.getLabel());
			System.out.print("\n");
			for(double d: ds.getInput()){
				System.out.print(d);
			}
			System.out.print("\n");
		}
		
		System.out.println(dataset.getInputSize());*/
    	
    	CharacterRecognizer cr = new CharacterRecognizer("/home/raslanrauff/ML/Dataset/NeuralNet_English.nnet");
		BufferedImage img = ioHelper.getBufferedImageFromResource("ltr.png");
		
		cr.recognizeLetter(img, 'l');
		
		
    	Logger logger = Logger.getInstance();
    	logger.info("App", "Ends :)");
    	
    }
}
