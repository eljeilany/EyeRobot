package com.eyerobot.dl4j_test;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.canova.api.records.reader.RecordReader;
import org.canova.api.split.FileSplit;
import org.canova.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.canova.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;

public class ImageLoader {
	private String ImageFolderPath;
	private int NChannels;
	private int Heigh;
	private int Width;
	ImageLoader(String ImageFolderPath ,int NChannels,int Heigh,int Width){
		this.ImageFolderPath = ImageFolderPath;
		this.NChannels = NChannels;
		this.Heigh = Heigh;
		this.Width = Width;
	}
	public DataSetIterator getIter(int batchSize){
		List<String> labels = new ArrayList<String>();

        //traverse dataset to get each label
        for(File f : new File(this.ImageFolderPath).listFiles())
        {
         labels.add(f.getName());
        }
        RecordReader recordReader = new ImageRecordReader(this.Heigh,this.Width,this.NChannels, true, labels);
        try {
			recordReader.initialize(new FileSplit(new File(this.ImageFolderPath)));
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        //DataSetIterator dataiter = new RecordReaderDataSetIterator(recordReader,batchSize, this.Width*this.NChannels*this.NChannels,labels.size());
		return  new RecordReaderDataSetIterator(recordReader, 30*30*3,labels.size());
	}
}
