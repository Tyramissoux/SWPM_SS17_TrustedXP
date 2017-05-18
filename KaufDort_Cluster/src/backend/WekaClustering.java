package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import weka.clusterers.SimpleKMeans;
import weka.core.Instances;

/**
 * Erzeugt aus einem gegebenen Pfad zu einer Arff-Datei ein KMeans Clustering
 * und leitet die geclusterten Instanzen (Datenzeilen aus der CSV) in ihre
 * zugehörigen KMeansCluster um
 * 
 * @author wooooot
 * 
 */
public class WekaClustering {

	private ArrayList<KMeansCluster> list;
	private int seed;
	private HashMap<Integer, KMeansCluster> clusterMap;

	public WekaClustering(String pathToArffFile, int chosenNumOfClusters) {
		clusterMap = new HashMap<Integer, KMeansCluster>();
		seed = 10;
		try {
			clusterArffData(pathToArffFile, chosenNumOfClusters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * Öffnet eine Datei mit einem FileReader und returniert das BufferedReader
	 * Objekt
	 * 
	 * @param filename
	 *            arff-Datei, die gelesen werden soll
	 * @return BufferedReader
	 */
	private BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			// sollte theoretisch nicht zu diesem Fehler kommen können
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}

	/**
	 * Methode führt das eigentliche Clustering durch und leitet die Daten um in
	 * entsprechende Objekte
	 * 
	 * @param pathToArffFile
	 * @param numOfClusters
	 * @throws Exception
	 */
	private void clusterArffData(String pathToArffFile, int numOfClusters)
			throws Exception {
		BufferedReader datafile = readDataFile(pathToArffFile);
		SimpleKMeans kmeans = new SimpleKMeans();

		kmeans.setSeed(seed);

		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(numOfClusters);

		Instances data = new Instances(datafile);

		kmeans.buildClusterer(data);

		Instances centers = kmeans.getClusterCentroids();
		int attributes = centers.numAttributes();

		// This array returns the cluster number (starting with 0) for each
		// instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();

		for (int i = 0; i < assignments.length; i++) {
			int assignment = assignments[i];
			if (clusterMap.containsKey(assignment)) {
				clusterMap.get(assignment).addInstance(data.instance(i));
			} else {
				clusterMap.put(assignment, new KMeansCluster(assignment,
						attributes));

			}
		}

		Instances centroids = kmeans.getClusterCentroids();
		for (int i = 0; i < centroids.numInstances(); i++) {
			if (clusterMap.containsKey(i)) {
				clusterMap.get(i).addCenteroid(centroids.instance(i));
			}

		}

		/*
		 * for (int i = 0; i < centroids.numInstances(); i++) {
		 * System.out.println("Centroid " + i + ": " + centroids.instance(i)); }
		 * 
		 * for ( int j = 0; j < centers.numInstances(); j++ ) { // for each
		 * cluster center Instance inst = centers.instance( j ); // as you
		 * mentioned, you only had 1 attribute // but you can iterate through
		 * the different attributes double value = inst.value( 0 );
		 * System.out.println(inst.attributeSparse(0)); System.out.println(
		 * "Value for centroid " + j + ": " + value +" weight: "+inst.weight());
		 * }
		 */

		for (Entry<Integer, KMeansCluster> en : clusterMap.entrySet()) {
			en.getValue().test();
		}

		list = new ArrayList<KMeansCluster>();
	}

	/**
	 * Gibt die KMeansClusterList zurück
	 * 
	 * @return ArrayList<KMeansCluster>
	 */
	protected ArrayList<KMeansCluster> getKMeansClusterList() {
		return list;
	}

	public static void main(String[] args) {
		new WekaClustering(
				"C:/Users/wooooot/AppData/Local/Temp/2017_4_13_8_17_39/SPM_TestdatensatzKlein_2017_new.arff",
				5);
	}

}
