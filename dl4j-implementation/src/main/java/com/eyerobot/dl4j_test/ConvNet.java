package com.eyerobot.dl4j_test;

import org.deeplearning4j.eval.Evaluation;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public abstract class ConvNet {

	// Train Net

	public abstract void train(DataSetIterator trainDataSet,int NEpochs);

	// Evaluate Net

	public abstract void evaluate(DataSetIterator testDataSet);

	// Feed foward an example and get prediction

	public abstract INDArray feedforwad(DataSet testDataSet);

	}
