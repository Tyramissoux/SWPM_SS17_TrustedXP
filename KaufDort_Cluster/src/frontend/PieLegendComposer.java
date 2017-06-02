package frontend;
import java.util.ArrayList;

import org.zkoss.chart.Chart;
import org.zkoss.chart.Charts;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.plotOptions.PiePlotOptions;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;

import backend.NumericFeature;


@SuppressWarnings("serial")
public class PieLegendComposer extends SelectorComposer<Window> {

    @Wire
    Charts chart;
    
    public void doAfterCompose(Window comp) throws Exception {
        super.doAfterCompose(comp);
        
      
        Chart chartOptional = chart.getChart();
        chartOptional.setPlotBorderWidth(0);
        chartOptional.setPlotShadow(false);
        
        chart.getTooltip().setPointFormat(
            "{series.name}: <b>{point.percentage:.1f}%</b>");
        
        PiePlotOptions plotOptions = chart.getPlotOptions().getPie();
        plotOptions.setAllowPointSelect(true);
        plotOptions.setCursor("pointer");
        plotOptions.getDataLabels().setEnabled(false);
        plotOptions.setShowInLegend(true);
        
        Series series = chart.getSeries();
        series.setType("pie");
      
        
        NumericFeature feat = (NumericFeature) Sessions.getCurrent().getAttribute(
				"chosenFeature");
        series.setName(feat.getFeatureName());
        chart.setTitle("Prozentualer Anteil an der Gesamtsummer eines Clusters für das Feature +\""+feat.getFeatureName()+"\"");
        ArrayList<ArrayList<Double>> clusterValues = feat.getValuesPerCluster();
        for (int i = 0; i < clusterValues.size(); i++) {
			ArrayList<Double> tmp = clusterValues.get(i);
			int sum = 0;
			for (int j = 0; j < tmp.size(); j++) {
				sum += tmp.get(j);
			}
			 series.addPoint(new Point("Cluster "+(i+1), sum));
		}
        
       
    }
}