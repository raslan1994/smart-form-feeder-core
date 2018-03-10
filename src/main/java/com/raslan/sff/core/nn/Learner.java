package com.raslan.sff.core.nn;

import java.util.ArrayList;
import java.util.List;

import org.neuroph.core.data.DataSet;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.neuroph.util.TransferFunctionType;

import com.raslan.sff.core.Config;
import com.raslan.sff.core.util.Logger;

public class Learner {
	private Logger logger = Logger.getInstance();
	private double learningRate;
	private double momentum;
	
	private DataSet testDs;
	private DataSet trainDs;
	private MultiLayerPerceptron network;
	
	private List<NetTaskListener> listeners;
	
	public Learner(DataSet trainDs, DataSet testDs, double learningRate, double momentum){
		this.trainDs = trainDs;
		this.testDs = testDs;
		this.learningRate = learningRate;
		this.momentum = momentum;
		this.listeners = new ArrayList<NetTaskListener>();
		
	}
	
	public void startLearning(){
		Thread t1 =new Thread(new Runnable() {
			@Override
			public void run() {
				logger.info("Learner", "Training dataset loaded.");
				logger.info("Learner", String.format("Input size: %d, Determined output size: %d", trainDs.getInputSize(),trainDs.getOutputSize()));
				network = new MultiLayerPerceptron(TransferFunctionType.SIGMOID,trainDs.getInputSize(),86,86,trainDs.getOutputSize());
				
				MomentumBackpropagation backpropagation = new MomentumBackpropagation();
				backpropagation.setLearningRate(learningRate);
				backpropagation.setMomentum(momentum);
				
				LearningEventListener evaluator = new LearningTestEvaluator(Config.DEFAULT_NEURAL_NETWORK_NAME, trainDs, testDs);
				backpropagation.addListener(evaluator);
				
				//Event listener for stop learning
				backpropagation.addListener(new LearningEventListener() {
					
					@Override
					public void handleLearningEvent(LearningEvent event) {
						if (event.getEventType() == LearningEvent.Type.LEARNING_STOPPED) {
                            listeners.forEach((l) -> l.learningStopped(Learner.this));
                        }
					}
				});
				network.setLearningRule(backpropagation);
				
				logger.info("Learner", "Learning process begins");
				logger.info("Learner", String.format("Parameters -> Learning rate : %f, Momentum: %f", learningRate,momentum));
				
				//execute in new thread
				network.learnInNewThread(trainDs);
			}
		});
		t1.start();
	}
}
