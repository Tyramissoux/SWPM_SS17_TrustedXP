package frontend;

import java.util.ArrayList;

import java.util.List;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.Button;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;
import frontend.helper.OldUploadItem;
import weka.core.Instance;
import backend.KMeansCluster;
import backend.Feature;

//http://zkfiddle.org/sample/h20ktc/2-Fixed-and-Dynamic-Columns
/**
 * 
 * @author Insa Kruse
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
		storeUploadItem();
		
		//System.out.println(picPath);
	}



	private void storeUploadItem() {
		
		String fileName = (String)Sessions.getCurrent().getAttribute("originalFileName");
		String date = (String)Sessions.getCurrent().getAttribute("uploadDate");
		OldUploadItem old = new OldUploadItem(fileName, date, clusterList.size());
		old.setClusterList(clusterList);
		old.setFeatureList(featureList);
		//new StoreToDataBase(old); 
	}

	
	
	/*private String createServerPath(String name) {
		String webAppPath = Executions.getCurrent().getDesktop().getWebApp()
				.getRealPath(File.separator);
		webAppPath += "Files" + File.separator;
		return webAppPath + name;
	}*/
	
	/*private String getRealPath(String file){
		return Sessions.getCurrent().getWebApp().getRealPath(file);
	}*/

	

	private void transferDataToListModelMap() {
		List<String> value = new java.util.ArrayList<String>();

		for (int i = 0; i < featureList.size(); i++) {

			for (int j = 0; j < clusterList.size(); j++) {
				
				Instance in = clusterList.get(j).getCenteroid();
				value.add(in.toString(i));
				 //System.out.print(in.toString(i)+" ");
			}

			data.put((featureList.get(i).getFeatureName()), value);
			//System.out.print(featureList.get(i).getFeatureName());
			// System.out.println();
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

	@Command
	public void showDiagram(@ContextParam(ContextType.COMPONENT) Component component) {
		 
		Button b = (Button) component;
		String featureChosen = b.getLabel();
		Feature feat = null;
		for(int i = 0; i < featureList.size();i++){
			if(featureList.get(i).getFeatureName().equals(featureChosen)){
				feat = featureList.get(i);
			}
		}
		Sessions.getCurrent().setAttribute("chosenFeature", feat);
		
		if(feat.getFeatureType()!= 0)
		//koennte problematisch werden bei anderen Browsern
		Executions.getCurrent().sendRedirect("stackedColumns.zul");
		else 
			Executions.getCurrent().sendRedirect("boxplot.zul");
		
	}

}
