package backend;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.text.Normalizer;
import java.text.Normalizer.Form;
import java.util.ArrayList;
import java.util.Arrays;

public class CSVRewriter {

	private String filePathIn;
	private String filePathOut;
	private ArrayList<Integer> selectedIndices;

	public CSVRewriter(ArrayList<Integer> selectedIndices, String filePath) {
		filePathIn = filePath;
		filePathOut = createFileOut(filePath);

		this.selectedIndices = selectedIndices;
		readLines();
	}

	public String createFileOut(String in) {
		return in.substring(0, in.lastIndexOf(".")) + "_new.csv";
	}

	private BufferedReader createFileReader() throws FileNotFoundException, UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(filePathIn), "UTF-8"));
	}

	private BufferedWriter createFileWriter() throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(filePathOut),Charset.forName("UTF-8").newEncoder()));
	}

	private void readLines() {
		try {
			BufferedReader read = createFileReader();
			BufferedWriter write = createFileWriter();
		
			StringBuilder sb = new StringBuilder();
			String line = null;
			int lineSize = selectedIndices.size()-1;
			while ((line = read.readLine()) != null) {
				String[] lineArr = line.split(",");

				for (int i = 0; i < lineArr.length; i++) {
					if (selectedIndices.contains(i)) {
						String temp =  Normalizer.normalize(lineArr[i], Form.NFD);
						temp = temp.replaceAll("[^\\p{ASCII}]", "");
						if (i < lineSize)
							sb.append(temp + ",");
						else
							sb.append(temp);
					}
				}
				write.write(sb.toString() + "\n");
				sb.setLength(0);
			}
			read.close();
			write.flush();
			write.close();
			read = null;
			write = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	protected String getGeneratedCSV() {
		return filePathOut;

	}

	public static void main(String[] args) {
		new CSVRewriter(new ArrayList<Integer>(Arrays.asList(1, 2, 3, 4)),
				"C:\\Users\\wooooot\\Downloads\\SPM_TestdatensatzKlein_2017.csv");
	}

}
