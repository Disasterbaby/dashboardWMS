package com.dashboardwms.components;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.vaadin.addon.JFreeChartWrapper;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class DailyStatisticsPanel extends Panel{
	
	
	private CssLayout dashboardPanels;
    private final VerticalLayout root;
	public DateField dfFechaInicio = new DateField();
	public DateField dfFechaFin = new DateField();
	private CheckBox cbRango = new CheckBox("Rango de Fechas");
    private CssLayout componentGraficoDiario =  new CssLayout();
	private AplicacionService aplicacionService;
	private ClienteService clienteService;
	private Date today = new Date();
	
	
	Calendar calendar = Calendar.getInstance();
	
	public DailyStatisticsPanel(){
		
		calendar.setTime(today);
		calendar.add(Calendar.DATE, -30);
		
        addStyleName(ValoTheme.PANEL_BORDERLESS);
        setSizeFull();
        root = new VerticalLayout();
        root.setSizeFull();
        root.setMargin(true);
        root.addStyleName("dashboard-view");
        setContent(root);
        Responsive.makeResponsive(root);
        Responsive.makeResponsive(componentGraficoDiario);
        root.addComponent(buildHeader());
     
    	 	  Component content = buildContent();
    	        root.addComponent(content);
    	        root.setExpandRatio(content, 1);
    	     
   
	}

	public void setAplicacionService(AplicacionService aplicacionService){
		this.aplicacionService = aplicacionService;
	}
	
	public void setClienteService(ClienteService clienteService){
		this.clienteService = clienteService;
	}
	
    private Component buildHeader() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
        buildFilter();
        Label title = new Label("Oyentes Diarios");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        HorizontalLayout tools = new HorizontalLayout(cbRango, dfFechaInicio, dfFechaFin);
        tools.setComponentAlignment(cbRango, Alignment.MIDDLE_CENTER);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);
        
        return header;
    }

    private void buildFilter() {
    	  dfFechaInicio.setValue(today);
          dfFechaInicio.setDateFormat("dd-MM-yyyy");
          dfFechaInicio.setImmediate(true);
          dfFechaInicio.setRangeEnd(today);
          dfFechaInicio.setRangeStart(calendar.getTime());
          dfFechaFin.setValue(today);
          dfFechaFin.setDateFormat("dd-MM-yyyy");
          dfFechaFin.setImmediate(true);
          dfFechaFin.setRangeStart(calendar.getTime());
          dfFechaFin.setRangeEnd(today);
          dfFechaFin.setVisible(false);
          
          dfFechaInicio.addValueChangeListener(dfChangeListener);
          dfFechaFin.addValueChangeListener(dfChangeListener);
          
          
          cbRango.addValueChangeListener(new ValueChangeListener() {
  			
  			@Override
  			public void valueChange(ValueChangeEvent event) {
  				Boolean valor = (Boolean) event.getProperty().getValue();
  				if (valor)
  					dfFechaFin.setVisible(true);
  				else
  					dfFechaFin.setVisible(false);
  				
  				
  			}
  		});;
	
    	
    }
	


	    private Component buildContent() {
	        dashboardPanels = new CssLayout();
	        dashboardPanels.addStyleName("dashboard-panels");
	        Responsive.makeResponsive(dashboardPanels);

	        
	        dashboardPanels.addComponent(componentGraficoDiario);
	     

	        return dashboardPanels;
	    }
	    
	    
	    private void fillContentWrapperGraficoMinutos(Date fechaInicio, Date fechaFin){
	    	List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();
	    	System.out.println("fecha inicio " + fechaInicio);
	       	if(fechaFin == null)
	    		listaAplicaciones = aplicacionService.getAplicacionPorFecha(fechaInicio, "planetafm");
	    	else{
	    		listaAplicaciones = aplicacionService.getAplicacionRangoFechas("planetafm", fechaInicio, fechaFin);
	    	 	System.out.println("avg " + clienteService.getAvgMinutosRangoFecha("planetafm", fechaInicio, fechaFin));
	    		System.out.println("total " + clienteService.getCantidadMinutosRangoFecha("planetafm", fechaInicio, fechaFin));
	    	}
	    	
	      	XYDataset datasetMinutosEscuchados = datasetDiario(listaAplicaciones);
	      	Component chartCantidadMinutos = wrapperChartDiario(datasetMinutosEscuchados);
	    	Responsive.makeResponsive(chartCantidadMinutos);
	    	chartCantidadMinutos.setCaption("Minutos Conectados");
	    	chartCantidadMinutos.setSizeFull();
	    	componentGraficoDiario.removeAllComponents();
	    	componentGraficoDiario.setWidth("100%");
	    	componentGraficoDiario.addStyleName("dashboard-panel-slot");

	        CssLayout card = new CssLayout();
	        card.setWidth("100%");
	        card.addStyleName(ValoTheme.LAYOUT_CARD);

	        HorizontalLayout toolbar = new HorizontalLayout();
	        toolbar.addStyleName("dashboard-panel-toolbar");
	        toolbar.setWidth("100%");

	        Label caption = new Label(chartCantidadMinutos.getCaption());
	        caption.addStyleName(ValoTheme.LABEL_H4);
	        caption.addStyleName(ValoTheme.LABEL_COLORED);
	        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	        chartCantidadMinutos.setCaption(null);

	        MenuBar tools = new MenuBar();
	        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	       
	        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                if (!componentGraficoDiario.getStyleName().contains("max")) {
	                    selectedItem.setIcon(FontAwesome.COMPRESS);
	                    toggleMaximized(componentGraficoDiario, true);
	                } else {
	                	componentGraficoDiario.removeStyleName("max");
	                    selectedItem.setIcon(FontAwesome.EXPAND);
	                    toggleMaximized(componentGraficoDiario, false);
	                }
	            }
	        });
	        max.setStyleName("icon-only");
	   
	    

	        toolbar.addComponents(caption, tools);
	        toolbar.setExpandRatio(caption, 1);
	        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

	        card.addComponents(toolbar, chartCantidadMinutos);
	        componentGraficoDiario.addComponent(card);
	    }
	    
	    private Component createContentWrapper(final Component content) {
	        final CssLayout slot = new CssLayout();
	        slot.setWidth("100%");
	        slot.addStyleName("dashboard-panel-slot");

	        CssLayout card = new CssLayout();
	        card.setWidth("100%");
	        card.addStyleName(ValoTheme.LAYOUT_CARD);

	        HorizontalLayout toolbar = new HorizontalLayout();
	        toolbar.addStyleName("dashboard-panel-toolbar");
	        toolbar.setWidth("100%");

	        Label caption = new Label(content.getCaption());
	        caption.addStyleName(ValoTheme.LABEL_H4);
	        caption.addStyleName(ValoTheme.LABEL_COLORED);
	        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
	        content.setCaption(null);

	        MenuBar tools = new MenuBar();
	        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
	       
	        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

	            @Override
	            public void menuSelected(final MenuItem selectedItem) {
	                if (!slot.getStyleName().contains("max")) {
	                    selectedItem.setIcon(FontAwesome.COMPRESS);
	                    toggleMaximized(slot, true);
	                } else {
	                    slot.removeStyleName("max");
	                    selectedItem.setIcon(FontAwesome.EXPAND);
	                    toggleMaximized(slot, false);
	                }
	            }
	        });
	        max.setStyleName("icon-only");
	   
	    

	        toolbar.addComponents(caption, tools);
	        toolbar.setExpandRatio(caption, 1);
	        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

	        card.addComponents(toolbar, content);
	        slot.addComponent(card);
	        return slot;
	    }
	    
	    
	    private void toggleMaximized(final Component panel, final boolean maximized) {
	        for (Iterator<Component> it = root.iterator(); it.hasNext();) {
	            it.next().setVisible(!maximized);
	        }
	        dashboardPanels.setVisible(true);

	        for (Iterator<Component> it = dashboardPanels.iterator(); it.hasNext();) {
	            Component c = it.next();
	            c.setVisible(!maximized);
	        }

	        if (maximized) {
	            panel.setVisible(true);
	            panel.addStyleName("max");
	        } else {
	            panel.removeStyleName("max");
	        }
	    }
	    
	    
		   private static JFreeChartWrapper wrapperChartDiario(XYDataset dataset) {
			   
			    JFreeChart createchart = chartDiario(dataset);
			  
			    return new JFreeChartWrapper(createchart);
				   	}
			    
	   
	
    private static JFreeChart chartDiario(XYDataset dataset) {

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
            renderer.setPaint(Color.blue);
            renderer.setBaseShapesVisible(true);
            renderer.setBaseShapesFilled(true);
            renderer.setDrawSeriesLineAsPath(true);
        }

        DateAxis axis = (DateAxis) plot.getDomainAxis();
        axis.setDateFormatOverride(new SimpleDateFormat("HH:mm"));
        axis.setAutoRange(true);
        
        ValueAxis numberAxis = plot.getRangeAxis();
        numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());
      
        return chart;

    }
    
    
    private  XYDataset datasetDiario(List<Aplicacion> listaAplicaciones) {
    	
    	        TimeSeries s1 = new TimeSeries("nombre", "domain", "range");
    	        
    	        TimeSeriesCollection dataset = new TimeSeriesCollection();
    	        for (Aplicacion aplicacion : listaAplicaciones) {
    	        	s1.add(new Minute(aplicacion.getTimestamp()), aplicacion.getConexionesActuales());
				}
    	      dataset.addSeries(s1);
    	     
    	        return dataset;
    	

   }
    
    
    public ValueChangeListener dfChangeListener = new ValueChangeListener() {
		
		@Override
		public void valueChange(ValueChangeEvent event) {
		 	Date fechaInicio = dfFechaInicio.getValue();
		 	Date fechaFin = null;
		 	if(dfFechaFin.isVisible())
		 	 fechaFin = dfFechaFin.getValue();
		 	
		 	fillData(fechaInicio, fechaFin);
			
		}
	};
    
    public void fillData(Date fechaInicio, Date fechaFin){
    	
    	fillContentWrapperGraficoMinutos(fechaInicio, fechaFin);
    	
    }
	
}
