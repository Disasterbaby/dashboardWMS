package com.dashboardwms.components;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Hour;
import org.jfree.data.time.Minute;
import org.jfree.data.time.Month;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.vaadin.addon.JFreeChartWrapper;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.service.AplicacionService;
import com.vaadin.ui.Panel;

public class DailyStatisticsPanel extends Panel{
	
	public DailyStatisticsPanel(AplicacionService aplicacionService){
		setContent(wrapperChartPaises(createDataset(aplicacionService)));
	}

	
	   private static JFreeChartWrapper wrapperChartPaises(XYDataset dataset) {
		   
		    JFreeChart createchart = createChart(dataset);
		  
		    return new JFreeChartWrapper(createchart);
			   	}
		    
	
    private static JFreeChart createChart(XYDataset dataset) {

        JFreeChart chart = ChartFactory.createTimeSeriesChart(
            null,  // title
            null,             // x-axis label
            null,   // y-axis label
            dataset,            // data
            true,               // create legend?
            true,               // generate tooltips?
            false               // generate URLs?
        );

        chart.setBackgroundPaint(Color.white);

        XYPlot plot = (XYPlot) chart.getPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.white);
        plot.setRangeGridlinePaint(Color.white);
        plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
        plot.setDomainCrosshairVisible(true);
        plot.setRangeCrosshairVisible(true);
        XYItemRenderer r = plot.getRenderer();
        if (r instanceof XYLineAndShapeRenderer) {
            XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
      
        return chart;

    }
    
    
    private static XYDataset createDataset(AplicacionService aplicacionService) {
    	
    	        TimeSeries s1 = new TimeSeries("hola");
    	        
    	        TimeSeriesCollection dataset = new TimeSeriesCollection();
    	        List<Aplicacion> listaAplicaciones =aplicacionService.getAplicacionPorFecha(new Date(), "planetafmv");
    	        for (Aplicacion aplicacion : listaAplicaciones) {
    	        	s1.add(new Minute(aplicacion.getTimestamp()), aplicacion.getConexionesActuales());
				}
    	      dataset.addSeries(s1);
    	     
    	        return dataset;
    	

   }
	
}
