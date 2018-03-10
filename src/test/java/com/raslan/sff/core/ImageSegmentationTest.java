package com.raslan.sff.core;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

public class ImageSegmentationTest extends TestCase{
	/**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public ImageSegmentationTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite(ImageSegmentationTest.class);
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }
}
