import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;

/**
 * Created by sam on 21/03/16.
 */
public class CSV2JPG {

    private String fileName;
    private String pathToCreate;

    private int heigh;
    private int width;

    private int numLabels;
    private int indexLabels;

    /**
     * Constructor
     * @param fileName
     * @param pathToCreate
     * @param width
     * @param heigh
     * @param nbLabels
     * @param indexLabels
     */
    public CSV2JPG (String fileName, String pathToCreate, int width, int heigh, int nbLabels, int indexLabels){
        this.fileName = fileName;
        this.pathToCreate = pathToCreate;
        this.heigh = heigh;
        this.width = width;
        this.numLabels = nbLabels;
        this.indexLabels = indexLabels;
    }

    /**
     * Make directories for Data
     */
    private void mkdir(){
        new File(this.pathToCreate).mkdir();
        for(int i=0; i<this.numLabels; i++){
            new File(this.pathToCreate + "/" + i).mkdir();
        }
    }

    /**
     * Make a name of lenght 4
     * @param i
     * @return String id for an image
     */
    private String nom(int i){
        String nom = Integer.toString(i);
        if(nom.length()==1){
            return "000"+nom;
        }else if(nom.length()==2){
            return "00"+nom;
        }else if(nom.length()==3){
            return "0"+nom;
        }else{
            return nom;
        }
    }

    /**
     * Make a vectorizing image
     * @param image
     * @return int[] that contains the image
     */
    private int[] image(String[] image){
        int[] result = new int[this.heigh * this.width];
        for(int i=0; i<result.length; i++){
            result[i] = new Color(Integer.parseInt(image[i]),Integer.parseInt(image[i+1024]),Integer.parseInt(image[i+2048])).getRGB();
        }
        return result;
    }

    /**
     * Create all images from one label
     * @param files
     * @throws Exception
     */
    private void make_JPG(ArrayList<String[]> files) throws Exception{
        for(int i=0;i<files.size(); i++) {
            BufferedImage out = new BufferedImage(this.heigh, this.width, BufferedImage.TYPE_3BYTE_BGR);
            out.setRGB(0, 0, this.heigh, this.width, this.image(files.get(i)), 0, this.width);
            File Output_File = new File(this.pathToCreate+"/"+files.get(i)[this.indexLabels] +"/"+ files.get(i)[indexLabels] +"_"+ this.nom(i)+".png");
            ImageIO.write(out, "PNG", Output_File);
            System.out.println(this.pathToCreate+"/"+files.get(i)[this.indexLabels] +"/"+ files.get(i)[this.indexLabels] +"_"+ this.nom(i)+".png");
        }
    }

    /**
     * Create all the images, main method
     * @throws Exception
     */
    public void makeImages() throws Exception{
        this.mkdir();
        for(int i=0; i<this.numLabels ;i++) {
            InputStream ips = new FileInputStream(this.fileName);
            InputStreamReader ipsr=new InputStreamReader(ips);
            BufferedReader tmp = new BufferedReader(ipsr);
            ArrayList<String[]> l = new ArrayList<String[]>();
            String line = tmp.readLine();
            while (line != null) {
                String[] temp = line.split(",");
                if (temp[this.indexLabels].equals(Integer.toString(i))) {
                    l.add(temp);
                }
                line = tmp.readLine();
            }
            tmp.close();
            this.make_JPG(l);
        }
        System.out.println("Finish !");
    }

}
