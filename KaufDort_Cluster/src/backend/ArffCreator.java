package backend;

import java.io.File;
import java.io.IOException;
import weka.core.Instances;
import weka.core.converters.ArffSaver;
import weka.core.converters.CSVLoader;

/**
 * Klasse erzeugt eine Arff-Datei aus einem gegebenen Pfad (String) zu einer CSV
 * Datei
 * @author Insa Kruse
 *
 */
public class ArffCreator {
	private String arffPath;

	public ArffCreator(String csvPath) {

		arffPath = createFileOut(csvPath);
		File arff = new File(arffPath);
		magicWekaTranslation(csvPath, arff);
	}

	/**
	 * Per Weka Bibliotheken wird aus einer einfachen csv eine speziell für weka
	 * formatierte arff Datei
	 * 
	 * @param csv
	 *            Input Datei im CSV Format
	 * @param arffFile
	 *            Output Datei im arff Format
	 */
	public void magicWekaTranslation(String csv, File arffFile) {
		if (!checkFile(arffFile)) {
			System.out
					.println("Error - arff already exists and can't be deleted");
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
			/*
			 * BufferedWriter writer = new BufferedWriter(new
			 * FileWriter(arffFile.getAbsolutePath()));
			 * writer.write(data.toString()); writer.flush(); writer.close();
			 */

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Prüft, ob eine Datei bereits existiert und falls ja, wird sie gelöscht.
	 * 
	 * @param f
	 *            eine Datei, die zu prüfen ist
	 * @return true, falls nicht existiert, true falls sie doch existiert und
	 *         sich löschen ließ, false falls nicht löschbar
	 */
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

	/**
	 * Erzeugt einen neuen String aus einem vollständigen Dateipfad indem es
	 * .csv durch .arff ersetzt
	 * 
	 * @param in
	 *            Als Input dient ein Pfad zu einer CSV-Datei
	 * @return Gibt einen String zurück, der die vorherige Dateiendung ersetzt
	 *         durch .arff
	 */
	private String createFileOut(String in) {
		return in.substring(0, in.lastIndexOf(".")) + ".arff";
	}

	/**
	 * 
	 * @return gibt den Pfad der erzeugten Arff-Datei zurück
	 */
	protected String getArffFilePath() {
		return arffPath;
	}

	public static void main(String[] args) {
		// new
		// ArffCreator("C:\\Users\\wooooot\\Downloads\\SPM_TestdatensatzKlein_2017_new.csv");
	}
}
