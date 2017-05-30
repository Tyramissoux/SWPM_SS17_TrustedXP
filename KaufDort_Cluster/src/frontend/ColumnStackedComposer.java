package frontend;

import org.zkoss.chart.Charts;
import org.zkoss.chart.StackLabels;
import org.zkoss.chart.Subtitle;
import org.zkoss.chart.Theme;
import org.zkoss.chart.Title;
import org.zkoss.chart.Tooltip;
import org.zkoss.chart.XAxis;
import org.zkoss.chart.YAxis;
import org.zkoss.chart.Legend;
import org.zkoss.chart.plotOptions.ColumnPlotOptions;
import org.zkoss.json.JavaScriptValue;
import org.zkoss.zk.ui.Sessions;
import org.zkoss.zk.ui.select.SelectorComposer;
import org.zkoss.zk.ui.select.annotation.Wire;
import org.zkoss.zul.Window;
import backend.Feature;

/**
 * 
 * @author wooooot
 * 
 */
@SuppressWarnings("serial")
public class ColumnStackedComposer extends SelectorComposer<Window> {

	@Wire
	Charts chart;

	public void doAfterCompose(Window comp) throws Exception {
		super.doAfterCompose(comp);
		
		chart.setModel(new ColumnStackedData().getCategoryModel());
		Title title = chart.getTitle();
		Subtitle sTitle = chart.getSubtitle();
		sTitle.setText("gestapelte Säulen pro Featurewert");
		try{
			Feature feat = (Feature) Sessions.getCurrent().getAttribute(
				"chosenFeature");
		title.setText("Auswertung des Features \""+feat.getFeatureName()+"\"");}
		
		catch( Exception e){
			title.setText("Auswertung des Features");
		}

		YAxis yAxis = chart.getYAxis();
		yAxis.setMin(0);
		yAxis.getTitle().setText("Vorkommen");
		XAxis xAxis = chart.getXAxis();
		xAxis.getTitle().setText("Einzigartige Werte pro Feature");
		StackLabels stackLabels = yAxis.getStackLabels();
		stackLabels.setEnabled(true);
		String style = "fontWeight: bold;";
		if (!isThemeStyleSet("textColor")) {
			style += "color: gray;";
		}
		stackLabels.setStyle(style);
		
		Legend legend = chart.getLegend();
		
		legend.setAlign("right");
	
		legend.setX(0);
		legend.setVerticalAlign("top");
		legend.setY(0);
		if (!isThemeStyleSet("background2")) {
			legend.setBackgroundColor("white");
		}
		legend.setBorderColor("#CCC");
		legend.setBorderWidth(1);
		legend.setShadow(false);
		legend.setFloating(false);
		

		Tooltip tooltip = chart.getTooltip();
		tooltip.setFollowPointer(true);
		tooltip.setBackgroundColor("white");
		JavaScriptValue jsv = new JavaScriptValue("function() {return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + this.y + '<br/>' + 'Total: ' + this.point.stackTotal;}");
		tooltip.setFormatter(jsv);
		
		//System.out
		//		.println(new JavaScriptValue(
		//				"function() {return '<b>' + this.x + '</b><br/>' + this.series.name + ': ' + this.y + '<br/>' + 'Total: ' + this.point.stackTotal;}"));

		ColumnPlotOptions plotOptions = chart.getPlotOptions().getColumn();
		plotOptions.setStacking("normal");
		plotOptions.getDataLabels().setEnabled(true);
		if (!isThemeStyleSet("dataLabelsColor")) {
			plotOptions.getDataLabels().setColor("white");
		}
		plotOptions.getDataLabels().setStyle("textShadow: '0 0 3px black'");
	}

	private boolean isThemeStyleSet(String style) {
		Theme theme = chart.getTheme();
		return theme != null && theme.toString().contains(style);
	}

}
