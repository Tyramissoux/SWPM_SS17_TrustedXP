package backend;

import java.util.ArrayList;
/**
 * Einfache Steuerklasse um die Vorgänge im Backend zentral zu steuern
 * @author wooooot
 *
 */
public class BackEndController {
	
	ArrayList<KMeansCluster> clusterList;

	public BackEndController(ArrayList<Integer> selectedIndices, String uploadedFilePath, int chosenNumOfClusters){
		//Reihenfolge ist notwendig
		String csv = new CSVRewriter(selectedIndices, uploadedFilePath).getGeneratedCSV();
		System.out.println(csv);
		String arff = new ArffCreator(csv).getArffFilePath();
		clusterList = new WekaClustering(arff, chosenNumOfClusters).getKMeansClusterList();
	}

}
