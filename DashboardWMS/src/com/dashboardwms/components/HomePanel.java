package com.dashboardwms.components;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.xml.sax.SAXException;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.Axis;
import com.vaadin.addon.charts.model.AxisType;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.PlotOptionsSpline;
import com.vaadin.addon.charts.model.Title;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Window;
import com.vaadin.ui.themes.ValoTheme;

public class HomePanel extends Panel {

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
    private Aplicacion aplicacion;
	private AplicacionService aplicacionService;
	private Date today = Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime();
    
	private String emisora;

	private CssLayout componentGraficoDia = new CssLayout();

	private CssLayout componentGraficoUltimaSemana = new CssLayout();

	private CssLayout componentGraficoUltimaHora = new CssLayout();

	private CssLayout componentGrafico30Dias = new CssLayout();
	
	
	

	Label valorUsuariosConectados = new Label();	
	Label valorTopeDiaActual = new Label();
	Label valorTopeDiaAnterior = new Label();
	Label valorTope30Dias = new Label();




	public void setEmisora(String emisora) {
		this.emisora = emisora;
	}

	
	public void setAplicacion(Aplicacion aplicacion){
		this.aplicacion = aplicacion;
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
	
		header.setSpacing(true);
		header.setHeight("100px");
		header.setWidth("100%");
		Responsive.makeResponsive(header);
		Component componentUsuariosConectados = construirPanelUsuarios();
		Component componentTopeAnterior = construirPanelDiaAnterior();
		Component componentTope = construirPanelTope();
		Component componentTope30Dias = construirPanelTope30Dias();
header.addComponents(componentUsuariosConectados, componentTope, componentTopeAnterior, componentTope30Dias);
		return header;
	}

	private Panel construirPanelUsuarios(){
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		HorizontalLayout contenido = new HorizontalLayout();
		contenido.setSizeFull();
		valorUsuariosConectados.addStyleName(ValoTheme.LABEL_BOLD);
		valorUsuariosConectados.addStyleName(ValoTheme.LABEL_H2);
		valorUsuariosConectados.addStyleName(ValoTheme.LABEL_COLORED);
		valorUsuariosConectados.setSizeUndefined();
		contenido.addComponent(valorUsuariosConectados);
		contenido.setComponentAlignment(valorUsuariosConectados, Alignment.MIDDLE_CENTER);
		panel.setCaption("Usuarios Conectados");
		panel.setContent(contenido);
		
		return panel;
	}
	
	private Panel construirPanelTope30Dias(){
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		HorizontalLayout contenido = new HorizontalLayout();
		contenido.setSizeFull();
		valorTope30Dias.addStyleName(ValoTheme.LABEL_BOLD);
		valorTope30Dias.addStyleName(ValoTheme.LABEL_H2);
		valorTope30Dias.addStyleName(ValoTheme.LABEL_COLORED);
		valorTope30Dias.setSizeUndefined();
		contenido.addComponent(valorTope30Dias);
		contenido.setComponentAlignment(valorTope30Dias, Alignment.MIDDLE_CENTER);
		panel.setCaption("Pico del Mes");
		panel.setContent(contenido);
		
		return panel;
	}
	
	private Panel construirPanelTope(){
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		HorizontalLayout contenido = new HorizontalLayout();
		contenido.setSizeFull();
		valorTopeDiaActual.addStyleName(ValoTheme.LABEL_BOLD);
		valorTopeDiaActual.addStyleName(ValoTheme.LABEL_H2);
		valorTopeDiaActual.addStyleName(ValoTheme.LABEL_COLORED);
		valorTopeDiaActual.setSizeUndefined();
		contenido.addComponent(valorTopeDiaActual);
		contenido.setComponentAlignment(valorTopeDiaActual, Alignment.MIDDLE_CENTER);
		panel.setCaption("Pico de Hoy");
		panel.setContent(contenido);
		
		return panel;
	}
	
	private Panel construirPanelDiaAnterior(){
		Panel panel = new Panel();
		panel.addStyleName(ValoTheme.PANEL_WELL);
		HorizontalLayout contenido = new HorizontalLayout();
		contenido.setSizeFull();
		valorTopeDiaAnterior.addStyleName(ValoTheme.LABEL_BOLD);
		valorTopeDiaAnterior.addStyleName(ValoTheme.LABEL_H2);
		valorTopeDiaAnterior.addStyleName(ValoTheme.LABEL_COLORED);
		valorTopeDiaAnterior.setSizeUndefined();
		contenido.addComponent(valorTopeDiaAnterior);
		contenido.setComponentAlignment(valorTopeDiaAnterior, Alignment.MIDDLE_CENTER);
		panel.setCaption("Pico de Ayer");
		panel.setContent(contenido);
		
		return panel;
	}
	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);


		Responsive.makeResponsive(componentGraficoDia);
		Responsive.makeResponsive(componentGrafico30Dias);
		Responsive.makeResponsive(componentGraficoUltimaHora);

		Responsive.makeResponsive(componentGraficoUltimaSemana);
		

		dashboardPanels.addComponent(componentGraficoUltimaHora);
		dashboardPanels.addComponent(componentGraficoDia);

		dashboardPanels.addComponent(componentGraficoUltimaSemana);
		dashboardPanels.addComponent(componentGrafico30Dias);
		
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
		fillContentWrapperGraficoDia(today, today);
		
		
		fillContentWrapperGraficoSemana();
		fillContentWrapperGrafico30Dias();
		fillContentWrapperGraficoUltimaHora();
		fillPanels();
		
	}


	
