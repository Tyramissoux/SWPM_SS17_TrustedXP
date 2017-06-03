package frontend;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.zkoss.bind.annotation.AfterCompose;
import org.zkoss.bind.annotation.Command;
import org.zkoss.bind.annotation.ContextParam;
import org.zkoss.bind.annotation.ContextType;
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

	@Command
	public void colourMe() {

		for (int i = 0; i < paintMe.length; i++) {
			for (int j = 0; j < paintMe[i].length; j++) {
				
					Cell c = (Cell) grid.getCell(i, j);
					if (c.getStyle() != null)
						c.setStyle(null);
					else
						c.setStyle(paintMe[i][j+1]);
				
			}
		}

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
				percentile.setData(featureValuesArr);
				double min = percentile.evaluate(0);
				double first = percentile.evaluate(25);
				double second = percentile.evaluate(50);
				double third = percentile.evaluate(75);
				double max = percentile.evaluate(100);

				for (int j = 0; j < clusterList.size(); j++) {
					Instance in = clusterList.get(j).getCentroid();
					String centroidVal = in.toString(i);
					double value = Double.valueOf(centroidVal);

					if (value >= min && value < first)
						paintMe[i][j + 1] = "background:#ffffcc";

					else if (value >= first && value < second)
						paintMe[i][j + 1] = "background:#ffddcc";

					else if (value >= second && value < third)
						paintMe[i][j + 1] = "background:#ffad99";

					else if (value >= third && value <= max)
						paintMe[i][j + 1] = "background:ff471a";

					valueList.add(centroidVal);

				}
			} else {
				for (int j = 0; j < clusterList.size(); j++) {
					Instance in = clusterList.get(j).getCentroid();
					paintMe[i ][j + 1] = null;
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
