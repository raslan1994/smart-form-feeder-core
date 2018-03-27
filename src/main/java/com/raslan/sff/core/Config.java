package com.raslan.sff.core;

public class Config {
	public static final int SERVER_PORT = 8081;
	
	public static final String FORM_LAYOUT_CONFIGURATION_BASE_PATH = "/home/raslanrauff/smart-form-feeder/";
	
	public static final int THRESHOLD = 100;
	public static final String DATASET_TEST_PATH = "/home/raslanrauff/ML/Complete-English/test/";
	public static final String DATASET_TRAIN_PATH = "/home/raslanrauff/ML/Complete-English/train/";
	public static final String DEFAULT_NEURAL_NETWORK_NAME = "englishHandPickedDataSetV3.nnet";
	public static final double DEFAULT_LEARNING_RATE = 0.01d;
	public static final double DEFAULT_MOMENTUM = 0.025d;
	
	/* Test Image Extraction */
	public static final boolean IS_TEST_EXTRACTION_ENABLED = true;
	public static final String TEST_EXTRACTION_PATH = "/home/raslanrauff/smart-form-feeder-extraction/test_extraction/";
	public static int TEST_EXTRACTION_COUNTER = 1;
	//public static final double CONFIDENT_LEVEL = 0.5;
	
	/* Client Configuration */
	public static final String CLIENT_HOST = "http://localhost/smart-form-feeder";
	public static final String CLIENT_CROSS_ORIGIN = "http://localhost";
}
