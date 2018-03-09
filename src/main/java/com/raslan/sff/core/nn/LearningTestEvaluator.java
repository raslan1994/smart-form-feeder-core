package com.raslan.sff.core.nn;

import org.neuroph.core.NeuralNetwork;
import org.neuroph.core.data.DataSet;
import org.neuroph.core.data.DataSetRow;
import org.neuroph.core.events.LearningEvent;
import org.neuroph.core.events.LearningEventListener;
import org.neuroph.core.learning.LearningRule;

import com.raslan.sff.core.util.Logger;

public class LearningTestEvaluator implements LearningEventListener{
	private Logger logger = Logger.getInstance();
	private String networkName;
	private DataSet trainDs;
	private DataSet testDs;
	private int testCounter;
	private double maxRate;
	
	public LearningTestEvaluator(String networkName, DataSet trainDs, DataSet testDs){
		this.networkName = networkName;
		this.trainDs = trainDs;
		this.testDs = testDs;
		this.testCounter = 0;
		this.maxRate = 0;
	}

	@Override
	public void handleLearningEvent(LearningEvent evt) {
		testCounter++;
		if(testCounter < 5) return;
		testCounter = 0;
		
		NeuralNetwork<LearningRule> nnet = ((LearningRule)evt.getSource()).getNeuralNetwork();
		
		int passes = 0;
		
		//Iteration over data-set
		for(DataSetRow row : testDs.getRows()){
			nnet.setInput(row.getInput());
			nnet.calculate();
			
			double[] networkOutput = nnet.getOutput();
			if(isOutputEquals(networkOutput, row.getDesiredOutput())){
				passes++;
			}
		}
		
		double rate = passes/ (double)testDs.size();
		logger.info("LearningTestEvaluation", String.format("Success rate is %f", rate));
		
		if(rate >= maxRate){
			maxRate = rate;
			logger.log("LearningTestEvaluation", "Saving neural network");
			nnet.save(networkName);
		}else{
			passes = 0;
			for(DataSetRow row : trainDs.getRows()){
				nnet.setInput(row.getInput());
				nnet.calculate();
				
				double[] networkOutput = nnet.getOutput();
				if(isOutputEquals(networkOutput, row.getDesiredOutput())){
					passes++;
				}
			}
			double trainingRate = passes / (double) trainDs.size();
			logger.info("LearningTestEvaluation", String.format("Training rate is %f", trainingRate));
		}
	}
	
	private static boolean isOutputEquals(double[] netOutput, double[] desiredOutput) {
        int index = -1;
        for (int i = 0; i < desiredOutput.length; i++) {
            if (desiredOutput[i] > 0.5) {
                index = i;
                break;
            }
        }

        int maxIndex = 0;
        double maxValue = 0;
        for (int i = 0; i < netOutput.length; i++) {
            if (netOutput[i] > maxValue) {
                maxValue = netOutput[i];
                maxIndex = i;
            }
        }
        return maxIndex == index;
    }
}
