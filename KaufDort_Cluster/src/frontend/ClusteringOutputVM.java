package frontend;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
import org.zkoss.bind.annotation.NotifyChange;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.Executions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.Selectors;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zul.Button;
import org.zkoss.zul.Cell;
import org.zkoss.zul.Grid;
import org.zkoss.zul.ListModel;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.ListModelMap;
import org.zkoss.zul.Messagebox;

import frontend.helper.OldUploadItem;
import weka.core.Instance;
import backend.KMeansCluster;
import backend.Feature;
import backend.NumericFeature;

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
	private String[][] paintMe;
	private boolean isVisible;

	@Wire("#grid")
	public Grid grid;

	public ClusteringOutputVM() {

		data = new ListModelMap();
		columns_model = new ListModelList();

		getSessionGlobalVariables();
		transferDataToListModelMap();
		fillColumnsModel(numOfClusters);

		// System.out.println(picPath);
	}

	@Command
	public void store() {
		Clients.showBusy("Bitte warten...");
		String fileName = (String) Sessions.getCurrent().getAttribute(
				"originalFileName");
		if (!fileName.equals("")) {
			String date = (String) Sessions.getCurrent().getAttribute(
					"uploadDate");
			OldUploadItem old = new OldUploadItem(fileName, date,
					clusterList.size());
			old.setClusterList(clusterList);
			old.setFeatureList(featureList);
			new StoreToDataBase(old);
			Clients.clearBusy();
			Messagebox.show("Erfolgreich gespeichert", "Information",
					Messagebox.OK, Messagebox.EXCLAMATION);

		}
	}
	
	@AfterCompose
	public void afterCompose(@ContextParam(ContextType.VIEW) Component view) {
		Selectors.wireComponents(view, this, false);
		/*
		 * List<Component> comps = view.getChildren(); for (Component c : comps)
		 * { if (c.getId().equals("grid")) grid = (Grid) c; }
		 */

	}

	@NotifyChange("isVisible")
	@Command
	public void colourMe() {
		int xAxis = clusterList.size() + 1;
		int yAxis = featureList.size();
		isVisible = !isVisible;
		for (int i = 0; i < yAxis; i++) {
			for (int j = 0; j < xAxis; j++) {

				Cell c = (Cell) grid.getCell(i, j);
				if (c.getStyle() != null)
					c.setStyle(null);
				else
					c.setStyle(paintMe[i][j]);

			}
		}

	}
	
	public boolean getIsVisible(){
		return isVisible;
	}

	/*
	 * private String createServerPath(String name) { String webAppPath =
	 * Executions.getCurrent().getDesktop().getWebApp()
	 * .getRealPath(File.separator); webAppPath += "Files" + File.separator;
	 * return webAppPath + name; }
	 */

	/*
	 * private String getRealPath(String file){ return
	 * Sessions.getCurrent().getWebApp().getRealPath(file); }
	 */

	private void transferDataToListModelMap() {

		List<String> valueList = new java.util.ArrayList<String>();
		int featureType = 0;

		for (int i = 0; i < featureList.size(); i++) {
			featureType = featureList.get(i).getFeatureType();
			if (featureType == 0) {
				NumericFeature nf = (NumericFeature) featureList.get(i);
				double[] featureValuesArr = nf.getAllValuesForFeature();
				Percentile percentile = new Percentile();
				Arrays.sort(featureValuesArr);
				percentile.setData(featureValuesArr);
				double min = 0;
				double ten = percentile.evaluate(10);
				double twenty = percentile.evaluate(20);
				double thirty = percentile.evaluate(30);
				double fourty = percentile.evaluate(40);
				double fifty = percentile.evaluate(50);
				double sixty = percentile.evaluate(60);
				double seventy = percentile.evaluate(70);
				double eighty = percentile.evaluate(80);
				double ninety = percentile.evaluate(90);
				double max = percentile.evaluate(100);

				// System.out.println(nf.getFeatureName()+"\t"+min+"\t"+first+"\t"+second+"\t"+third+"\t"+max);

				for (int j = 0; j < clusterList.size(); j++) {
					Instance in = clusterList.get(j).getCentroid();
					String centroidVal = in.toString(i);
					double value = Double.valueOf(centroidVal);
					valueList.add(centroidVal);
					//http://colorbrewer2.org/#type=sequential&scheme=YlOrRd&n=9
					if (value >= min && value < ten) {
						paintMe[i][j + 1] = "background:#f7fcfd";//
						continue;
					}

					if (value >= ten && value < twenty) {
						paintMe[i][j + 1] = "background:#e0ecf4";//
						continue;
					}
					
					if (value >= twenty && value < thirty) {
						paintMe[i][j + 1] = "background:#bfd3e6";//
						continue;
					}

					if (value >= thirty && value < fourty) {
						paintMe[i][j + 1] = "background:#9ebcda";//
						continue;
					}
					

					if (value >= fourty && value < fifty) {
						paintMe[i][j + 1] = "background:#8c96c6";//
						continue;
					}

					if (value >= fifty && value < sixty) {
						paintMe[i][j + 1] = "background:#a1d99b";//
						continue;
					}

					
					if (value >= sixty && value < seventy) {
						paintMe[i][j + 1] = "background:#74c476";//
						continue;
					}

					if (value >= seventy && value < eighty) {
						paintMe[i][j + 1] = "background:#41ab5d";//
						continue;
					}

					if (value >= eighty && value < ninety) {
						paintMe[i][j + 1] = "background:#8c6bb1";//
						continue;
					}
					
					if (value >= ninety && value <= max) {
						paintMe[i][j + 1] = "background:#810f7c";
						continue;
					}
				}
			} else {
				for (int j = 0; j < clusterList.size(); j++) {
					Instance in = clusterList.get(j).getCentroid();
					paintMe[i][j + 1] = "background:#f7fcb9";
					valueList.add(in.toString(i));
				}

			}
			data.put((featureList.get(i).getFeatureName()), valueList);
			valueList = new ArrayList<String>();
		}

	}

	private void getSessionGlobalVariables() {
		clusterList = (ArrayList<KMeansCluster>) Sessions.getCurrent()
				.getAttribute("finalClusterList");
		featureList = (ArrayList<Feature>) Sessions.getCurrent().getAttribute(
				"finalFeatureList");
		numOfClusters = (int) Sessions.getCurrent().getAttribute(
				"chosenNumOfClusters");
		paintMe = new String[featureList.size() + 1][clusterList.size() + 1];
	}

	private void fillColumnsModel(int endValue) {
		((List) columns_model).add(new String("Feature"));
		for (int i = 1; i <= endValue; ++i)
			((List) columns_model).add(new String("Cluster " + i));

	}

	public ListModel getColumnsModel() {
		return columns_model;
	}

	public ListModel getMapModel() {
		return data;
	}

	@Command
	public void showDiagram(
			@ContextParam(ContextType.COMPONENT) Component component) {

		Button b = (Button) component;
		String featureChosen = b.getLabel();
		Feature feat = null;
		for (int i = 0; i < featureList.size(); i++) {
			if (featureList.get(i).getFeatureName().equals(featureChosen)) {
				feat = featureList.get(i);
			}
		}
		Sessions.getCurrent().setAttribute("chosenFeature", feat);

		if (feat.getFeatureType() != 0)
			// koennte problematisch werden bei anderen Browsern
			Executions.getCurrent()
					.sendRedirect("stackedColumns.zul", "_blank");
		else
			Executions.getCurrent().sendRedirect("piechart.zul", "_blank");

	}

	@Command
	public void logOut() {
		UserCredentialManager mgmt = UserCredentialManager.getIntance(Sessions
				.getCurrent());
		mgmt.logOff();
		Executions.sendRedirect("login.zul");
	}

	@Command
	public void home() {

		Executions.sendRedirect("upload.zul");
	}

}
