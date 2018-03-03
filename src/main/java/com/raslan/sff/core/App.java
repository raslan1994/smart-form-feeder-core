package com.raslan.sff.core;

import java.io.File;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Iterator;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;

import com.raslan.sff.core.data.DatasetLoader;
import com.raslan.sff.core.data.LettersDataSetLoader;
import com.raslan.sff.core.util.Logger;

/**
 * Primary execution class
 *
 */
public class App 
{
    public static void main( String[] args ) throws URISyntaxException
    {
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
    	Logger logger = Logger.getInstance();
    	logger.info("App", "Ends :)");
    	
		
    }
}
