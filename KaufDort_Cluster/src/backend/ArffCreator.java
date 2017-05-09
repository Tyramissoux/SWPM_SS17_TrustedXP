package backend;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class ArffCreator {
	private String arffPath;

	public ArffCreator(String csvPath) {

		arffPath = createFileOut(csvPath);
		File arff = new File(arffPath);
		magicWekaTranslation(arff, csvPath);
	}

	public void magicWekaTranslation(File arffFile, String csv) {
		if (!checkFile(arffFile)) {
			System.out.println("Error - arff already exists and can't be deleted");
			return;
		}
		// CSV-Datei laden
		CSVLoader loader = new CSVLoader();
		try {
			loader.setSource(new File(csv));
			Instances data = loader.getDataSet();
			
			// und als ARFF-Datei speichern
			ArffSaver saver = new ArffSaver();
			saver.setFile(arffFile);
		
			saver.setInstances(data);			
			saver.writeBatch();
			/*BufferedWriter writer = new BufferedWriter(new FileWriter(arffFile.getAbsolutePath()));
		    writer.write(data.toString());
		    writer.flush();
		    writer.close();*/
	
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private boolean checkFile(File f) {
		if (f.exists()) {
			try {
				f.delete();
			} catch (Exception e) {
				return false;
			}
			return true;
		} else
			return true;

	}

	private String createFileOut(String in) {
		return in.substring(0, in.lastIndexOf(".")) + ".arff";
	}

	protected String getArffFilePath() {
		return arffPath;
	}

	public static void main(String[] args) {
		//new ArffCreator("C:\\Users\\wooooot\\Downloads\\SPM_TestdatensatzKlein_2017_new.csv");
	}
}
