package backend;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Feature ist eine Datenstruktur, die Daten zu einem einzelnen Feature pro
 * Cluster speichert und das Vorkommen der Werte, die das Feature annehmen kann,
 * berechnet.
 * 
 * @author Insa Kruse
 * 
 */
public class Feature {
	private String featureName;
	private int featureType;
	private ArrayList<HashMap<String, Integer>> nominalElementCountsPerCluster;
	private ArrayList<String> uniqueNominalItems;

	/**
	 * Konstruktor nimmt den Namen des betreffenden Features, den Featuretyp
	 * (von weka) und die Anzahl an Clustern, die fuer das Clustering verwendet
	 * wurden
	 * 
	 * @param featureName
	 * @param featureType
	 * @param numOfClusters
	 */
	public Feature(String featureName, int featureType, int numOfClusters) {
		super();
		this.nominalElementCountsPerCluster = new ArrayList<HashMap<String, Integer>>();
		this.uniqueNominalItems = new ArrayList<String>();
		preFillNominalArrayList(numOfClusters);
		this.featureName = featureName;
		this.featureType = featureType;
	}

	/**
	 * Die ArrayList elementCountsPerCluster wird vorher gefuellt mit so vielen
	 * Objekten wie Cluster fuer das Clustering verwendet wurden. Die Methode
	 * verhindert NullPointer Exceptions, macht den nachfolgenden Code etwas
	 * einfacher und ist auch absolut notwendig fuer die weitere Ausfuehrung
	 * 
	 * @param end
	 */
	private void preFillNominalArrayList(int end) {
		for (int i = 0; i < end; i++) {
			nominalElementCountsPerCluster.add(new HashMap<String, Integer>());
		}
	}

	/**
	 * Es wird ein weiteres Element der entsprechenden HashMap, gespeichert am
	 * Index, der dem Cluster entspricht, hinzugefügt. Ist das Element bereits
	 * in der Hashmap enthalten, wird der zugehörige Value um eins erhöht. So
	 * werden die einzigartigen Elemente pro Cluster gezaehlt. Ausserdem wird
	 * die Liste mit den einzigartigen Elementen pro Feature bestueckt.
	 * 
	 * @param clusterNum
	 * @param nominalElement
	 */
	public void addNominalFeatureElementForCluster(int clusterNum,
			String nominalElement) {
		if (!uniqueNominalItems.contains(nominalElement))
			uniqueNominalItems.add(nominalElement);
		if (nominalElementCountsPerCluster.get(clusterNum).containsKey(
				nominalElement)) {
			int value = nominalElementCountsPerCluster.get(clusterNum).get(
					nominalElement);
			nominalElementCountsPerCluster.get(clusterNum).put(nominalElement,
					value + 1);
		} else {
			nominalElementCountsPerCluster.get(clusterNum).put(nominalElement,
					1);
		}
	}

	/**
	 * Gibt die ArrayList nominalElementCountsPerCluster zurueck, die alle
	 * zaehlenden Hashmaps enthaelt
	 * 
	 * @return
	 */
	public ArrayList<HashMap<String, Integer>> getAllNominalClusterData() {
		return nominalElementCountsPerCluster;
	}

	/**
	 * Gibt eine einzelne Hashmap zurueck, die zu einem Cluster, repraesentiert
	 * vom Parameter clusterNum, gehoert
	 * 
	 * @param clusterNum
	 * @return
	 */
	public HashMap<String, Integer> getElementValuesForCluster(int clusterNum) {
		try {
			return nominalElementCountsPerCluster.get(clusterNum);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Feature - index not found in ArrayList");
			return null;
		}
	}

	/**
	 * Gibt den Namen des Features zurueck
	 * 
	 * @return
	 */
	public String getFeatureName() {
		return featureName;
	}

	/**
	 * Gibt die Liste zurueck, die alle einzigartigen Werte, die das Feature
	 * annehmen kann
	 * 
	 * @return
	 */
	public ArrayList<String> getUniqueNominalItem() {
		return uniqueNominalItems;
	}

	/**
	 * Gibt den Typ des Features als Zahlwert zurueck - 0: numerisch, 1: nominal, 2: string,
	 * 3: datum, 4: relational - von weka festgelegt
	 * 
	 * @return
	 */
	public int getFeatureType() {
		return featureType;
	}

	/**
	 * Übersetzt den von Weka gegebenen Zahlenwert in einen String
	 * 
	 * @return Typ des Features
	 */
	/*
	 * private String featureType() { switch (featureType) { case 0: return
	 * "numeric"; case 1: return "nominal"; case 2: return "string"; case 3:
	 * return "date"; case 4: return "relational"; default: return null; } }
	 */

}
