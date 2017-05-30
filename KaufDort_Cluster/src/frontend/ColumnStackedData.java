package frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import org.zkoss.chart.model.CategoryModel;
import org.zkoss.chart.model.DefaultCategoryModel;
import org.zkoss.zk.ui.Sessions;

import backend.Feature;

public class ColumnStackedData {
	private CategoryModel model;

	public ColumnStackedData() {
		Feature feat = (Feature) Sessions.getCurrent().getAttribute(
				"chosenFeature");
		ArrayList<HashMap<String, Integer>> mapList = feat.getAllNominalClusterData();
		//ArrayList<String> unique = feat.getUniqueItem();

		model = new DefaultCategoryModel();
		for (int i = 0; i < mapList.size(); i++) {
			HashMap<String,Integer> tmp = mapList.get(i);
			for(Entry<String,Integer>e : tmp.entrySet())
			model.setValue("Cluster "+i, e.getKey(), e.getValue());
		}
		
	}

	public  CategoryModel getCategoryModel() {
		return model;
	}

}
