package frontend;

import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.math3.stat.descriptive.rank.Percentile;
import org.zkoss.chart.model.BoxPlotModel;
import org.zkoss.chart.model.DefaultBoxPlotModel;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zul.ListModelList;

import backend.NumericFeature;


public class BoxPlotData {
    private  BoxPlotModel model;
    
    @SuppressWarnings({ "unchecked", "rawtypes" })
	public BoxPlotData(ListModelList list){
    	NumericFeature feat = (NumericFeature) Sessions.getCurrent().getAttribute(
				"chosenFeature");
    	ArrayList<ArrayList<Double>> arrayList = feat.getValuesPerCluster();
    	model = new DefaultBoxPlotModel();
    	list.add(feat.getMin());
    	list.add(feat.getMax());
    	list.add(feat.getMean());
    	list.add(feat.getStdDev());
    	Percentile p = new Percentile();
    	
		for (int i = 0; i < arrayList.size(); i++) {
			ArrayList<Double> tmp = arrayList.get(i);
			double[] tmpArr = new double[tmp.size()];
			for (int j = 0; j < tmp.size(); j++) {
				tmpArr[j]=tmp.get(j);
			}
			Arrays.sort(tmpArr);
			p.setData(tmpArr);
			model.addValue("Beobachtungen",tmpArr[0], p.evaluate(0.25),p.evaluate(0.5),p.evaluate(0.75),tmpArr[tmpArr.length-1]);
		}
    }
    
   
    
    public BoxPlotModel getBoxplotModel() {
        return model;
    }
}