package com.dashboardwms.components;

import java.io.IOException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.xml.sax.SAXException;

import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.service.XLSReadingService;
import com.dashboardwms.service.XMLConnectionService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.Background;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.ListSeries;
import com.vaadin.addon.charts.model.Pane;
import com.vaadin.addon.charts.model.PlotOptionsSolidGauge;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.addon.charts.model.YAxis;
import com.vaadin.addon.charts.model.YAxis.Stop;
import com.vaadin.addon.charts.model.style.SolidColor;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class HomePanel extends Panel {

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private XLSReadingService xlsReadingService;
	private ClienteService clienteService;
    private XMLConnectionService xmlConnectionService;

	private AplicacionService aplicacionService;
	private Date today = new Date();
    private LookupService cl;
    private String appMovil;
	private String emisora;

	private Label captionInfoPeriodo = new Label();
	private CssLayout componentGraficoPeriodo = new CssLayout();
	private Double totalSesiones = 0.0;
	private Double totalRegistros = 0.0;
	private Double totalMinutos = 0.0;
	private Double topeMinutos = 0.0;
	private Double topeSesiones = 0.0;
	private Double topeRegistros = 0.0;
	
	

	public void setXMLConnectionService(XMLConnectionService xmlConnectionService){
		this.xmlConnectionService = xmlConnectionService;
	}

	public void setAppMovil(String appMovil){
		this.appMovil = appMovil;
	}

	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	public void setXLSReadingService(XLSReadingService xlsReadingService){
		this.xlsReadingService = xlsReadingService;
	}
	
	public void setClienteService(ClienteService clienteService){
		this.clienteService = clienteService;
	}
	


	public void setAplicacionService(AplicacionService aplicacionService) {
		this.aplicacionService = aplicacionService;
	}
	
	public HomePanel() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("dashboard-view");
		setContent(root);
		Responsive.makeResponsive(root);
		root.addComponent(buildHeader());

		Component content = buildContent();
		
		root.addComponent(content);
		root.setExpandRatio(content, 1);

	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		
		Responsive.makeResponsive(header);
		Component componentUsuariosConectados = buildComponentUsuariosConectados();
header.addComponent(componentUsuariosConectados);
		return header;
	}

	private Component buildComponentUsuariosConectados(){
		final Chart chart = new Chart();
		final Configuration configuration = chart.getConfiguration();
        configuration.getChart().setType(ChartType.SOLIDGAUGE);

        configuration.getTitle().setText("Usuarios Conectados");

        Pane pane = new Pane();
        pane.setCenterXY("50%", "85%");
        pane.setSize("140%");
        pane.setStartAngle(-90);
        pane.setEndAngle(90);
        configuration.addPane(pane);

        configuration.getTooltip().setEnabled(false);

        Background bkg = new Background();
        bkg.setInnerRadius("60%");
        bkg.setOuterRadius("100%");
        bkg.setShape("arc");
        bkg.setBorderWidth(0);
        pane.setBackground(bkg);


        YAxis yaxis = configuration.getyAxis();
        yaxis.setLineWidth(0);
        yaxis.setTickInterval(200);
        yaxis.setTickWidth(0);
        yaxis.setMin(0);
        yaxis.setMax(200);
        yaxis.setTitle("");
        yaxis.getTitle().setY(-70);
        yaxis.getLabels().setY(16);
        Stop stop1 = new Stop(0.1f, SolidColor.GREEN);
        Stop stop2 = new Stop(0.5f, SolidColor.YELLOW);
        Stop stop3 = new Stop(0.9f, SolidColor.BLUE);
        yaxis.setStops(stop1, stop2, stop3);

        PlotOptionsSolidGauge plotOptions = new PlotOptionsSolidGauge();
        plotOptions.getTooltip().setValueSuffix(" Usuarios Conectados");
        Labels labels = new Labels();
        labels.setY(5);
        labels.setBorderWidth(0);
        labels.setUseHTML(true);
        labels.setFormat("{y}" + "                       Usuarios Conectados");
        plotOptions.setDataLabels(labels);
        configuration.setPlotOptions(plotOptions);

        final ListSeries series = new ListSeries("UsuariosConectados", 80);
        configuration.setSeries(series);

      
        chart.drawChart(configuration);
		return chart;
		
	}
	
	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);

		String captionPeriodo = "Cantidad de Oyentes " + Utilidades.LISTA_PERIODOS.get(0);
		String captionDispositivos = "Tipos de Conexión " + Utilidades.LISTA_PERIODOS.get(0);
	
		captionInfoPeriodo.setValue(captionPeriodo);
		Responsive.makeResponsive(componentGraficoPeriodo);
		
		dashboardPanels.addComponent(componentGraficoPeriodo);

		
		return dashboardPanels;
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

	public void fillData() throws InvalidResultSetAccessException,
			ParseException, ParserConfigurationException, SAXException, IOException {
		fillContentWrapperGraficoPeriodo(today, today);
//		fillTableMovil();
//		fillTableDia(today, today);
//		fillTablaUsuariosPaises();
//		fillTablaTiempoReal();
		
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
		Label caption = new Label();
		caption.addStyleName(ValoTheme.LABEL_H4);
		caption.addStyleName(ValoTheme.LABEL_COLORED);
		caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		caption.setValue(content.getCaption());
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
		toolbar.setComponentAlignment(caption,
				Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}




	private void fillContentWrapperGraficoPeriodo(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();

		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio, fechaFin);
 Chart vaadinChartPeriodo = new Chart();
		vaadinChartPeriodo.setHeight("100%");
		vaadinChartPeriodo.setWidth("100%");
        Configuration configuration = vaadinChartPeriodo.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);



        configuration.getxAxis().setType(AxisType.DATETIME);
       
        				
        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title(""));
        yAxis.setMin(0);

        
        configuration.setTitle("");
        configuration.getTooltip().setxDateFormat("%d-%m-%Y %H:%M");
        DataSeries ls = new DataSeries();
        ls.setPlotOptions(new PlotOptionsSpline());
        ls.setName("Oyentes Conectados");
        
		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) pairs.getKey());
			  DataSeriesItem item = new DataSeriesItem(calendar.getTimeInMillis(),
					  (Integer) pairs.getValue());
	            ls.add(item);
			it.remove();
		}
        
 

        configuration.addSeries(ls);

        vaadinChartPeriodo.drawChart(configuration);
        Responsive.makeResponsive(vaadinChartPeriodo);
		vaadinChartPeriodo.setSizeFull();
		componentGraficoPeriodo.removeAllComponents();
		componentGraficoPeriodo.setWidth("100%");
		componentGraficoPeriodo.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_H4);
		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_COLORED);
		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		vaadinChartPeriodo.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoPeriodo.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoPeriodo, true);
				} else {
					componentGraficoPeriodo.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoPeriodo, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(captionInfoPeriodo, tools);
		toolbar.setExpandRatio(captionInfoPeriodo, 1);
		toolbar.setComponentAlignment(captionInfoPeriodo, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, vaadinChartPeriodo);
		componentGraficoPeriodo.addComponent(card);
	}
    

	

    public void setLookupService(LookupService cl){
    	this.cl = cl;
    }
    
  
}