private void fillPanels(){

	

		List<Cliente> listaClientes = new ArrayList<Cliente>();
		if(aplicacion!=null)
		listaClientes = aplicacion.getListaClientes();

		valorUsuariosConectados.setValue(listaClientes.size()+"");

	
	valorTopeDiaActual.setValue(aplicacionService.getPicoUsuariosRangoFecha(emisora, today, today).toString());
	Date fechaFin = today;
	
	Calendar fechaInicio = Calendar.getInstance();
	fechaInicio.setTime(fechaFin);
	fechaInicio.add(Calendar.DATE, -1);
	valorTopeDiaAnterior.setValue(aplicacionService.getPicoUsuariosRangoFecha(emisora, fechaInicio.getTime(), fechaInicio.getTime()).toString());
	fechaInicio.setTime(fechaFin);
	fechaInicio.add(Calendar.DATE, -30);
	valorTope30Dias.setValue(aplicacionService.getPicoUsuariosRangoFecha(emisora, fechaInicio.getTime(), fechaFin).toString());
}



	private void fillContentWrapperGraficoDia(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();

		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio, fechaFin);
 Chart vaadinChartPeriodo = new Chart();
		vaadinChartPeriodo.setHeight("100%");
		vaadinChartPeriodo.setWidth("100%");
      final  Configuration configuration = vaadinChartPeriodo.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);



        configuration.getxAxis().setType(AxisType.DATETIME);
       
        				
        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title(""));
        yAxis.setMin(0);

        
        configuration.setTitle("");
        configuration.getTooltip().setxDateFormat("%d-%m-%Y %I:%M %P");
        DataSeries ls = new DataSeries();
        ls.setPlotOptions(new PlotOptionsSpline());
        ls.setName("Oyentes Conectados");
        
		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) pairs.getKey());
			  DataSeriesItem item = new DataSeriesItem(calendar.getTime(),
					  (Integer) pairs.getValue());
	            ls.add(item);
			it.remove();
		}
        
 

        configuration.addSeries(ls);

        vaadinChartPeriodo.drawChart(configuration);
        Responsive.makeResponsive(vaadinChartPeriodo);
		vaadinChartPeriodo.setSizeFull();
		componentGraficoDia.removeAllComponents();
		componentGraficoDia.setWidth("100%");
		componentGraficoDia.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");
final Label caption = new Label("Usuarios Conectados Hoy");
caption.addStyleName(ValoTheme.LABEL_H4);
caption.addStyleName(ValoTheme.LABEL_COLORED);
caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		vaadinChartPeriodo.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				generarPDF(configuration, caption.getValue());
			}
		});
		print.setStyleName("icon-only");
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoDia.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoDia, true);
				} else {
					componentGraficoDia.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoDia, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, vaadinChartPeriodo);
		componentGraficoDia.addComponent(card);
	}
    


	private void fillContentWrapperGraficoSemana()
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();
		Date fechaFin = today;
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);

		fechaInicio.add(Calendar.DATE, -7);
		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio.getTime(), fechaFin);
final Chart vaadinChartPeriodo = new Chart();
 
		vaadinChartPeriodo.setHeight("100%");
		vaadinChartPeriodo.setWidth("100%");
        final Configuration configuration = vaadinChartPeriodo.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);


        //configuration.setExporting(true);
        configuration.getxAxis().setType(AxisType.DATETIME);
       
        				
        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title(""));
        yAxis.setMin(0);

        
        configuration.setTitle("");
        configuration.getTooltip().setxDateFormat("%d-%m-%Y %I:%M %P");
        DataSeries ls = new DataSeries();
        ls.setPlotOptions(new PlotOptionsSpline());
        ls.setName("Oyentes");
        
		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) pairs.getKey());
			  DataSeriesItem item = new DataSeriesItem(calendar.getTime(),
					  (Integer) pairs.getValue());
	            ls.add(item);
			it.remove();
		}
        
 

        configuration.addSeries(ls);

        vaadinChartPeriodo.drawChart(configuration);
        Responsive.makeResponsive(vaadinChartPeriodo);
		vaadinChartPeriodo.setSizeFull();
		componentGraficoUltimaSemana.removeAllComponents();
		componentGraficoUltimaSemana.setWidth("100%");
		componentGraficoUltimaSemana.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

