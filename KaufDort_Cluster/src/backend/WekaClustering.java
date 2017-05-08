package backend;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;

import weka.clusterers.SimpleKMeans;
import weka.core.Instance;
import weka.core.Instances;
public class WekaClustering {
	
	ArrayList<KMeansCluster> list;
	
	public WekaClustering(String pathToArffFile, int chosenNumOfClusters){
		try {
			clusterArffData(pathToArffFile,chosenNumOfClusters);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private BufferedReader readDataFile(String filename) {
		BufferedReader inputReader = null;
		try {
			inputReader = new BufferedReader(new FileReader(filename));
		} catch (FileNotFoundException ex) {
			System.err.println("File not found: " + filename);
		}
		return inputReader;
	}

	private void clusterArffData(String pathToArffFile, int numOfClusters) throws Exception {
		BufferedReader datafile = readDataFile(pathToArffFile);
		SimpleKMeans kmeans = new SimpleKMeans();
		 
		kmeans.setSeed(10);
 
		//important parameter to set: preserver order, number of cluster.
		kmeans.setPreserveInstancesOrder(true);
		kmeans.setNumClusters(numOfClusters);
 
		
		Instances data = new Instances(datafile);
 
 
		kmeans.buildClusterer(data);
 
		// This array returns the cluster number (starting with 0) for each instance
		// The array has as many elements as the number of instances
		int[] assignments = kmeans.getAssignments();
 
		int i=0;
		for(int clusterNum : assignments) {
		    System.out.printf("Instance %d -> Cluster %d \n", i, clusterNum);
		    i++;
		}
		
		Instances instances = kmeans.getClusterCentroids();
		for ( int j = 0; j < instances.numInstances(); j++ ) {
		    // for each cluster center
		    Instance inst = instances.instance( j );
		    // as you mentioned, you only had 1 attribute
		    // but you can iterate through the different attributes
		    double value = inst.value( 0 );
		    System.out.println( "Value for centroid " + j + ": " + value );
		}
		
		list = new ArrayList<KMeansCluster>();
	}
	
	protected ArrayList<KMeansCluster> getKMeansClusterList(){
		return list;
	}

}
