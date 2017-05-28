package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Enumeration;
import weka.clusterers.SimpleKMeans;
import weka.core.Attribute;
import weka.core.Instance;
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

	private ArrayList<KMeansCluster> kMeansClusterList;
	private int seed;
	private ArrayList<Feature> features;

	public WekaClustering(String pathToArffFile, int chosenNumOfClusters) {
		this.features = new ArrayList<Feature>();
		this.kMeansClusterList = new ArrayList<KMeansCluster>();

		seed = 10;
		try {
			clusterArffData(pathToArffFile, chosenNumOfClusters);
			System.out.println("YES");
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
	 * entsprechende Objekte - wichtigste Methode in der Klasse, da sie alle
	 * Datenströme koordiniert
	 * 
	 * @param pathToArffFile
	 * @param numOfClusters
	 * @throws Exception
	 */
	private void clusterArffData(String pathToArffFile, int numOfClusters)
			throws Exception {
		// no touchy!! alles wichtige weka-bezogene Sachen
		BufferedReader datafile = readDataFile(pathToArffFile);
		SimpleKMeans kmeans = new SimpleKMeans();
		kmeans.setSeed(seed);

		// important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(numOfClusters);
		Instances data = new Instances(datafile);
		kmeans.buildClusterer(data);

		// eigene Kreationen
		createBasicFeatureList(numOfClusters, data);
		fillKMeansClusterList(numOfClusters);
		assignInstancesToKMeansCluster(data, features.size(),
				kmeans.getAssignments());
		addCentroids(kmeans);

		/*
		 * FOR TESTING: HashMap<String,Integer> map =
		 * features.get(1).getElementValuesForCluster(4); for(Entry<String,
		 * Integer> e : map.entrySet()){
		 * System.out.println(e.getKey()+" "+e.getValue()); }
		 */

	}

	/**
	 * 
	 * @param numOfClusters
	 */
	private void fillKMeansClusterList(int numOfClusters) {
		for (int i = 0; i < numOfClusters; i++) {
			kMeansClusterList.add(new KMeansCluster(numOfClusters, features
					.size()));
		}
	}

	/**
	 * 
	 * @param numOfClusters
	 * @param data
	 */
	private void createBasicFeatureList(int numOfClusters, Instances data) {
		@SuppressWarnings("unchecked")
		Enumeration<Attribute> enu = data.enumerateAttributes();
		while (enu.hasMoreElements()) {
			Attribute a = enu.nextElement();
			features.add(new Feature(a.name(), a.type(), numOfClusters));
			a = null;
		}
		enu = null;
	}

	/**
	 * 
	 * @param data
	 * @param numOfFeatures
	 * @param assignments
	 */
	private void assignInstancesToKMeansCluster(Instances data,
			int numOfFeatures, int[] assignments) {
		for (int i = 0; i < assignments.length; i++) {
			int assignment = assignments[i];
			Instance inst = data.instance(i);

			kMeansClusterList.get(assignment).addInstance(inst);
			kMeansClusterList.get(assignment).addOriginalInstanceNum(i);
			for (int j = 0; j < numOfFeatures; j++) {
				features.get(j).addFeatureElementForCluster(assignment,
						inst.toString(j));
			}
			inst = null;
		}
	}

	/**
	 * Fuegt die Centroide den KMeans Clustern in der kMeansClusterList hinzu
	 * 
	 * @param kmeans
	 */
	private void addCentroids(SimpleKMeans kmeans) {
		Instances centroids = kmeans.getClusterCentroids();
		for (int i = 0; i < centroids.numInstances(); i++) {
			kMeansClusterList.get(i).addCenteroid(centroids.instance(i));
		}
	}

	/**
	 * Gibt die KMeansClusterList zurück, die alle KMeansCluster Objekte
	 * enthaelt
	 * 
	 * @return ArrayList<KMeansCluster>
	 */
	protected ArrayList<KMeansCluster> getKMeansClusterList() {
		return kMeansClusterList;
	}
	
	protected ArrayList<Feature> getFeatureList(){
		return features;
	}

	public static void main(String[] args) {
		new WekaClustering(
				"C:/Users/wooooot/AppData/Local/Temp/2017_4_13_8_17_39/SPM_TestdatensatzKlein_2017_new.arff",
				5);
	}

}
