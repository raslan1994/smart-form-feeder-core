package com.raslan.sff.core.nn;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TrainingSetImport;
import org.neuroph.util.TransferFunctionType;

import com.raslan.sff.core.data.DatasetLoader;
import com.raslan.sff.core.util.Logger;

public class Learner {
	private Logger logger = Logger.getInstance();
	private double learningRate;
	private double momentum;
	private String networkName;
	
	private DataSet testDs;
	private DataSet trainDs;
	private MultiLayerPerceptron network;
	
	private List<NetTaskListener> listeners;
	
	public Learner(DataSet trainDs, DataSet testDs, double learningRate, double momentum, String networkName){
		this.trainDs = trainDs;
		this.testDs = testDs;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.networkName = networkName;
		this.listeners = new ArrayList<NetTaskListener>();
		
	}
	
	public void startLearning(){
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				logger.info("Learner", "Training dataset loaded.");
				logger.info("Learner", String.format("Input size: %f, Determined output size: %f", trainDs.getInputSize(),trainDs.getOutputSize()));
				network = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,trainDs.getInputSize(),86,86,trainDs.getOutputSize());
				
				MomentumBackpropagation backpropagation = new MomentumBackpropagation();
				backpropagation.setLearningRate(learningRate);
				backpropagation.setMomentum(momentum);
				
				
				
			}
		});
	}
}
