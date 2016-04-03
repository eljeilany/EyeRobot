import org.canova.api.records.reader.RecordReader;
import org.canova.api.split.FileSplit;
import org.canova.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.canova.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sam on 16/03/16.
 */
public class ImageLoader {

    private boolean train;

    private String pathName;
    private List<String> label;
    private int heigh;
    private int width;
    private int nChannels;

    private int batchSize;

    private DataSetIterator dataSetIt;

    /**
     * Constructor
     * @param pathName
     * @param HEIGH
     * @param WIDTH
     * @param nbChanels
     * @param batchSize
     * @param train
     */
    public ImageLoader(String pathName, int HEIGH, int WIDTH, int nbChanels, int batchSize, boolean train){
        this.pathName = pathName;
        this.heigh = HEIGH;
        this.width = WIDTH;
        this.nChannels = nbChanels;
        this.batchSize = batchSize;
        this.train = train;
        this.label = new ArrayList<String>();
    }

    /**
     * Make list of different labels
     */
    private void makeLabels(){
        /**File[] f = new File(this.pathName).listFiles();
        for(File file : f) {
            this.label.add(file.getName());
            System.out.println(file.getName());
        }**/
        for(int i=0; i<17; i++){
            this.label.add(Integer.toString(i));
        }
    }

    /**
     * Make DataSetIterator from images
     * @throws Exception
     */
    private void makeDataSetIteratorFromImage() throws Exception{
        if(this.train){
            this.makeLabels();
            RecordReader recordReader = new ImageRecordReader(this.heigh, this.width ,this.nChannels, true, this.label);
            recordReader.initialize(new FileSplit(new File(this.pathName)));
            this.dataSetIt = new RecordReaderDataSetIterator(recordReader,this.batchSize, this.heigh * this.width * this.nChannels, this.label.size());
        }else{
            RecordReader recordReader = new ImageRecordReader(this.heigh, this.width ,this.nChannels, false);
            recordReader.initialize(new FileSplit(new File(this.pathName)));
            this.dataSetIt = new RecordReaderDataSetIterator(recordReader, this.batchSize);
        }
    }

    /**
     * @return DataSetIterator
     * @throws Exception
     */
    public DataSetIterator getData() throws Exception{
        this.makeDataSetIteratorFromImage();
        return this.dataSetIt;
    }
}
