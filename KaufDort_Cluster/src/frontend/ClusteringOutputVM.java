package frontend;

import java.util.ArrayList;
import java.util.List;

import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;

import weka.core.Instance;

import backend.KMeansCluster;
import backend.Feature;

//http://zkfiddle.org/sample/h20ktc/2-Fixed-and-Dynamic-Columns
/**
 * 
 * @author wooooot
 * 
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class ClusteringOutputVM {

	private ArrayList<KMeansCluster> clusterList;
	private ArrayList<Feature> featureList;
	private int numOfClusters;

	private ListModelMap data;
	private ListModel columns_model;

	public ClusteringOutputVM() {

		data = new ListModelMap();
		columns_model = new ListModelList();
		
		getSessionGlobalVariables();
		transferDataToListModelMap();
		fillColumnsModel(numOfClusters);
		
	}

	private void transferDataToListModelMap() {
		List<String> value = new java.util.ArrayList<String>();

		for (int i = 0; i < featureList.size(); i++) {

			for (int j = 0; j < clusterList.size(); j++) {
				Instance in = clusterList.get(j).getCenteroid();
				value.add(in.toString(i));
				System.out.print(in.toString(i)+" ");
			}
			
			data.put((featureList.get(i).getFeatureName()), value);
			System.out.print(featureList.get(i).getFeatureName());
			System.out.println();
			value = new ArrayList<String>();
		}

	}

	private void getSessionGlobalVariables() {
		clusterList = (ArrayList<KMeansCluster>) Sessions.getCurrent()
				.getAttribute("finalClusterList");
		featureList = (ArrayList<Feature>) Sessions.getCurrent().getAttribute(
				"finalFeatureList");
		numOfClusters = (int) Sessions.getCurrent().getAttribute(
				"chosenNumOfClusters");
	}

	private void fillColumnsModel(int endValue) {
		((List) columns_model).add(new String("Feature"));
		for (int i = 1; i <= endValue; ++i)
			((List) columns_model).add(new String("Centroid " + i));

	}

	public ListModel getColumnsModel() {
		return columns_model;
	}

	public ListModel getMapModel() {
		return data;
	}

	// @Command
	// @NotifyChange("data")
	// public void removeRow(@BindingParam("row") ArrayList list) {
	// data.remove(list);
	// }

}
