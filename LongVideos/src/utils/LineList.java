package utils;

import java.awt.Color;
import java.awt.Font;
import java.text.SimpleDateFormat;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.chart.servlet.ServletUtilities;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.general.Dataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;

public class LineList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
//		pieChart();
		
		TimeSeries series=new TimeSeries("每日访问记录数量表",Movie.class);
		TimeSeriesCollection lineDataset=new TimeSeriesCollection();
//		series.add(new Movie(),1111);
		lineDataset.addSeries(series);
		//倒数第二个true设置当鼠标放上去后是否显示数据.
		JFreeChart chart=ChartFactory.createTimeSeriesChart("", "", "", lineDataset,true,true,true);
		chart.setBackgroundPaint(Color.white);
		XYPlot plot = chart.getXYPlot();
		XYItemRenderer xyitemrenderer = plot.getRenderer();
		if(xyitemrenderer instanceof XYLineAndShapeRenderer){
			XYLineAndShapeRenderer xyLineAndShapeRenderer=(XYLineAndShapeRenderer)xyitemrenderer;
			xyLineAndShapeRenderer.setBaseShapesVisible(true); 
			xyLineAndShapeRenderer.setBaseShapesFilled(true); 
		}
		//设置网格背景色
		plot.setBackgroundPaint(Color.white); 
		//网格竖线的颜色
		plot.setDomainGridlinePaint(Color.black); 
		//网格横线的颜色
		plot.setRangeGridlinePaint(Color.black); 
		//plot.setAxisOffset(new Spacer(Spacer.ABSOLUTE, 5.0, 5.0, 5.0, 5.0)); 
		//曲线颜色, 注意第一个参数的意义是：设置第四个曲线为红色，0是第一个。
		//plot.getRenderer().setSeriesPaint(3,Color.red);
		//plot.getRenderer().setSeriesPaint(2,Color.BLUE);
		//设置X轴的时间显示的形式
		DateAxis dateaxis = (DateAxis)plot.getDomainAxis(); 
		dateaxis.setDateFormatOverride(new SimpleDateFormat("dd-MM-yyyy")); 
		//数据轴的数据标签是否自动确定
		dateaxis.setAutoTickUnitSelection(true);
		//设置横轴刻度以X月X日方式显示
		//SimpleDateFormat format = new SimpleDateFormat("M月");
		//dateaxis.setDateFormatOverride(format);
		//横轴刻度为1月
		//dateaxis.setTickUnit(new DateTickUnit(DateTickUnit.MONTH,1));
		//设置子标题 
//		TextTitle subtitle = new TextTitle("2007年度", new Font("黑体", Font.BOLD, 12)); 
//		chart.addSubtitle(subtitle); 
//		//设置主标题 
//		chart.setTitle(new TextTitle("阿蜜果blog访问量统计", new Font("隶书", Font.ITALIC, 15))); 
//		chart.setAntiAlias(true); 
//		System.out.println();
//		String filename = ServletUtilities.saveChartAsPNG(chart, 800, 600, null, session); 
//		System.out.println();
//		String graphURL = request.getContextPath() + "/DisplayChart?filename=" + filename; 
//		System.out.println();
		
	}

	private static void pieChart() {
		DefaultPieDataset data=new DefaultPieDataset();
		data.setValue("2018-10-01", 20);
		data.setValue("2018-10-02", 20);
		data.setValue("2018-10-03", 20);
		data.setValue("2018-10-04", 20);
		data.setValue("2018-10-05", 20);
		//下一行代码依赖jcommon包的org.jfree.util.PublicCloneable
		JFreeChart chart=ChartFactory.createPieChart("每日访问记录数量表",data, true, true, false);
		TextTitle textTitle=chart.getTitle();
		textTitle.setFont(new Font("黑体", Font.PLAIN, 20));
		chart.getLegend().setItemFont(new Font("宋体", Font.PLAIN, 12));
		
		ChartFrame cf=new ChartFrame("每日访问记录数量表", chart);
		cf.pack();
		cf.setVisible(true);
	}

}
