package com.raslan.sff.core;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;

import javax.imageio.ImageIO;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import com.raslan.sff.core.data.BinaryImage;
import com.raslan.sff.core.data.CharacterMapper;
import com.raslan.sff.core.data.DatasetLoader;
import com.raslan.sff.core.data.InputImage;
import com.raslan.sff.core.data.LetterCharacterMapper;
import com.raslan.sff.core.data.LettersDataSetLoader;
import com.raslan.sff.core.nn.CharacterRecognizer;
import com.raslan.sff.core.nn.Learner;
import com.raslan.sff.core.nn.helper.DiagonalFeatureVector;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.ui.TemplatePreview;
import com.raslan.sff.ui.data.FormTemplateManager;

/**
 * Primary execution class
 *
 */
public class App 
{
    public static void main( String[] args ) throws URISyntaxException, IOException
    {
    	/*CharacterMapper cm = new LetterCharacterMapper();
    	BufferedImage img = ImageIO.read(new File("/home/raslanrauff/ML/Dataset/english/new_method/1/A1B.png"));
    	BinaryImage bimg = new BinaryImage(img,null);
    	InputImage iimg = new InputImage(bimg, 'A');*/
    	
    	/*double[] v = bimg.getVector();
    	System.out.println("Vector is");
    	for(double v_ : v){
    		System.out.print(""+Integer.valueOf(String.valueOf(v_).substring(0,1)));
    	}
    	System.out.print("\n");
    	
    	System.out.println("Matrix is ");
    	int[][] ps = bimg.getMatrix();
    	for(int i = 0; i < ps.length; i++){
    		for(int x = 0; x < ps[i].length; x++){
    			System.out.print(ps[i][x]+",");
    		}
    		System.out.print("\n");
    	}
    	System.out.print("\n");
    	
    	DiagonalFeatureVector dfv = new DiagonalFeatureVector(iimg, cm);
    	double[] out = dfv.getInputs();
    	System.out.println("Output");
    	for(double d: out){
    		System.out.println(d);
    	}
    	System.out.print("\n");*/
    	//IOHelper ioHelper = IOHelper.getInstance();
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
			System.out.print(ds.getLabel());
			System.out.print("\n");
			for(double d: ds.getInput()){
				System.out.print(d);
			}
			System.out.print("\n");
		}
		
		System.out.println(dataset.getInputSize());*/
    	
    	/*CharacterRecognizer cr = new CharacterRecognizer("/home/raslanrauff/ML/Dataset/NeuralNet_English.nnet");
		BufferedImage img = ioHelper.getBufferedImageFromResource("ltr1.png");
		
		cr.recognizeLetter(img, 'l');
		
		
    	Logger logger = Logger.getInstance();
    	logger.info("App", "Ends :)");*/
    	
    	/*FormTemplateManager manager = FormTemplateManager.getInstance();
    	String path = "/media/raslanrauff/HP My Files/BSC_SE/Final/test_img/papers/img.jpg";
    	manager.loadFromFile("/home/raslanrauff/smart-form-feeder/form_template_1.json");
    	
    	TemplatePreview preview = new TemplatePreview(manager);
    	preview.changeImage(path);
    	preview.setVisible(true);*/    	
    	
    }
}
