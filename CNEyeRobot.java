import java.io.*;
import java.util.Date;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.math3.util.MathArrays;
import org.deeplearning4j.eval.Evaluation;
import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.GradientNormalization;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.Updater;
import org.deeplearning4j.nn.conf.layers.ConvolutionLayer;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.conf.layers.SubsamplingLayer;
import org.deeplearning4j.nn.conf.layers.setup.ConvolutionLayerSetup;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.factory.Nd4j;
import org.nd4j.linalg.lossfunctions.LossFunctions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



public class CNEyeRobot {
	
	private MultiLayerConfiguration conf ;
    private MultiLayerNetwork model ;
    private static final Logger log = LoggerFactory.getLogger(CNEyeRobot.class);
    private int NOutput;
    
	public CNEyeRobot(int NChannels,int Heigh,int Width ,int NOutput, int Iterations,int Seed){
		this.NOutput = NOutput;
		MultiLayerConfiguration.Builder builder = new NeuralNetConfiguration.Builder()
                .seed(Seed)
                .iterations(Iterations)
                .regularization(true).l2(0.0005)
                .learningRate(0.01)
                .weightInit(WeightInit.XAVIER)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(Updater.ADAGRAD).momentum(0.9)
                .list(6)
                .layer(0, new ConvolutionLayer.Builder(5, 5)
                        .nIn(NChannels)
                        .stride(1, 1)
                        .nOut(30)
                        .activation("relu")
                        .build())
                .layer(1, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(3,3)
                        .stride(2,2)
                        .build())
                .layer(2, new ConvolutionLayer.Builder(5, 5)
                        .stride(1, 1)
                        .nOut(50).dropOut(0.1)
                        .activation("relu")
                        .build())
                .layer(3, new SubsamplingLayer.Builder(SubsamplingLayer.PoolingType.MAX)
                        .kernelSize(3,3)
                        .stride(2,2)
                        .build())
                .layer(4, new DenseLayer.Builder().activation("relu")
                        .nOut(500).dropOut(0.3)
                        .build())
                .layer(5, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nOut(NOutput)
                        .activation("softmax")
                        .build())
                .backprop(true).pretrain(false);
        new ConvolutionLayerSetup(builder,Heigh,Width,NChannels);

        this.conf = builder.build();
        this.model = new MultiLayerNetwork(conf);
	}


	public void train(DataSetIterator trainDataSet, int NEpochs) {
		//model.setListeners(new ScoreIterationListener(1));
        System.out.println(new Date().toString());
        for( int i=0; i<NEpochs; i++ ) {
            model.fit(trainDataSet);
            System.out.println("*** Completed epoch {} *** "+ i + " " + new Date().toString());

        }
        this.saveModelAndParameters(model, "/home/samuel/Documents/model12epochs");
		
	}
	
	public void saveModelAndParameters(MultiLayerNetwork net, String basePath) {
        String confPath = FilenameUtils.concat(basePath, net.toString()+"-conf.json");
        String paramPath = FilenameUtils.concat(basePath, net.toString() + ".bin");
        log.info("Saving model and parameters to {} and {} ...",  confPath, paramPath);

        // save parameters
        try {
            DataOutputStream dos = new DataOutputStream(new FileOutputStream(paramPath));
            Nd4j.write(net.params(), dos);
            dos.flush();
            dos.close();

            // save model configuration
            FileUtils.write(new File(confPath), net.conf().toJson());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static MultiLayerNetwork loadModelAndParameters(File confPath, String paramPath) {
        log.info("Loading saved model and parameters...");
        MultiLayerNetwork savedNetwork = null;
        // load parameters
        try {
            MultiLayerConfiguration confFromJson = MultiLayerConfiguration.fromJson(FileUtils.readFileToString(confPath));
            DataInputStream dis = new DataInputStream(new FileInputStream(paramPath));
            INDArray newParams = Nd4j.read(dis);
            dis.close();

            // load model configuration
            savedNetwork = new MultiLayerNetwork(confFromJson);
            savedNetwork.init();
            savedNetwork.setParams(newParams);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return savedNetwork;
    }


	public void evaluate(DataSetIterator testDataSet) {
		// TODO Auto-generated method stub
        System.out.println("Début eval " + new Date().toString());
		Evaluation eval = new Evaluation(NOutput);
        while(testDataSet.hasNext()){
            DataSet ds = testDataSet.next();
            INDArray output = model.output(ds.getFeatureMatrix());
            eval.eval(ds.getLabels(), output);
        }
        log.info(eval.stats());
	}

    public void predict(DataSetIterator testDataSet, String fileName) throws Exception{
        FileWriter predict = new FileWriter(fileName);
        int i=0;
        while(testDataSet.hasNext()){
            DataSet ds = testDataSet.next();
            int[] result = this.model.predict(ds.getFeatureMatrix());
            for(int j=0 ; j<result.length; j++){
                predict.write(Integer.toString(result[j])+"\n");
            }
            i += result.length;
            System.out.println(i);
        }
        predict.close();
        System.out.println(fileName+" Created");
    }

	public INDArray feedforwad(DataSet ds) {
		// TODO Auto-generated method stub
		return model.output(ds.getFeatureMatrix());
	}
	
}
