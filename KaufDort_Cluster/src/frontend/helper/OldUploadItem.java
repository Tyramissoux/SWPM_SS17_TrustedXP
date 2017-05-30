package frontend.helper;

import java.io.Serializable;
import java.util.ArrayList;
import backend.Feature;
import backend.KMeansCluster;

@SuppressWarnings("serial")
public class OldUploadItem implements Serializable{
	
	//Basisklasse für Veränderungen
	
	private String date;
	private int clustersUsed;
	private String fileName;
	private ArrayList<Feature> featureList;
	private ArrayList<KMeansCluster> clusterList;

	public OldUploadItem (String fileName, String date, int clustersUsed) {
		this.fileName = fileName;
		this.date = date;
		this.clustersUsed = clustersUsed;
	}
	
	public String getFileName () {
		return fileName;
	}
	
	public int getClustersUsed () {
		return clustersUsed;
	}
	
	public String getDate() {
		return date;
	}
	
	public void setFeatureList(ArrayList<Feature> list){
		featureList = list;
	}
	
	public ArrayList<Feature> getFeatureList(){
		return featureList;
	}
	
	public void setClusterList(ArrayList<KMeansCluster> list){
		clusterList = list;
	}
	
	public ArrayList<KMeansCluster> getClusterList(){
		return clusterList;
	}
}