final Label caption = new Label("Usuarios Conectados Últimos 7 Días");
caption.addStyleName(ValoTheme.LABEL_H4);
caption.addStyleName(ValoTheme.LABEL_COLORED);
caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		vaadinChartPeriodo.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		final MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				
				
				generarPDF(configuration, caption.getValue());
			}
		});
		print.setStyleName("icon-only");
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoUltimaSemana.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoUltimaSemana, true);
				} else {
					componentGraficoUltimaSemana.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoUltimaSemana, false);
				}
			}
		});
		max.setStyleName("icon-only");
	
		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, vaadinChartPeriodo);
		componentGraficoUltimaSemana.addComponent(card);

	}
    
	private void fillContentWrapperGrafico30Dias()
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();
		Date fechaFin = today;
	
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		fechaInicio.add(Calendar.DATE, -30);
		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio.getTime(), fechaFin);
 final Chart vaadinChartPeriodo = new Chart();
		vaadinChartPeriodo.setHeight("100%");
		vaadinChartPeriodo.setWidth("100%");
       final Configuration configuration = vaadinChartPeriodo.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);


//configuration.setExporting(true);
        configuration.getxAxis().setType(AxisType.DATETIME);
       
        				
        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title(""));
        yAxis.setMin(0);

        
        configuration.setTitle("");
        configuration.getTooltip().setxDateFormat("%d-%m-%Y %I:%M %P");
        DataSeries ls = new DataSeries();
        ls.setPlotOptions(new PlotOptionsSpline());
        ls.setName("Oyentes");
        
		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) pairs.getKey());
			  DataSeriesItem item = new DataSeriesItem(calendar.getTime(),
					  (Integer) pairs.getValue());
	            ls.add(item);
			it.remove();
		}
        
 

        configuration.addSeries(ls);

        vaadinChartPeriodo.drawChart(configuration);
        Responsive.makeResponsive(vaadinChartPeriodo);
		vaadinChartPeriodo.setSizeFull();
		componentGrafico30Dias.removeAllComponents();
		componentGrafico30Dias.setWidth("100%");
		componentGrafico30Dias.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");
final Label caption = new Label("Usuarios Conectados Últimos 30 Días");
caption.addStyleName(ValoTheme.LABEL_H4);
caption.addStyleName(ValoTheme.LABEL_COLORED);
caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		vaadinChartPeriodo.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				
				generarPDF(configuration, caption.getValue());
			}
		});
		print.setStyleName("icon-only");
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGrafico30Dias.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGrafico30Dias, true);
				} else {
					componentGrafico30Dias.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGrafico30Dias, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, vaadinChartPeriodo);
		componentGrafico30Dias.addComponent(card);
	}
	
	
	private void fillContentWrapperGraficoUltimaHora()
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();
		Date fechaFin = today;
		
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		fechaInicio.add(Calendar.HOUR, -1);
		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio.getTime(), fechaFin);
final Chart vaadinChartPeriodo = new Chart();
		vaadinChartPeriodo.setHeight("100%");
		vaadinChartPeriodo.setWidth("100%");
       final Configuration configuration = vaadinChartPeriodo.getConfiguration();
        configuration.getChart().setType(ChartType.SPLINE);


        configuration.getxAxis().setType(AxisType.DATETIME);
       
        				
        Axis yAxis = configuration.getyAxis();
        yAxis.setTitle(new Title(""));
        yAxis.setMin(0);

        
        configuration.setTitle("");
        configuration.getTooltip().setxDateFormat("%d-%m-%Y %I:%M %P");
        DataSeries ls = new DataSeries();
        ls.setPlotOptions(new PlotOptionsSpline());
        ls.setName("Oyentes");
        
		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime((Date) pairs.getKey());
			  DataSeriesItem item = new DataSeriesItem(calendar.getTime(),
					  (Integer) pairs.getValue());
	            ls.add(item);
			it.remove();
		}
        
 

        configuration.addSeries(ls);

        vaadinChartPeriodo.drawChart(configuration);
        Responsive.makeResponsive(vaadinChartPeriodo);
		vaadinChartPeriodo.setSizeFull();
		componentGraficoUltimaHora.removeAllComponents();
		componentGraficoUltimaHora.setWidth("100%");
		componentGraficoUltimaHora.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");
final Label caption = new Label("Oyentes Conectados Última Hora");
caption.addStyleName(ValoTheme.LABEL_H4);
caption.addStyleName(ValoTheme.LABEL_COLORED);
caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		vaadinChartPeriodo.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
		MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				generarPDF(configuration, caption.getValue());
			}
		});
		print.setStyleName("icon-only");
		
		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoUltimaHora.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGrafico30Dias, true);
				} else {
					componentGraficoUltimaHora.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGrafico30Dias, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(caption, tools);
		toolbar.setExpandRatio(caption, 1);
		toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, vaadinChartPeriodo);
		componentGraficoUltimaHora.addComponent(card);
	}
	

	
	
	private void generarPDF(final Configuration conf, final String titulo)
	{

	Embedded pdf = Utilidades.buildPDF(conf, titulo);
	Window subWindow = new Window();
	subWindow.setSizeFull();
	subWindow.setModal(true);
	subWindow.setCaption(null);
	VerticalLayout subContent = new VerticalLayout();
	subContent.setMargin(false);
	subContent.setSizeFull();
	subWindow.setContent(subContent);
	pdf.setSizeFull();
	subContent.addComponent(pdf);
//
//	// Center it in the browser window
	subWindow.center();
//
//	// Open it in the UI
	getUI().addWindow(subWindow);
	
	}
    
  
}

