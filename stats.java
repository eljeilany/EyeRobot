import java.awt.Color;
import java.util.ArrayList;

import javax.swing.JFrame;

import org.math.plot.Plot2DPanel;

public class stats {

    private int nbEpoch;
    private int nbClass;
    private int indexL;
    private ArrayList<String> labels;
    private ArrayList<String>[] labelsPredict;


    public stats(int nbEpoch,int nbClass){ //constructeur
    	this.nbEpoch = nbEpoch;
    	this.nbClass = nbClass;
    	this.indexL = 0;
		this.labels = new ArrayList<String>();
		this.labelsPredict= new ArrayList[nbEpoch];
    }
    public void addPretictLabels(ArrayList<String> labelsPredict){
    	 this.labelsPredict[this.indexL] = labelsPredict;
    	 this.indexL++;
    }
    public void addPretictLabels(ArrayList<String> labelsPredict, int index){
    	this.labelsPredict[index] = labelsPredict;
    	// 	ajouter les labels predis avec l'index
    }
    public void addLabels(ArrayList<String> labels) {
    	this.labels=labels;
    	//ajouter les labels
    }
    private double[] wrongPrediction(){
    	double[] res = new double[this.nbEpoch];
    	for(int i=0; i<res.length;i++){
    		int err=0;
    		for(int j=0;j<this.labels.size();j++){
    			if(this.labels.get(j) != this.labelsPredict[i].get(j)){
    				err++;
    			}
    		}
    		res[i]=err;
    	}
    	return res;
    }
    private double[] rightPrediction(){

    	double[] res = new double[this.nbEpoch];
    	for(int i=0; i<res.length;i++){
    		int valide=0;
    		for(int j=0;j<this.labels.size();j++){
    			if(this.labels.get(j) == this.labelsPredict[i].get(j)){
    				valide++;
    			}
    		}
    		res[i]=valide;
    	}
    	return res;
    }
    private double[] precisionRight(){
    	double[] res = new double[this.nbEpoch];
    	for(int i=0; i<res.length;i++){
    		res[i]=rightPrediction()[i]/this.labels.size();
    	}
    	return res;
    }
    private double[] precisionWrong(){
    	double[] res = new double[this.nbEpoch];
    	for(int i=0; i<res.length;i++){
    		res[i]=wrongPrediction()[i]/this.labels.size();
    	}
    	return res;
    }
    private int[][] rightPredictionClass(String classe){

    	int[][] res = new int[this.nbEpoch][this.nbClass];

    	for(int i=0; i<res.length;i++){
    		int[] valide=new int[this.nbClass];

    		for(int j=0;j<this.labels.size();j++){

    			if(this.labels.get(j) == this.labelsPredict[i].get(j)){
    				valide[Integer.parseInt(labels.get(j))]++;
    			}
    		}
    		res[i]=valide;
    	}
    	// precision sur une class
    	return res;
    }
    private int[][] precisionClass(String classe){
    	int[][] res = new int[this.nbEpoch][this.nbClass];
    	for(int i=0; i<res.length;i++){
    		for(int j=0; j<res[0].length;j++){
    			res[i][j]=res[i][j]/nbElemClass(Integer.toString(j));
    		}
    	}
    	return res;
    }
    private int nbElemClass(String classe){
    	int res=0;
    	for(int i=0;i<this.labels.size();i++){
    		if(this.labels.get(i)==classe){
    			res++;
    		}
    	}
    	return res;
    }
    public void graph(String graphName){
    	double[] ii = new double[this.nbEpoch];
    	for(int i=0;i<ii.length;i++){
    		ii[i]=i;
    	}
    	double[] precision = precisionRight();
    	Plot2DPanel plot2D = new Plot2DPanel();

		if(graphName.equals("precision") || graphName.equals("Precision")){
			plot2D.addLinePlot("Precision", new Color(255,0,0), ii,precision);
		}


		JFrame frame = new JFrame("Plot");
    	frame.setSize(600, 600);
    	frame.setContentPane(plot2D);
    	frame.setVisible(true);
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    	// crée un graph selon la demande
    }
}
