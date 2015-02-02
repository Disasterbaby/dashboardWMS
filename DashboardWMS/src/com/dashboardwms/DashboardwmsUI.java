package com.dashboardwms;



import java.awt.Color;
import java.awt.GradientPaint;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.chart.renderer.xy.XYAreaRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.statistics.Regression;
import org.jfree.data.xy.DefaultTableXYDataset;
import org.jfree.data.xy.DefaultXYDataset;
import org.jfree.data.xy.XYSeries;
import org.springframework.context.annotation.Scope;
import org.vaadin.addon.JFreeChartWrapper;
import org.springframework.stereotype.Component;

import ru.xpoft.vaadin.DiscoveryNavigator;







import com.vaadin.annotations.Theme;
import com.vaadin.annotations.Title;
import com.vaadin.navigator.Navigator;
import com.vaadin.navigator.ViewChangeListener;
import com.vaadin.navigator.ViewChangeListener.ViewChangeEvent;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.VaadinRequest;
import com.vaadin.server.Page.BrowserWindowResizeEvent;
import com.vaadin.server.Page.BrowserWindowResizeListener;
import com.vaadin.ui.Button;
import com.vaadin.ui.Button.ClickEvent;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.UI;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.views.*;

@SuppressWarnings("serial")
@Theme("dashboard")
@Title("PlugStreaming")
@Component("DashboardwmsUI")
@Scope("prototype")
public class DashboardwmsUI extends UI {

        private Navigator navigator;

        public static final String LOGINVIEW = "";
        public static final String MAINVIEW = "form";
        public static final String ERRORVIEW = "error";

       
       
        @Override
        protected void init(VaadinRequest request) {
               
                UI.getCurrent().setLocale(new Locale("es"));
        
                
                
                Responsive.makeResponsive(this);
        navigator = new DiscoveryNavigator(this, this);
               
                System.out.println("despues de discovery navigator");
                navigator.setErrorView(ErrorView.class);
                navigator.navigateTo(MAINVIEW);
        }
}

//				
//		final XMLReader xmlReader = new XMLReader();
//		final TiempoRealService tiempoRealService = new TiempoRealService();
//		final GeoLocalization geoLocation = new GeoLocalization();
//		final 
//		ComboBox cboxAplicaciones = new ComboBox("Aplicaciones");
//	
//
//		try{
//		final LookupService cl = new LookupService(Utilidades.DB_LOCATION,LookupService.GEOIP_MEMORY_CACHE);
//		final VerticalLayout layout = new VerticalLayout();
//		layout.setSizeFull();
//		layout.setMargin(true);
//		setContent(layout);
//	
//	
//		Button button = new Button("Prueba");
//		button.addClickListener(new Button.ClickListener() {
//			public void buttonClick(ClickEvent event) {
	//			try {
//					
	//	layout.addComponent(createBasicDemo());
//		setContent(createBasicDemo());
		
//
		//layout.addComponent(getLevelChart());
//
	//	layout.addComponent(regressionChart());
//					
//					Servidor servidor;
//					
//						servidor = xmlReader.getXML("http://s1.serverht.net:8086/serverinfo");
//				
//					BeanItemContainer<Aplicacion> listaAplicaciones = new BeanItemContainer<Aplicacion>(Aplicacion.class);
//					Aplicacion aplicacionTodas = new Aplicacion();
//					aplicacionTodas.setNombre("Todas");
//					aplicacionTodas.setListaClientes( tiempoRealService.getTodosClientes(servidor));
//					listaAplicaciones.addItemAt(0, aplicacionTodas);
//					listaAplicaciones.addAll(servidor.getListaAplicaciones());
//					cboxAplicaciones.setContainerDataSource(listaAplicaciones);
//					cboxAplicaciones.setItemCaptionPropertyId("nombre");
//					cboxAplicaciones.setImmediate(true);
//					cboxAplicaciones.setNullSelectionAllowed(false);
//					cboxAplicaciones.setInvalidAllowed(false);
//					cboxAplicaciones.setValue(aplicacionTodas);
//					geoLocation.setLocalization(cl, aplicacionTodas.getListaClientes());
//					geoLocation.setWidth("80%");
//					geoLocation.setHeight("80%");
//					layout.addComponent(cboxAplicaciones);
//					layout.addComponent(geoLocation);	
//					layout.setComponentAlignment(geoLocation, Alignment.MIDDLE_CENTER);
//					layout.setExpandRatio(geoLocation, 1f);
//					
//					cboxAplicaciones.addValueChangeListener(new ValueChangeListener() {
//						
//						@Override
//						public void valueChange(ValueChangeEvent event) {
//							Aplicacion aplicacionSeleccionada = (Aplicacion)cboxAplicaciones.getValue();
//						
//							geoLocation.setLocalization(cl, aplicacionSeleccionada.getListaClientes());
//						}
//					});
//					
//				} catch (ParserConfigurationException
//					| SAXException | IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//				Conexion conexion = new Conexion();
//				try {
//					conexion.probar();
//				} catch (ClassNotFoundException | SQLException e) {
//				// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
					
