package backend;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class KMeansCluster {
	
	private ArrayList<Instance> assignedInstances;
	private ArrayList<Integer> originalInstanceNumber;
	private int clusterNum;
	private int attributesNum;//for test
	Instance centroid;
	
	
	public KMeansCluster(int numOfClusters, int numOfAttributes){
		this.clusterNum = numOfClusters;
		this.attributesNum = numOfAttributes;
		this.assignedInstances = new ArrayList<Instance>();
		this.originalInstanceNumber = new ArrayList<Integer>();	
	}

	public void addInstance(Instance in){
		assignedInstances.add(in);
	}
	
	public void addOriginalInstanceNum(int in){
		originalInstanceNumber.add(in);
	}
	
	public int getClusterNumber(){
		return clusterNum;
	}
	
	public ArrayList<Instance> getAllInstances(){
		return assignedInstances;
	}
	
	public int instancesAssignedToCluster(){
		return assignedInstances.size();
	}
	
	public void addCenteroid(Instance instance){
		centroid = instance;
	}
	
	public Instance getCenteroid(){
		return centroid;
	}
	
	public String getCentroidValues(){
		return centroid.toString();
	}
	
	public int getNumberOfInstancesAssgned(){
		return assignedInstances.size();
	}
	
	/* hrrrrm
	public void test(){
		System.out.println("Cluster "+clusterNum+":");
	for(int i = 0; i < assignedInstances.size(); i++){
		Instance inst = assignedInstances.get(i);
		System.out.print("Instance "+i+":\t");
		for (int j = 0; j < attributesNum; j++) {
			Attribute a = inst.attribute(j); 
			System.out.print(inst.toString(j) +" ("+a.isNominal()+")\t");	
		}
		System.out.println();
		
	}
	}*/
	
	
}
