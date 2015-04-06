package com.dashboardwms.components;

import java.awt.Color;
import java.awt.Font;
import java.awt.Shape;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.time.Day;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.vaadin.addon.JFreeChartWrapper;

import com.dashboardwms.service.XLSReadingService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class MobileStatisticsPanel extends Panel {

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private CssLayout componentGraficoSesiones = new CssLayout();
	private CssLayout componentGraficoMinutos = new CssLayout();
	private CssLayout componentGraficoRegistros = new CssLayout();
	private XLSReadingService xlsReadingService;
	private Date today = new Date();
	public ComboBox cboxPeriodo = new ComboBox();
	private final Table tablaInformacionPeriodo = new Table();
	private Label captionGraficoSesiones = new Label();
	private Label captionGraficoMinutos = new Label();
	private Label captionGraficoRegistros = new Label();
	private Label captionInfoPeriodo = new Label();
	private String appMovil;
	Calendar calendar = Calendar.getInstance();
	
	private Double totalSesiones = 0.0;
	private Double totalRegistros = 0.0;
	private Double totalMinutos = 0.0;
	private Double topeMinutos = 0.0;
	private Double topeSesiones = 0.0;
	private Double topeRegistros = 0.0;

	public void setAppMovil(String appMovil) {
		this.appMovil = appMovil;
	}

	public void setXLSReadingService(XLSReadingService xlsReadingService){
		this.xlsReadingService = xlsReadingService;
	}
	
	public MobileStatisticsPanel() {
		cboxPeriodo.setImmediate(true);
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

		Responsive.makeResponsive(tablaInformacionPeriodo);
		Responsive.makeResponsive(componentGraficoSesiones);
		Responsive.makeResponsive(componentGraficoMinutos);
		Responsive.makeResponsive(componentGraficoRegistros);
		Responsive.makeResponsive(cboxPeriodo);

		root.addComponent(buildHeader());

		Component content = buildContent();
		
		root.addComponent(content);
		root.setExpandRatio(content, 1);

	}

	private Component buildHeader() {
		HorizontalLayout header = new HorizontalLayout();
		header.addStyleName("viewheader");
		header.setSpacing(true);
		Responsive.makeResponsive(header);
		buildFilter();
		Label title = new Label("Estadísticas Móviles");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(title);
	     Label lbVer = new Label("Ver: ");
	        HorizontalLayout tools = new HorizontalLayout(lbVer, cboxPeriodo);
	        tools.setComponentAlignment(lbVer, Alignment.MIDDLE_CENTER);
		
		Responsive.makeResponsive(tools);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private void buildFilter() {
	
		cboxPeriodo.setImmediate(true);
		cboxPeriodo.setNullSelectionAllowed(false);
		cboxPeriodo.setInvalidAllowed(false);
		BeanItemContainer<String> listaPeriodosContainer = new BeanItemContainer<String>(
				String.class);

		listaPeriodosContainer.addAll(Utilidades.LISTA_PERIODOS);
		listaPeriodosContainer.removeItem(Utilidades.HOY);
		listaPeriodosContainer.removeItem(Utilidades.CUSTOM_DATE);
		cboxPeriodo.setContainerDataSource(listaPeriodosContainer);
		cboxPeriodo.setTextInputAllowed(false);
		cboxPeriodo.select(Utilidades.ULTIMA_SEMANA);
		cboxPeriodo.addValueChangeListener(cboxChangeListener);
	}

	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(componentGraficoSesiones);
	
		dashboardPanels.addComponent(componentGraficoMinutos);
		
		dashboardPanels.addComponent(componentGraficoRegistros);
		
		Component componentInfoPeriodo = buildComponentTablaInfoPeriodo();
		dashboardPanels.addComponent(componentInfoPeriodo);
		String captionSesiones = "Sesiones " + Utilidades.LISTA_PERIODOS.get(0);
		String captionRegistros = "Registros " + Utilidades.LISTA_PERIODOS.get(0);
		String captionMinutos = "Minutos Trasnmitidos " + Utilidades.LISTA_PERIODOS.get(0);
		captionGraficoSesiones.setValue(captionSesiones);
		captionGraficoMinutos.setValue(captionMinutos);
		captionGraficoRegistros.setValue(captionRegistros);
		captionInfoPeriodo.setValue(captionMinutos);

		return dashboardPanels;
	}

	private void fillContentWrapperGraficoMinutos(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Double> listaMinutos = new LinkedHashMap<>();

		listaMinutos = xlsReadingService.getStreamingMinutes(appMovil, fechaInicio, fechaFin);
		
		XYDataset datasetMinutos = datasetMinutos(listaMinutos);
		Component chartMinutos = wrapperChartMinutos(datasetMinutos);
		Responsive.makeResponsive(chartMinutos);
		chartMinutos.setSizeFull();
		componentGraficoMinutos.removeAllComponents();
		componentGraficoMinutos.setWidth("100%");
		componentGraficoMinutos.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionGraficoMinutos.addStyleName(ValoTheme.LABEL_H4);
		captionGraficoMinutos.addStyleName(ValoTheme.LABEL_COLORED);
		captionGraficoMinutos.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		chartMinutos.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoMinutos.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoMinutos, true);
				} else {
					componentGraficoMinutos.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoMinutos, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(captionGraficoMinutos, tools);
		toolbar.setExpandRatio(captionGraficoMinutos, 1);
		toolbar.setComponentAlignment(captionGraficoMinutos, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, chartMinutos);
		componentGraficoMinutos.addComponent(card);
	}
		
	private void fillContentWrapperGraficoSesiones(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaSesiones = new LinkedHashMap<>();

		listaSesiones = xlsReadingService.getStreamingSessions(appMovil, fechaInicio, fechaFin);

		XYDataset datasetSesiones = datasetSesiones(listaSesiones);
		Component chartSesiones = wrapperChartSesiones(datasetSesiones);
		Responsive.makeResponsive(chartSesiones);
		chartSesiones.setSizeFull();
		componentGraficoSesiones.removeAllComponents();
		componentGraficoSesiones.setWidth("100%");
		componentGraficoSesiones.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionGraficoSesiones.addStyleName(ValoTheme.LABEL_H4);
		captionGraficoSesiones.addStyleName(ValoTheme.LABEL_COLORED);
		captionGraficoSesiones.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		chartSesiones.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoSesiones.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoSesiones, true);
				} else {
					componentGraficoSesiones.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoSesiones, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(captionGraficoSesiones, tools);
		toolbar.setExpandRatio(captionGraficoSesiones, 1);
		toolbar.setComponentAlignment(captionGraficoSesiones, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, chartSesiones);
		componentGraficoSesiones.addComponent(card);
	}
		
	private void fillContentWrapperGraficoRegistros(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaRegistros = new LinkedHashMap<>();

		listaRegistros = xlsReadingService.getCustomRegistrations(appMovil, fechaInicio, fechaFin);

		XYDataset datasetRegistros = datasetRegistros(listaRegistros);
		Component chartRegistros = wrapperChartRegistros(datasetRegistros);
		Responsive.makeResponsive(chartRegistros);
		chartRegistros.setSizeFull();
		componentGraficoRegistros.removeAllComponents();
		componentGraficoRegistros.setWidth("100%");
		componentGraficoRegistros.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionGraficoRegistros.addStyleName(ValoTheme.LABEL_H4);
		captionGraficoRegistros.addStyleName(ValoTheme.LABEL_COLORED);
		captionGraficoRegistros.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		chartRegistros.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoRegistros.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoRegistros, true);
				} else {
					componentGraficoRegistros.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoRegistros, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(captionGraficoRegistros, tools);
		toolbar.setExpandRatio(captionGraficoRegistros, 1);
		toolbar.setComponentAlignment(captionGraficoRegistros, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, chartRegistros);
		componentGraficoRegistros.addComponent(card);
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

	private static JFreeChartWrapper wrapperChartSesiones(XYDataset dataset) {

		JFreeChart createchart = chartSesiones(dataset);

		return new JFreeChartWrapper(createchart);
	}

	private static JFreeChartWrapper wrapperChartMinutos(XYDataset  dataset) {

		JFreeChart createchart = chartMinutos(dataset);

		return new JFreeChartWrapper(createchart);
	}
	
	private static JFreeChartWrapper wrapperChartRegistros(XYDataset  dataset) {

		JFreeChart createchart = chartRegistros(dataset);

		return new JFreeChartWrapper(createchart);
	}
	
	private static JFreeChart chartMinutos(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				"Fecha", // x-axis label
				"Minutos Transmitidos", // y-axis label
				dataset, // data
				false, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();

		Shape theShape = ShapeUtilities.createDiamond(1);
		
		r.setShape(theShape);
		
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setPaint(Color.BLUE);
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		plot.getRangeAxis().setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		plot.getRangeAxis().setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));

		axis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		axis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1,
					new SimpleDateFormat("dd-MM")));
	
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setAutoRange(true);

		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private static JFreeChart chartSesiones(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				"Fecha", // x-axis label
				"Sesiones", // y-axis label
				dataset, // data
				false, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();

		Shape theShape = ShapeUtilities.createDiamond(1);
		
		r.setShape(theShape);
		
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setPaint(Color.GREEN);
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		plot.getRangeAxis().setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		plot.getRangeAxis().setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));

		axis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		axis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1,
					new SimpleDateFormat("dd-MM")));
	
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setAutoRange(true);

		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private static JFreeChart chartRegistros(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				"Fecha", // x-axis label
				"Registros", // y-axis label
				dataset, // data
				false, // create legend?
				true, // generate tooltips?
				false // generate URLs?
				);

		chart.setBackgroundPaint(Color.white);
		
		XYPlot plot = (XYPlot) chart.getPlot();
		plot.setBackgroundPaint(Color.WHITE);
		plot.setDomainGridlinePaint(Color.GRAY);
		plot.setRangeGridlinePaint(Color.GRAY);

		plot.setAxisOffset(new RectangleInsets(5.0, 5.0, 5.0, 5.0));
		plot.setDomainCrosshairVisible(true);
		plot.setRangeCrosshairVisible(true);
		XYItemRenderer r = plot.getRenderer();

		Shape theShape = ShapeUtilities.createDiamond(1);
		
		r.setShape(theShape);
		
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setPaint(Color.MAGENTA);
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		plot.getRangeAxis().setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		plot.getRangeAxis().setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));

		axis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		axis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1,
					new SimpleDateFormat("dd-MM")));
	
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setAutoRange(true);

		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}
		
	private XYDataset datasetMinutos(
			LinkedHashMap<Date, Double> listaSesiones) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		Iterator it = listaSesiones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Double minutos = (Double) pairs.getValue();
			
			s1.add(new Day((Date) pairs.getKey()), minutos);
			totalMinutos = totalMinutos + minutos;
			if(minutos > topeMinutos)
				topeMinutos = minutos;
			it.remove();
		}

		dataset.addSeries(s1);

		return dataset;

	}
		
	private XYDataset datasetSesiones(
			LinkedHashMap<Date, Integer> listaSesiones) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		Iterator it = listaSesiones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Integer sesiones = (Integer) pairs.getValue();
			s1.add(new Day((Date) pairs.getKey()), sesiones);
			
			totalSesiones = totalSesiones + sesiones;
			if(sesiones > topeSesiones)
				topeSesiones = sesiones.doubleValue();
			it.remove();
		}

		dataset.addSeries(s1);

		return dataset;

	}
	
	private XYDataset datasetRegistros(
			LinkedHashMap<Date, Integer> listaRegistros) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		Iterator it = listaRegistros.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();

			Integer registros = (Integer) pairs.getValue();
			s1.add(new Day((Date) pairs.getKey()), registros);
			totalRegistros = totalRegistros + registros;
			if(registros > topeRegistros)
				topeRegistros = registros.doubleValue();
			it.remove();
		}

		dataset.addSeries(s1);

		return dataset;

	}
	

	public ValueChangeListener cboxChangeListener = new ValueChangeListener() {
		@Override
		public void valueChange(ValueChangeEvent event) {
			try {
				fillData();
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	};

	public void fillData() throws InvalidResultSetAccessException,
			ParseException {
		
		String periodo = (String) cboxPeriodo.getValue();
		String captionSesiones = "Sesiones " + periodo;
		String captionRegistros = "Registros " + periodo;
		String captionMinutos = "Minutos Transmitidos " + periodo;
		String captionResumen = "Resumen " + periodo;
	
		Date fechaFin = today;
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		switch (periodo) {


		case Utilidades.ULTIMA_SEMANA: {
			fechaInicio.add(Calendar.DATE, -7);

			fillContentWrapperGraficoSesiones(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoMinutos(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoRegistros(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			captionGraficoRegistros.setValue(captionRegistros);
			captionGraficoSesiones.setValue(captionSesiones);
			captionGraficoMinutos.setValue(captionMinutos);
			captionInfoPeriodo.setValue(captionResumen);
			break;
		}
		case Utilidades.ULTIMA_QUINCENA:{

			fechaInicio.add(Calendar.DATE, -14);

			fillContentWrapperGraficoSesiones(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoMinutos(fechaInicio.getTime(), fechaFin);

			fillContentWrapperGraficoRegistros(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);

			captionGraficoRegistros.setValue(captionRegistros);
			captionGraficoSesiones.setValue(captionSesiones);
			captionInfoPeriodo.setValue(captionResumen);
			captionGraficoMinutos.setValue(captionMinutos);
			break;
		}
		case Utilidades.ULTIMO_MES:{

			fechaInicio.add(Calendar.DATE, -30);
			

			fillContentWrapperGraficoSesiones(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoMinutos(fechaInicio.getTime(), fechaFin);

			fillContentWrapperGraficoRegistros(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			captionGraficoRegistros.setValue(captionRegistros);
			captionGraficoSesiones.setValue(captionSesiones);
			captionInfoPeriodo.setValue(captionResumen);
			captionGraficoMinutos.setValue(captionMinutos);
			break;
		}
		


		}

	}

	private void fillTablePeriodo(Date fechaInicio, Date fechaFin) {
		tablaInformacionPeriodo.removeAllItems();				
		
			tablaInformacionPeriodo.addItem("TotalSesiones");

			tablaInformacionPeriodo.addItem("TopeSesiones");
			tablaInformacionPeriodo.addItem("TotalMinutos");

			tablaInformacionPeriodo.addItem("TopeMinutos");
			tablaInformacionPeriodo.addItem("TotalRegistros");
			tablaInformacionPeriodo.addItem("TopeRegistros");
			
			Item row = tablaInformacionPeriodo.getItem("TotalSesiones");
			row.getItemProperty("Key").setValue("Total de Sesiones");
			row.getItemProperty("Value").setValue(totalSesiones);

			Item row1 = tablaInformacionPeriodo.getItem("TotalMinutos");
			row1.getItemProperty("Key").setValue("Total de Minutos");
			row1.getItemProperty("Value").setValue(totalMinutos);

			Item row2 = tablaInformacionPeriodo.getItem("TotalRegistros");
			row2.getItemProperty("Key").setValue("Total de Registros");
			row2.getItemProperty("Value").setValue(totalRegistros);

			Item row3 = tablaInformacionPeriodo.getItem("TopeSesiones");
			row3.getItemProperty("Key").setValue("Tope de Sesiones");
			row3.getItemProperty("Value").setValue(topeSesiones);

			Item row4 = tablaInformacionPeriodo.getItem("TopeMinutos");
			row4.getItemProperty("Key").setValue("Tope de Minutos");
			row4.getItemProperty("Value").setValue(topeMinutos);

			Item row5 = tablaInformacionPeriodo.getItem("TopeRegistros");
			row5.getItemProperty("Key").setValue("Tope de Registros");
			row5.getItemProperty("Value").setValue(topeRegistros);

			
		

	}
	

	private Component buildComponentTablaInfoPeriodo() {
		buildTableInfoPeriodo();
		Component contentWrapper = createContentWrapperPeriodo(tablaInformacionPeriodo);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}
	
	private void buildTableInfoPeriodo() {

		tablaInformacionPeriodo.setSizeFull();
		tablaInformacionPeriodo.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaInformacionPeriodo.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaInformacionPeriodo.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaInformacionPeriodo.addStyleName(ValoTheme.TABLE_SMALL);
		tablaInformacionPeriodo.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaInformacionPeriodo.addContainerProperty("Key", String.class, null);
		tablaInformacionPeriodo.addContainerProperty("Value", Double.class,
				null);
		tablaInformacionPeriodo.setColumnAlignment("Value", Align.RIGHT);
	}
	
	private Component createContentWrapperPeriodo(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");
		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_H4);
		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_COLORED);
		captionInfoPeriodo.addStyleName(ValoTheme.LABEL_NO_MARGIN);
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

		toolbar.addComponents(captionInfoPeriodo, tools);
		toolbar.setExpandRatio(captionInfoPeriodo, 1);
		toolbar.setComponentAlignment(captionInfoPeriodo,
				Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}

	

}

