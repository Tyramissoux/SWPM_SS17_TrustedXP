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

/**
 * Erzeugt aus einer gegebenen CSV-Datei eine neue CSV-Datei mit nur noch den
 * wünschten Spalten
 * 
 * @author wooooot
 * 
 */
public class CSVRewriter {

	private String filePathIn;
	private String filePathOut;
	private ArrayList<Integer> selectedIndices;

	public CSVRewriter(ArrayList<Integer> selectedIndices, String filePath) {
		this.filePathIn = filePath;
		this.filePathOut = createFileOut(filePath);

		this.selectedIndices = selectedIndices;
		readLines();
	}

	/**
	 * Erzeugt einen neuen String aus einem vollständigen Dateipfad indem es
	 * .csv durch .arff ersetzt
	 * 
	 * @param in
	 *            Als Input dient ein Pfad zu einer CSV-Datei
	 * @return Gibt einen String zurück, der die vorherige Dateiendung ersetzt
	 *         durch .arff
	 */
	public String createFileOut(String in) {
		return in.substring(0, in.lastIndexOf(".")) + "_new.csv";
	}

	/**
	 * Erzeugt einen BufferedReader und öffnet einen als Instanzvariable
	 * gegebenen Dateipfad
	 * 
	 * @return Gibt einen BufferedReader zurück
	 * @throws FileNotFoundException
	 * @throws UnsupportedEncodingException
	 */
	private BufferedReader createFileReader() throws FileNotFoundException,
			UnsupportedEncodingException {
		return new BufferedReader(new InputStreamReader(new FileInputStream(
				filePathIn), "UTF-8"));
	}

	/**
	 * Erzeugt einen BuferedWriter, der eine gegebene Instanzvariable als Pfad
	 * verwendet
	 * 
	 * @return
	 * @throws IOException
	 */
	private BufferedWriter createFileWriter() throws IOException {
		return new BufferedWriter(new OutputStreamWriter(new FileOutputStream(
				filePathOut), Charset.forName("UTF-8").newEncoder()));
	}

	/**
	 * Liest jede Zeile der gegebenen Instanzvariable, spaltet sie nach Kommas
	 * auf und analysiert/normalisiert die einzelnen Bruckstücke. Wenn der Index
	 * des Bruchstücks im Array einem der gewählten Indizes aus dem Frontend
	 * entspricht, wird es einem StingBuilder hinzu gefügt und am Ende in eine
	 * Datei geschrieben.
	 */
	private void readLines() {
		try {
			BufferedReader read = createFileReader();
			BufferedWriter write = createFileWriter();

			StringBuilder sb = new StringBuilder();
			String line = null;
			
			int endValue = selectedIndices.size();

			
			while ((line = read.readLine()) != null) {
				String[] lineArr = line.split(",");
				int counter = 1;
				for (int i = 0; i < lineArr.length; i++) {
					if (selectedIndices.contains(i)) {
						
						 String temp = Normalizer.normalize(lineArr[i],
						 Form.NFD); temp = temp.replaceAll("[^\\p{ASCII}]","");
						 
						if (counter < endValue) {
							sb.append(temp + ",");
						} else {
							sb.append(temp);
						}
						counter++;
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

	/**
	 * Gibt den Pfad der neu erzeugten CSV Datei zurück
	 * 
	 * @return
	 */
	protected String getGeneratedCSV() {
		return filePathOut;

	}

	public static void main(String[] args) {
		new CSVRewriter(new ArrayList<Integer>(Arrays.asList(1, 2, 4, 7, 9)),
				"C:\\Users\\wooooot\\Downloads\\SPM_TestdatensatzKlein_2017.csv");
	}

}
