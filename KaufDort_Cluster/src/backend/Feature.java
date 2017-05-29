package backend;

import java.util.ArrayList;
import java.util.HashMap;

public class Feature {
	private String featureName;
	private int featureType;
	private ArrayList<HashMap<String, Integer>> elementCountsPerCluster;
	private ArrayList<String> uniqueItems;

	public Feature(String featureName, int featureType, int numOfClusters) {
		super();
		this.elementCountsPerCluster = new ArrayList<HashMap<String, Integer>>();
		this.uniqueItems = new ArrayList<String>();
		preFillArrayList(numOfClusters);
		this.featureName = featureName;
		this.featureType = featureType;
	}

	private void preFillArrayList(int end) {
		for (int i = 0; i < end; i++) {
			elementCountsPerCluster.add(new HashMap<String, Integer>());
		}
	}

	public void addFeatureElementForCluster(int clusterNum,
			String nominalElement) {
		if(!uniqueItems.contains(nominalElement))uniqueItems.add(nominalElement);
		if (elementCountsPerCluster.get(clusterNum).containsKey(nominalElement)) {
			int value = elementCountsPerCluster.get(clusterNum).get(
					nominalElement);
			elementCountsPerCluster.get(clusterNum).put(nominalElement,
					value + 1);
		} else {
			elementCountsPerCluster.get(clusterNum).put(nominalElement, 1);
		}
	}
	
	public ArrayList<HashMap<String,Integer>> getAllClusterData(){
		return elementCountsPerCluster;
	}

	public HashMap<String, Integer> getElementValuesForCluster(int clusterNum) {
		try {
			return elementCountsPerCluster.get(clusterNum);
		} catch (IndexOutOfBoundsException e) {
			System.out.println("Feature - index not found in ArrayList");
			return null;
		}
	}
	
	public String getFeatureName() {
		return featureName;
	}

	public ArrayList<String> getUniqueItem(){
		return uniqueItems;
	}
	
	public int getFeatureType() {
		return featureType;
	}

	/**
	 * Übersetzt den von Weka gegebenen Zahlenwert in einen String
	 * 
	 * @return Typ des Features
	 */
	/*private String featureType() {
		switch (featureType) {
		case 0:
			return "numeric";
		case 1:
			return "nominal";
		case 2:
			return "string";
		case 3:
			return "date";
		case 4:
			return "relational";
		default:
			return null;
		}
	}*/

}
