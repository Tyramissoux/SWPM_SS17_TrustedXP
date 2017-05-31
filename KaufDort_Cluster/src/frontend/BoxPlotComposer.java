package frontend;

import java.util.Arrays;
import org.zkoss.chart.Charts;
import org.zkoss.chart.PlotLine;
import org.zkoss.chart.Point;
import org.zkoss.chart.Series;
import org.zkoss.chart.XAxis;
import org.zkoss.chart.YAxis;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.ListModelList;
import org.zkoss.zul.Window;

@SuppressWarnings("serial")
public class BoxPlotComposer extends SelectorComposer<Window> {

	@Wire
	Charts chart;
	
	ListModelList<Double> list;
	

	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);

		chart.getLegend().setEnabled(false);
		list = new ListModelList<Double>();
		BoxPlotData boxplot = new BoxPlotData(list);
		XAxis xAxis = chart.getXAxis();
		xAxis.setCategories("1", "2", "3", "4", "5");
		xAxis.getTitle().setText("Experiment No.");

		YAxis yAxis = chart.getYAxis();
		yAxis.getTitle().setText("Observations");
		PlotLine plotLine = new PlotLine();
		plotLine.setValue(932);
		plotLine.setColor("red");
		plotLine.setWidth(1);
		plotLine.getLabel().setText("Theoretical mean: 932");
		plotLine.getLabel().setAlign("center");
		plotLine.getLabel().setStyle("color: gray;");
		yAxis.setPlotLines(Arrays.asList(plotLine));

		chart.setModel(boxplot.getBoxplotModel());
		Series series0 = chart.getSeries(0);
		series0.getTooltip().setHeaderFormat(
				"<em>Experiment No. {point.key}</em><br/>");

		Series series1 = chart.getSeries(1);
		series1.setName("Outlier");
		series1.setColor(chart.getColors().get(0));
		series1.setType("scatter");
		series1.setData(new Point(0, 644), new Point(4, 718),
				new Point(4, 951), new Point(4, 969));
		series1.getMarker().setFillColor("white");
		series1.getMarker().setLineWidth(1);
		series1.getMarker().setLineColor(chart.getColors().get(0));
		series1.getTooltip().setPointFormat("Observation: {point.y}");
	}
	
	public ListModelList getList(){
		return list;
	}
}