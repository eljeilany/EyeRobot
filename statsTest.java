
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

/**
 * Created by Morgane on 01/04/2016.
 */
public class statsTest extends TestCase {
    public static void main(String[] args) throws Exception {
/*        String path = "D:\\Antoine\\Projet_Eye\\Fichier_Source\\local_train.csv";
        File f = new File(path);
        BufferedReader read = new BufferedReader(new FileReader("D:\\Antoine\\Projet_Eye\\Fichier_Source\\local_train.csv"));
        ArrayList<String> labels = new ArrayList<String>();
        for (int i=0; i<30000;i++){

            //System.out.println(read.readLine().split(",")[3072]);
            labels.add(read.readLine().split(",")[3072]);
        }

        stats o = new stats(30,17);
        o.addLabels(labels);
        ArrayList<String> labelsP;
        for(int i=0;i<30;i++){
            labelsP = new ArrayList<String>();
            for(int j=0;j<30000;j++){
                String rand = Integer.toString((int)(Math.random()*16));
                labelsP.add(rand);
                System.out.println(rand);

            }
            System.out.println("P : "+i);
            o.addPretictLabels(labelsP);
        }
        o.graph("Precision");*/

        ArrayList<String> labels = new ArrayList<String>();
        labels.add("1");
        labels.add("0");
        labels.add("0");
        labels.add("1");

        ArrayList<String> labelsP1 = new ArrayList<String>();
        labelsP1.add("0");
        labelsP1.add("0");
        labelsP1.add("0");
        labelsP1.add("0");

        ArrayList<String> labelsP2 = new ArrayList<String>();
        labelsP2.add("1");
        labelsP2.add("0");
        labelsP2.add("0");
        labelsP2.add("0");

        ArrayList<String> labelsP3 = new ArrayList<String>();
        labelsP3.add("1");
        labelsP3.add("0");
        labelsP3.add("0");
        labelsP3.add("1");

        stats o = new stats(3,2);
        o.addLabels(labels);
        o.addPretictLabels(labelsP1);
        o.addPretictLabels(labelsP2);
        o.addPretictLabels(labelsP3);

        o.graph("Precision");
    }
}
