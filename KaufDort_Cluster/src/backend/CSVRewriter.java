package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class CSVRewriter {
	
	private String filePathIn;
	private String filePathOut;
	private ArrayList<Integer> selectedIndices;
	
	public CSVRewriter(ArrayList<Integer> selectedIndices,String filePath){
		filePathIn = filePath;
		filePathOut = createFileOut();
		this.selectedIndices = selectedIndices;
		readLines();
	}
	
	private String createFileOut(){
		return filePathIn.substring(0, filePathIn.lastIndexOf("\\."))+"_re.csv";
	}
	
	private BufferedReader createFileReader() throws FileNotFoundException{
		return new BufferedReader(new FileReader(filePathIn));	
	}
	
	private BufferedWriter createFileWriter() throws IOException {
		return new BufferedWriter(new FileWriter(filePathOut));
	}
	
	private void readLines(){
		try{
			BufferedReader read = createFileReader();
			BufferedWriter write = createFileWriter();
			StringBuilder sb =  new StringBuilder();
			String line = null;
			while((line = read.readLine())!= null){
				String[] lineArr = line.split(",");
				
				for (int i = 0; i < lineArr.length; i++) {
					if(selectedIndices.contains(i)) sb.append(lineArr[i]+",");
				}
				write.write(sb.toString()+"\n");
				sb.setLength(0);
			}
			read.close();
			write.flush();
			write.close();
			read = null;
			write = null;
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	protected String getGeneratedCSV(){
		return filePathOut;
		
	}
	
	

}
