package backend;

import java.util.ArrayList;

import weka.core.Attribute;
import weka.core.Instance;
import weka.core.Instances;

public class KMeansCluster {
	
	ArrayList<Instance> assignedInstances;
	ArrayList<Integer> originalInstanceNumber;
	private int clusterNum;
	private int attributesNum;
	Instance centroid;
	
	
	public KMeansCluster(int numOfClusters, int numOfAttributes){
		this.clusterNum = numOfClusters;
		this.attributesNum = numOfAttributes;
		this.assignedInstances = new ArrayList<Instance>();
		this.originalInstanceNumber = new ArrayList<Integer>();	
	}

	protected void addInstance(Instance in){
		assignedInstances.add(in);
	}
	
	protected void addOriginalInstanceNum(int in){
		originalInstanceNumber.add(in);
	}
	
	protected int getClusterNumber(){
		return clusterNum;
	}
	
	protected ArrayList<Instance> getAllInstances(){
		return assignedInstances;
	}
	
	protected int instancesAssignedToCluster(){
		return assignedInstances.size();
	}
	
	protected void addCenteroid(Instance instance){
		centroid = instance;
	}
	
	protected Instance getCenteroid(){
		return centroid;
	}
	
	protected String getCentroidValues(){
		return centroid.toString();
	}
	
	protected int getNumberOfInstancesAssgned(){
		return assignedInstances.size();
	}
	
	// hrrrrm
	protected void test(){
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
	}
	
	
}
