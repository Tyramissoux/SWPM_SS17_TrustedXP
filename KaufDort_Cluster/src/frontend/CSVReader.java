package frontend;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class CSVReader {
	String[] headerElements;
	String[] featureExamples;

	public CSVReader(String in) {
		readHeader(in);
	}
	
	public String[] getHeaderElements(){
		return headerElements;
	}
	public String[] getFeatureExamples(){
		return featureExamples;
	}

	
	//TO DO: Malware check
	@SuppressWarnings("resource")
	private void readHeader(String in) {
		try {
			BufferedReader read = new BufferedReader(new FileReader(in));
			headerElements = read.readLine().split(",");
			featureExamples = read.readLine().split(",");
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
