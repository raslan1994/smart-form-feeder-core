package com.raslan.sff.core.nn;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.imageio.ImageIO;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.LearningRule;

import com.raslan.sff.core.data.BinaryImage;
import com.raslan.sff.core.data.CharacterMapper;
import com.raslan.sff.core.data.InputImage;
import com.raslan.sff.core.data.LetterCharacterMapper;
import com.raslan.sff.core.nn.helper.DiagonalFeatureVector;
import com.raslan.sff.core.nn.helper.FeatureVector;
import com.raslan.sff.core.util.ImageFinder;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.filters.FiltersFactory;

public class CharacterRecognizer {
	NeuralNetwork<LearningRule> network;
	static Logger logger = Logger.getInstance();
	static CharacterMapper mapper = new LetterCharacterMapper();
	
	public CharacterRecognizer(String networkPath){
		network = NeuralNetwork.createFromFile(networkPath);
	}
	
	public RecognitionResult recognieChar(BufferedImage img){
		RecognitionResult result = new RecognitionResult();
		
		if(img==null) return null;
		
		char tmpSymbol = 't';
		BinaryImage bi = new BinaryImage(img, FiltersFactory.getDefaultFilters());
		InputImage inputImage = new InputImage(bi, tmpSymbol);
		
		FeatureVector featureVector = new DiagonalFeatureVector(inputImage, mapper);
		network.setInput(featureVector.getInputs());
		network.calculate();
		
		double[] networkOutput = network.getOutput();
		HashMap<Character, Double> outputResults = new HashMap<Character, Double>();
		for(int i = 0; i < networkOutput.length; i++){
			outputResults.put(mapper.mapInteger(i), networkOutput[i]);
		}
		
		TreeMap<Character, Double> sortResults = new TreeMap<Character, Double>();
		sortResults.putAll(outputResults);
		
		double bestFitWeight = 0.0;
		Character bestFitCharacter = null;
		for(Map.Entry<Character, Double> entry : sortResults.entrySet()){
			if(bestFitWeight <= entry.getValue()){
				bestFitCharacter = entry.getKey();
				bestFitWeight = entry.getValue();
			}
		}
		
		//fetch results
		result.setBestFitCharacter(bestFitCharacter);
		result.setWeight(bestFitWeight);
		
		return result;
	}
	
	public void recognizeLetter(BufferedImage image, char symbol, String filename){		
		
		if(image == null){
			return;
		}
		
		BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
		InputImage inputImage = new InputImage(binImage, symbol);
		
		CharacterMapper mapper = new LetterCharacterMapper();
		
		int index = mapper.mapCharacter(symbol);
		if(index == -1) return;
		
		FeatureVector featureVector = new DiagonalFeatureVector(inputImage, mapper);
		network.setInput(featureVector.getInputs());
		network.calculate();
		
		double[] networkOutput = network.getOutput();
		/*for(double no : networkOutput){
			logger.log("Output",String.valueOf(no));
		}*/
		HashMap<Character, Double> results = new HashMap<Character, Double>();
		for(int i = 0; i < networkOutput.length; i++){
			results.put(mapper.mapInteger(i), networkOutput[i]);
		}
		
		//Comparator<Character> comparator = new ValueComparator(results);
		TreeMap<Character, Double> sortResults = new TreeMap<Character, Double>();
		sortResults.putAll(results);
		
		logger.log("CharacterRecognizer", "[FILE LOADED] -> " + filename);
		logger.log("CharacterRecognizer", "[RECOGNIZING " + filename + "] -----------------------------------------------------");
		
		double bestFitWeight = 0.0;
		Character bestFitCharacter = null;
		for(Map.Entry<Character, Double> entry : sortResults.entrySet()){
			if(bestFitWeight <= entry.getValue()){
				bestFitCharacter = entry.getKey();
				bestFitWeight = entry.getValue();
			}
		}
		
		if(bestFitCharacter!= null){
			logger.log("CharacterRecognizer",filename + "likely to be " + bestFitCharacter + " with weight of " + bestFitWeight);
		}
		
		/*int counter = 0;
        for (Map.Entry<Character, Double> entry : sortResults.entrySet()) {
            if (entry.getValue() <= 0.02 && counter > 28) break;//later can adjust max limit accordingly
            logger.log("CharacterRecognizer",entry.getKey() + " => " + Math.round(entry.getValue() * 1000)/1000.0);
            counter++;
        }*/
	}
	
	public static void main(String[] args){
		String networkPath = "/home/raslanrauff/ML/Dataset/new_trained-network/englishWithLowercase.nnet";
		String imagesBasePath = "/home/raslanrauff/ML/new/filtered/";
		CharacterRecognizer cr = new CharacterRecognizer(networkPath);
		ImageFinder imgFinder = new ImageFinder();
		
		try {
			Files.walkFileTree(new File(imagesBasePath).toPath(), imgFinder);
			List<Path> paths = imgFinder.getImages();
			
			logger.info("CharacterRecognizer", "No of images in the directory ->" + paths.size());
			int i = 0;
			for(Path p : paths){
				BufferedImage img_ = ImageIO.read(p.toFile());
				cr.recognizeLetter(img_, p.getFileName().toString().charAt(0), p.getFileName().toString());//providing a temporary char symbol to maintain the stand
			}
		} catch (IOException e1) {
			logger.error("CharacterRecognizer", e1.toString());
		}
	}
}
