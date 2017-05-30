package backend;

import java.util.ArrayList;

import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
/**
 * Einfache Steuerklasse um die Vorgänge im Backend zentral zu steuern
 * @author Insa Kruse
 *
 */
public class BackEndController {
	
	

	public BackEndController(ArrayList<Integer> selectedIndices, String uploadedFilePath, int chosenNumOfClusters){
		//Reihenfolge ist notwendig
		String csv = new CSVRewriter(selectedIndices, uploadedFilePath).getGeneratedCSV();
		//System.out.println(csv);
		String arff = new ArffCreator(csv).getArffFilePath();
		
		WekaClustering wc = new WekaClustering(arff, chosenNumOfClusters);
		
		ArrayList<KMeansCluster> clusterList = wc.getKMeansClusterList();
		ArrayList<Feature> featureList = wc.getFeatureList();
		
		//Variablen werden global gesetzt
		Sessions.getCurrent().setAttribute("finalClusterList", clusterList);
		Sessions.getCurrent().setAttribute("finalFeatureList", featureList);
		Sessions.getCurrent().setAttribute("chosenNumOfClusters", chosenNumOfClusters);
		
		Executions.sendRedirect("clusteringOutput.zul");
		
	}

}
