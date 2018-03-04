package com.raslan.sff.core.nn;

import java.awt.image.BufferedImage;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.learning.LearningRule;

import com.raslan.sff.core.data.BinaryImage;
import com.raslan.sff.core.data.CharacterMapper;
import com.raslan.sff.core.data.InputImage;
import com.raslan.sff.core.data.LetterCharacterMapper;
import com.raslan.sff.core.data.LettersDataSetLoader;
import com.raslan.sff.core.nn.helper.DiagonalCrossFeatureVector;
import com.raslan.sff.core.nn.helper.FeatureVector;
import com.raslan.sff.core.util.Logger;
import com.raslan.sff.filters.FiltersFactory;

public class CharacterRecognizer {
	NeuralNetwork<LearningRule> network;
	Logger logger = Logger.getInstance();
	public CharacterRecognizer(String networkPath){
		network = NeuralNetwork.createFromFile(networkPath);
	}
	
	public void recognizeLetter(BufferedImage image, char letter){
		if(image == null){
			return;
		}
		
		BinaryImage binImage = new BinaryImage(image, FiltersFactory.getDefaultFilters());
		InputImage inputImage = new InputImage(binImage, letter);
		
		CharacterMapper mapper = new LetterCharacterMapper();
		
		int index = mapper.mapCharacter(letter);
		if(index == -1) return;
		
		FeatureVector featureVector = new DiagonalCrossFeatureVector(inputImage, mapper);
		network.setInput(featureVector.getInputs());
		network.calculate();
		
		double[] networkOutput = network.getOutput();
		HashMap<Character, Double> results = new HashMap<Character, Double>();
		for(int i = 0; i < networkOutput.length; i++){
			results.put(mapper.mapInteger(i), networkOutput[i]);
		}
		
		//Comparator<Character> comparator = new ValueComparator(results);
		TreeMap<Character, Double> sortResults = new TreeMap<Character, Double>();
		sortResults.putAll(results);
		
		int counter = 0;
        for (Map.Entry<Character, Double> entry : sortResults.entrySet()) {
            if (entry.getValue() <= 0.02 && counter > 9) break;
            logger.log("CharacterRecognizer",entry.getKey() + " => " + Math.round(entry.getValue() * 1000)/1000.0);
            counter++;
        }
	}
}