//				} catch (ParserConfigurationException | SAXException
//						| IOException e) {
//					// TODO Auto-generated catch block
//					e.printStackTrace();
//				}
//			}
					
//		});
//		layout.addComponent(button);
//		}
//		catch (Exception e) {
//			// TODO: handle exception
//		}
//	}

//	   public static JFreeChartWrapper createBasicDemo() {
//	        JFreeChart createchart = createchart(createDataset());
//	        return new JFreeChartWrapper(createchart);
//	    }
//	   
//	   /**
//	     * Returns a sample dataset.
//	     * 
//	     * @return The dataset.
//	     */
//	    private static CategoryDataset createDataset() {
//
//	        // row keys...
//	        String y2009 = "2009";
////	        String y2008 = "2008";
////	        String y2007 = "2007";
//
//	        // column keys...
//	        String under5 = "Nov 03";
//	        String between5_9 = "Nov 09";
//	        String between10_14 = "Nov 14";
//	        String between15_19 = "Nov 20";
//	        String between20_24 = "20-24";
//
//	        // create the dataset...
//	        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
//
//	        dataset.addValue(10, y2009, under5);
//	        dataset.addValue(2, y2009, between5_9);
//	        dataset.addValue(11, y2009, between10_14);
//	        dataset.addValue(12, y2009, between15_19);
//	        dataset.addValue(13, y2009, between20_24);
//
////	        dataset.addValue(21005852, y2008, under5);
////	        dataset.addValue(20065249, y2008, between5_9);
////	        dataset.addValue(20054627, y2008, between10_14);
////	        dataset.addValue(21514358, y2008, between15_19);
////	        dataset.addValue(21058981, y2008, between20_24);
//
////	        dataset.addValue(20724125, y2007, under5);
////	        dataset.addValue(19849628, y2007, between5_9);
////	        dataset.addValue(20314309, y2007, between10_14);
////	        dataset.addValue(21473690, y2007, between15_19);
////	        dataset.addValue(21032396, y2007, between20_24);
//
//	        return dataset;
//
//	    }
//	    
//	    
//
//	
//	    
//	    /**
//	     * Creates a sample chart.
//	     * 
//	     * @param dataset
//	     *            the dataset.
//	     * 
//	     * @return The chart.
//	     */
//	    private static JFreeChart createchart(CategoryDataset dataset) {
//
//	        // create the chart...
//	        JFreeChart chart = ChartFactory.createLineChart("Bar Chart Demo 1", // chart
//	                // title
//	                "Age group", // domain axis label
//	                "Number of members", // range axis label
//	                dataset, // data
//	                PlotOrientation.VERTICAL, // orientation
//	                true, // include legend
//	                true, // tooltips?
//	                false // URLs?
//	                );
//
//	        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...
//
//	        // set the background color for the chart...
//	        chart.setBackgroundPaint(Color.white);
//
//	        // get a reference to the plot for further customisation...
//	        CategoryPlot plot = (CategoryPlot) chart.getPlot();
//	        plot.setBackgroundPaint(Color.WHITE);
//	        plot.setDomainGridlinePaint(Color.LIGHT_GRAY);
//	        plot.setDomainGridlinesVisible(true);
//	        plot.setRangeGridlinePaint(Color.white);
//
//	        // ******************************************************************
//	        // More than 150 demo applications are included with the JFreeChart
//	        // Developer Guide...for more information, see:
//	        //
//	        // > http://www.object-refinery.com/jfreechart/guide.html
//	        //
//	        // ******************************************************************
//
//	        // set the range axis to display integers only...
//	        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
//	        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
//
//	        // disable bar outlines...
//	        LineAndShapeRenderer renderer = (LineAndShapeRenderer) plot.getRenderer();
//	        // renderer.setDrawBarOutline(false);
//
//	        // set up gradient paints for series...
//	        GradientPaint gp0 = new GradientPaint(0.0f, 0.0f, Color.blue, 0.0f,
//	                0.0f, new Color(0, 0, 64));
//	        GradientPaint gp1 = new GradientPaint(0.0f, 0.0f, Color.green, 0.0f,
//	                0.0f, new Color(0, 64, 0));
//	        GradientPaint gp2 = new GradientPaint(0.0f, 0.0f, Color.red, 0.0f,
//	                0.0f, new Color(64, 0, 0));
//	        renderer.setSeriesPaint(0, gp0);
//	        renderer.setSeriesPaint(1, gp1);
//	        renderer.setSeriesPaint(2, gp2);
//
//	        CategoryAxis domainAxis = plot.getDomainAxis();
//	        domainAxis.setCategoryLabelPositions(CategoryLabelPositions
//	                .createUpRotationLabelPositions(Math.PI / 6.0));
//	        // OPTIONAL CUSTOMISATION COMPLETED.
//
//	        return chart;
//
//	    }
//
//	    private static Component getLevelChart() {
//        DefaultTableXYDataset ds = new DefaultTableXYDataset();
//	        NumberAxis y = new NumberAxis("Sales in thousands of $");
//
//	        XYSeries series;
//	        Calendar cal = Calendar.getInstance();
//	        cal.set(Calendar.YEAR, 2006);
//	        long y2007 = cal.getTimeInMillis();
//	        cal.set(Calendar.YEAR, 2007);
//	        long y2008 = cal.getTimeInMillis();
//	        cal.set(Calendar.YEAR, 2008);
//	        long y2009 = cal.getTimeInMillis();
//
//	        series = new XYSeries("Asia", false, false);
//	        series.add(y2007, 130942);
//	        series.add(y2008, 78730);
//	        series.add(y2009, 86895);
//	        ds.addSeries(series);
//
//	        series = new XYSeries("Europe", false, false);
//	        series.add(y2007, 207740);
//	        series.add(y2008, 152144);
//	        series.add(y2009, 130942);
//	        ds.addSeries(series);
//
//	        series = new XYSeries("USA", false, false);
//	        series.add(y2007, 217047);
//	        series.add(y2008, 129870);
//	        series.add(y2009, 174850);
//	        ds.addSeries(series);
//
//	        // Paint p = new Color(0, 0, 0, Color.OPAQUE);
//	        // r.setSeriesPaint(0, p);
//	        // BasicStroke s = new BasicStroke(2);
//	        // r.setSeriesStroke(0, s);
//
//	        DateAxis x = new DateAxis("Year");
//	        x.setDateFormatOverride(new SimpleDateFormat("yyyy"));
//	        x.setTickUnit(new DateTickUnit(DateTickUnitType.YEAR, 1));
//
//	        XYPlot plot2 = new XYPlot(ds, x, y, new XYAreaRenderer(
//	                XYAreaRenderer.AREA_AND_SHAPES));
//	        plot2.setForegroundAlpha(0.5f);
//
//	        JFreeChart c = new JFreeChart(plot2);
//
//	        return new JFreeChartWrapper(c);
//	    }
//
//	    private static Component regressionChart() {
//
//	        DefaultTableXYDataset ds = new DefaultTableXYDataset();
//
//	        XYSeries series;
//
//	        series = new XYSeries("Measured difference", false, false);
//	        series.add(1, 1);
//	        series.add(2, 4);
//	        series.add(3, 6);
//	        series.add(4, 9);
//	        series.add(5, 9);
//	        series.add(6, 11);
//	        ds.addSeries(series);
//
//	        JFreeChart scatterPlot = ChartFactory.createScatterPlot("Regression",
//	                "cm", "Measuring checkpoint", ds, PlotOrientation.HORIZONTAL,
//	                true, false, false);
//
//	        XYPlot plot = (XYPlot) scatterPlot.getPlot();
//
//	        double[] regression = Regression.getOLSRegression(ds, 0);
//
//	        // regression line points
//
//	        double v1 = regression[0] + regression[1] * 1;
//	        double v2 = regression[0] + regression[1] * 6;
//
//	        DefaultXYDataset ds2 = new DefaultXYDataset();
//	        ds2.addSeries("regline", new double[][] { new double[] { 1, 6 },
//	                new double[] { v1, v2 } });
//	        plot.setDataset(1, ds2);
//	        plot.setRenderer(1, new XYLineAndShapeRenderer(true, false));
//
//	        JFreeChart c = new JFreeChart(plot);
//
//	        return new JFreeChartWrapper(c);
//	    }

//}