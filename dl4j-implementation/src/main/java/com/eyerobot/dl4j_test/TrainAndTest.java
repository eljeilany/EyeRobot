package com.eyerobot.dl4j_test;

import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;


public class TrainAndTest {
	
	
	public static void main( String[] args ) throws Exception
    {
		String datasetroot = "/home/eljeilany/dev/dl4j/new/dl4j-test/dataset";
		ImageLoader iml = new ImageLoader(datasetroot,3,30,30) ;

		DataSetIterator dataTrain = iml.getIter(10);
		
		CNEyeRobot Net = new CNEyeRobot(3, 30, 30, 17, 1, 123);
		/*Integer i=1;
		while(dataTrain.hasNext()){
            DataSet next = dataTrain.next();
            System.out.println("Reading  "+i+" = "+next.asList()+" added");
        }*/
		Net.train(dataTrain, 10);
		
    }
	
}
