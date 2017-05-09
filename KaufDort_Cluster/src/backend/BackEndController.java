package backend;

import java.util.ArrayList;

public class BackEndController {
	
	ArrayList<KMeansCluster> clusterList;
	/*needed: 	arraylist f�r feature indices
	 * 			Anzahl Cluster
	 * 			Pfad f�r Input CSV*/
	public BackEndController(ArrayList<Integer> selectedIndices, String uploadedFilePath, int chosenNumOfClusters){
		
		String csv = new CSVRewriter(selectedIndices, uploadedFilePath).getGeneratedCSV();
		System.out.println(csv);
		String arff = new ArffCreator(csv).getArffFilePath();
		clusterList = new WekaClustering(arff, chosenNumOfClusters).getKMeansClusterList();
	}

}
