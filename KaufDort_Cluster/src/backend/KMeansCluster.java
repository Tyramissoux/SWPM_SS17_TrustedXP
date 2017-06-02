package backend;

import java.io.Serializable;
import java.util.ArrayList;
import weka.core.Instance;

/**
 * Datenstruktur, die einen einzelnen Cluster repr�sentiert, der von Weka
 * erzeugt wurde. S�mtliche Instanzen (Datenreihen), die von Weka diesem Cluster
 * zugewiesen wurden, befinden sich ebenfalls hier. Des weiteren auch der
 * Centroid. Potentiell kann diese Klasse auch f�r weitere Analysen dienen oder
 * zur Speicherung des Outputs in einer Excel Tabelle als Vorlage verwendet
 * werden.
 * 
 * @author Insa Kruse
 * 
 */
public class KMeansCluster implements Serializable{

	private ArrayList<Instance> assignedInstances;
	private ArrayList<Integer> originalInstanceNumber;
	private int clusterNum;
	private int attributesNum;// for test
	Instance centroid;

	/**
	 * Erzeugt einen neuen KMeansCluster
	 * 
	 * @param numOfClusters
	 *            - Anzahl an Clustern, die vorher festgelegt wurden
	 * @param numOfAttributes
	 *            - Anzahl an Attributen (Features) in der arff-Datei
	 */
	public KMeansCluster(int numOfClusters, int numOfAttributes) {
		this.clusterNum = numOfClusters;
		this.attributesNum = numOfAttributes;
		this.assignedInstances = new ArrayList<Instance>();
		this.originalInstanceNumber = new ArrayList<Integer>();
	}

	/**
	 * F�gt eine Weka-Instanz (Datenreihe) dem Cluster hinzu
	 * 
	 * @param in
	 */
	public void addInstance(Instance in) {
		assignedInstances.add(in);
	}

	/**
	 * F�gt die originale Zeilennummer der Instanz hinzu. Kann verwendet werden,
	 * um in die Datenreihen in der urspr�nglich hochgeladenen Datei zur�ck zu
	 * verfolgen.
	 * 
	 * @param in
	 */
	public void addOriginalInstanceNum(int in) {
		originalInstanceNumber.add(in);
	}

	/**
	 * Gibt die Nummer des Clusters zur�ck (aus assignments[])
	 * 
	 * @return
	 */
	public int getClusterNumber() {
		return clusterNum;
	}

	/**
	 * Gibt eine ArrayList aller gespeicherten Instanzen (Datenreihen) zur�ck
	 * 
	 * @return
	 */
	public ArrayList<Instance> getAllInstances() {
		return assignedInstances;
	}

	/**
	 * Gibt die Liste der Zeilennummern der zugewiesenen Instanzen (Datenreihen)
	 * aus der Originaldatei zur�ck
	 * 
	 * @return
	 */
	public ArrayList<Integer> getOriginalInstanceNumbers() {
		return originalInstanceNumber;
	}

	/**
	 * Gibt die Anzahl der zu diesem Cluster zugewiesenen Instanzen
	 * (Datenreihen) zur�ck
	 * 
	 * @return
	 */
	public int getNumberOfInstancesAssignedToCluster() {
		return assignedInstances.size();
	}

	/**
	 * F�gt den Centroid (Clustermittelpunkt) dem Cluster hinzu
	 * 
	 * @param instance
	 */
	public void addCenteroid(Instance instance) {
		centroid = instance;
	}

	/**
	 * Gibt den gespeicherten Centroid zur�ck
	 * 
	 * @return
	 */
	public Instance getCentroid() {
		return centroid;
	}

	/**
	 * Gibt die Centroid-Werte als String zur�ck
	 * 
	 * @return
	 */
	public String getCentroidValues() {
		return centroid.toString();
	}

	/*
	 * hrrrrm public void test(){ System.out.println("Cluster "+clusterNum+":");
	 * for(int i = 0; i < assignedInstances.size(); i++){ Instance inst =
	 * assignedInstances.get(i); System.out.print("Instance "+i+":\t"); for (int
	 * j = 0; j < attributesNum; j++) { Attribute a = inst.attribute(j);
	 * System.out.print(inst.toString(j) +" ("+a.isNominal()+")\t"); }
	 * System.out.println();
	 * 
	 * } }
	 */

}
