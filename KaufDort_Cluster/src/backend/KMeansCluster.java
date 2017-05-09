package backend;

import java.util.ArrayList;

import weka.core.Instance;

public class KMeansCluster {
	
	ArrayList<Instance> instances;
	private int clusterNum;
	
	public KMeansCluster(int clusterNum){
		this.clusterNum = clusterNum;
		instances = new ArrayList<Instance>();
	}

	protected void addInstance(Instance in){
		instances.add(in);
	}
	
	protected int getClusterNumber(){
		return clusterNum;
	}
	
	protected ArrayList<Instance> getAllInstances(){
		return instances;
	}
	
	protected int instancesAssignedToCluster(){
		return instances.size();
	}
}
