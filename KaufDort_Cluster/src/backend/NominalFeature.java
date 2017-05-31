package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class NominalFeature extends Feature
{
	private ArrayList<HashMap<String, Integer>> nominalElementCountsPerCluster;
	private ArrayList<String> uniqueNominalItems;


	public NominalFeature(String featureName, int featureType, int numOfClusters) {
		super(featureName, featureType, numOfClusters);
		// TODO Auto-generated constructor stub
		this.nominalElementCountsPerCluster = new ArrayList<HashMap<String, Integer>>();
		this.uniqueNominalItems = new ArrayList<String>();
		preFillNominalArrayList(numOfClusters);
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
	public void addFeatureElementForCluster(int clusterNum,
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
	public ArrayList<HashMap<String, Integer>> getAllClusterData() {
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
	 * Gibt die Liste zurueck, die alle einzigartigen Werte, die das Feature
	 * annehmen kann
	 * 
	 * @return
	 */
	public ArrayList<String> getUniqueItem() {
		return uniqueNominalItems;
	}

}
