package backend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

@SuppressWarnings("serial")
public class NumericFeature extends Feature {
	private ArrayList<HashMap<Double, Integer>> numericElementCountsPerCluster;
	private ArrayList<Double> uniqueNumericItems;
	private ArrayList<Double> totalValues;
	private ArrayList<ArrayList<Double>> valuesPerCluster;
	


	public NumericFeature(String featureName, int featureType, int numOfClusters) {
		super(featureName, featureType, numOfClusters);
		// TODO Auto-generated constructor stub
		this.numericElementCountsPerCluster = new ArrayList<HashMap<Double, Integer>>();
		this.uniqueNumericItems = new ArrayList<Double>();
		this.valuesPerCluster = new ArrayList<ArrayList<Double>>();
		this.totalValues = new ArrayList<Double>();
		preFillHashMapArrayList(numOfClusters);
		prefillArrayList(numOfClusters);
	}

	/**
	 * Die ArrayList elementCountsPerCluster wird vorher gefuellt mit so vielen
	 * Objekten wie Cluster fuer das Clustering verwendet wurden. Die Methode
	 * verhindert NullPointer Exceptions, macht den nachfolgenden Code etwas
	 * einfacher und ist auch absolut notwendig fuer die weitere Ausfuehrung
	 * 
	 * @param end
	 */
	private void preFillHashMapArrayList(int end) {
		for (int i = 0; i < end; i++) {
			numericElementCountsPerCluster.add(new HashMap<Double, Integer>());
		}
	}
	
	private void prefillArrayList(int end){
		for (int i = 0; i < end; i++) {
			valuesPerCluster.add(new ArrayList<Double>());
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
	 * @param numericElement
	 */
	public void addFeatureElementForCluster(int clusterNum, double numericElement) {
		if (!uniqueNumericItems.contains(numericElement))
			uniqueNumericItems.add(numericElement);
		if (numericElementCountsPerCluster.get(clusterNum).containsKey(
				numericElement)) {
			int value = numericElementCountsPerCluster.get(clusterNum).get(
					numericElement);
			numericElementCountsPerCluster.get(clusterNum).put(numericElement,
					value + 1);
		} else {
			numericElementCountsPerCluster.get(clusterNum).put(numericElement,
					1);
		}
		valuesPerCluster.get(clusterNum).add(numericElement);
		totalValues.add(numericElement);
	}

	/**
	 * Gibt die ArrayList nominalElementCountsPerCluster zurueck, die alle
	 * zaehlenden Hashmaps enthaelt
	 * 
	 * @return
	 */
	public ArrayList<HashMap<Double, Integer>> getAllClusterData() {
		return numericElementCountsPerCluster;
	}

	/**
	 * Gibt eine einzelne Hashmap zurueck, die zu einem Cluster, repraesentiert
	 * vom Parameter clusterNum, gehoert
	 * 
	 * @param clusterNum
	 * @return
	 */
	public HashMap<Double, Integer> getElementValuesForCluster(int clusterNum) {
		try {
			return numericElementCountsPerCluster.get(clusterNum);
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
	public ArrayList<Double> getUniqueItem() {
		Collections.sort(uniqueNumericItems);
		return uniqueNumericItems;
	}

	public double[] getAllValuesForFeature(){
		double[] arr = new double[totalValues.size()];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = totalValues.get(i);
		}
		return arr;
	}
	
	public ArrayList<ArrayList<Double>> getValuesPerCluster(){
		return valuesPerCluster;
	}
}
