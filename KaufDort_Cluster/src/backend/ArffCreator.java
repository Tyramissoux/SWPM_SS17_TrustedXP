package backend;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

public class ArffCreator {
	private String arffPath;

	public ArffCreator(String csvPath) {
		String csvDat = csvPath + "kd.csv"; // Rohdaten
		String arffDat = csvPath + "kd.arff"; // arff-Daten für WeKa

		// CSV-Datei laden
		CSVLoader loader = new CSVLoader();
		try {
			loader.setSource(new File(csvDat));
			Instances data = loader.getDataSet();
			// und als ARFF-Datei speichern
			ArffSaver saver = new ArffSaver();
			saver.setInstances(data);
			saver.setFile(new File(arffDat));
			saver.writeBatch();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		arffPath = arffDat;
	}

	protected String getArffFilePath() {
		return arffPath;
	}
}
