package frontend.helper;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class CSVReader {

	private ArrayList<FeatureItem> list;

	public CSVReader(String in) {
		list = new ArrayList<FeatureItem>();
		readHeader(in);
	}
	
	public ArrayList<FeatureItem> getFeatureExampleList(){
		return list;
	}
	
	//TO DO: Malware check
	@SuppressWarnings("resource")
	private void readHeader(String in) {
		try {
			BufferedReader read = new BufferedReader(new FileReader(in));
			String[] headerElements = read.readLine().split(",");
			String[] featureExamples = read.readLine().split(",");
			for (int i = 0; i < headerElements.length; i++) {
				list.add(new FeatureItem());
				list.get(i).setFeature(headerElements[i]);
				list.get(i).setExample(featureExamples[i]);
			}	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
}
