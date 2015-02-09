package com.dashboardwms.components;

import java.awt.Color;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
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
import org.jfree.data.time.Minute;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.vaadin.addon.JFreeChartWrapper;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.CheckBox;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.DateField;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class DailyStatisticsPanel extends Panel {

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	public PopupDateField dfFecha = new PopupDateField();
	private CssLayout componentGraficoDiario = new CssLayout();
	private CssLayout componentGraficoPeriodo = new CssLayout();
	private AplicacionService aplicacionService;
	private ClienteService clienteService;
	private Date today = new Date();
	private ComboBox cboxPeriodo = new ComboBox();
	private final Table tablaInformacionDiaria = new Table();
	private final Table tablaInformacionPeriodo = new Table();
	private Label captionInfoDiaria = new Label();
	private Label captionTablaDiaria = new Label();
	private Label captionTablaPeriodo = new Label();
	private Label captionInfoPeriodo = new Label();
	private String emisora;
	Calendar calendar = Calendar.getInstance();

	
	public void setEmisora(String emisora){
		this.emisora = emisora;
	}
	public DailyStatisticsPanel() {
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
		Responsive.makeResponsive(componentGraficoDiario);

		Responsive.makeResponsive(tablaInformacionPeriodo);
		Responsive.makeResponsive(componentGraficoPeriodo);
		Responsive.makeResponsive(tablaInformacionDiaria);
		Responsive.makeResponsive(cboxPeriodo);
		Responsive.makeResponsive(dfFecha);

		root.addComponent(buildHeader());

		Component content = buildContent();
		root.addComponent(content);
		root.setExpandRatio(content, 1);

	}

	public void setAplicacionService(AplicacionService aplicacionService) {
		this.aplicacionService = aplicacionService;
	}

	public void setClienteService(ClienteService clienteService) {
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
		HorizontalLayout tools = new HorizontalLayout(dfFecha, cboxPeriodo);
		Responsive.makeResponsive(tools);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		header.addComponent(tools);

		return header;
	}

	private void buildFilter() {
		dfFecha.setValue(today);
		dfFecha.setDateFormat("dd-MM-yyyy");
		dfFecha.setImmediate(true);
		dfFecha.setRangeEnd(today);
		dfFecha.setRangeStart(calendar.getTime());

		dfFecha.addValueChangeListener(dfChangeListener);
		dfFecha.setTextFieldEnabled(false);

		cboxPeriodo.setImmediate(true);
		cboxPeriodo.setNullSelectionAllowed(false);
		cboxPeriodo.setInvalidAllowed(false);
		BeanItemContainer<String> listaPeriodosContainer = new BeanItemContainer<String>(
				String.class);

		listaPeriodosContainer.addAll(Utilidades.LISTA_PERIODOS);
		cboxPeriodo.setContainerDataSource(listaPeriodosContainer);
		cboxPeriodo.setTextInputAllowed(false);
		cboxPeriodo.select(Utilidades.LISTA_PERIODOS.get(0));
		cboxPeriodo.addValueChangeListener(cboxChangeListener);
	}

	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);

		dashboardPanels.addComponent(componentGraficoDiario);

		dashboardPanels.addComponent(buildComponentTablaInformacion());

		dashboardPanels.addComponent(componentGraficoPeriodo);

		dashboardPanels.addComponent(buildComponentTablaInfoPeriodo());

		String caption = "Oyentes "
				+ Utilidades.DATE_FORMAT_LOCAL.format(today);
		captionTablaDiaria.setValue(caption);
		captionInfoDiaria.setValue(caption);

		String captionPeriodo = "Oyentes " + Utilidades.LISTA_PERIODOS.get(0);
		captionTablaPeriodo.setValue(captionPeriodo);
		captionInfoPeriodo.setValue(captionPeriodo);

		return dashboardPanels;
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

	private Component buildComponentTablaInformacion() {
		buildTableInformacion();
		Component contentWrapper = createContentWrapperDiario(tablaInformacionDiaria);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}

	private Component createContentWrapperDiario(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionTablaDiaria.addStyleName(ValoTheme.LABEL_H4);
		captionTablaDiaria.addStyleName(ValoTheme.LABEL_COLORED);
		captionTablaDiaria.addStyleName(ValoTheme.LABEL_NO_MARGIN);
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

		toolbar.addComponents(captionTablaDiaria, tools);
		toolbar.setExpandRatio(captionTablaDiaria, 1);
		toolbar.setComponentAlignment(captionTablaDiaria, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
	}

	private void buildTableInformacion() {
		tablaInformacionDiaria.setSizeFull();
		tablaInformacionDiaria.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaInformacionDiaria.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaInformacionDiaria.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaInformacionDiaria.addStyleName(ValoTheme.TABLE_SMALL);
		tablaInformacionDiaria.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaInformacionDiaria.addContainerProperty("Key", String.class, null);
		tablaInformacionDiaria
				.addContainerProperty("Value", Double.class, null);

	}

	private void fillContentWrapperGraficoMinutos(Date fecha) {
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();

		listaAplicaciones = aplicacionService.getAplicacionPorFecha(fecha,
				emisora);

		XYDataset datasetMinutosEscuchados = datasetDiario(listaAplicaciones);
		Component chartCantidadMinutos = wrapperChartDiario(datasetMinutosEscuchados);
		Responsive.makeResponsive(chartCantidadMinutos);
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

		captionInfoDiaria.addStyleName(ValoTheme.LABEL_H4);
		captionInfoDiaria.addStyleName(ValoTheme.LABEL_COLORED);
		captionInfoDiaria.addStyleName(ValoTheme.LABEL_NO_MARGIN);
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

		toolbar.addComponents(captionInfoDiaria, tools);
		toolbar.setExpandRatio(captionInfoDiaria, 1);
		toolbar.setComponentAlignment(captionInfoDiaria, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, chartCantidadMinutos);
		componentGraficoDiario.addComponent(card);
	}

	private void fillContentWrapperGraficoPeriodo(String periodo) {
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();
		
		Date fechaFin = today;
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		switch (periodo) {
		case Utilidades.ULTIMA_SEMANA:
			fechaInicio.add(Calendar.DATE, -7);
			break;

		case Utilidades.ULTIMA_QUINCENA:

			fechaInicio.add(Calendar.DATE, -14);
			break;

		case Utilidades.ULTIMO_MES:

			fechaInicio.add(Calendar.DATE, -30);
			break;
		}
		listaAplicaciones = aplicacionService.getAplicacionRangoFechas(emisora, fechaInicio.getTime(), fechaFin);
		
		XYDataset datasetPeriodo = datasetPeriodo(listaAplicaciones);
		Component chartPeriodo = wrapperChartPeriodo(datasetPeriodo);
		Responsive.makeResponsive(chartPeriodo);
		chartPeriodo.setCaption("Usuarios");
		chartPeriodo.setSizeFull();
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
		chartPeriodo.setCaption(null);

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

		card.addComponents(toolbar, chartPeriodo);
		componentGraficoPeriodo.addComponent(card);
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
		captionTablaPeriodo.addStyleName(ValoTheme.LABEL_H4);
		captionTablaPeriodo.addStyleName(ValoTheme.LABEL_COLORED);
		captionTablaPeriodo.addStyleName(ValoTheme.LABEL_NO_MARGIN);
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

		toolbar.addComponents(captionTablaPeriodo, tools);
		toolbar.setExpandRatio(captionTablaPeriodo, 1);
		toolbar.setComponentAlignment(captionTablaPeriodo,
				Alignment.MIDDLE_LEFT);

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

	private static JFreeChartWrapper wrapperChartPeriodo(XYDataset dataset) {

		JFreeChart createchart = chartPeriodo(dataset);

		return new JFreeChartWrapper(createchart);
	}

	private static JFreeChartWrapper wrapperChartDiario(XYDataset dataset) {

		JFreeChart createchart = chartDiario(dataset);

		return new JFreeChartWrapper(createchart);
	}

	private static JFreeChart chartDiario(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				null, // x-axis label
				null, // y-axis label
				dataset, // data
				false, // create legend?
				false, // generate tooltips?
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
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setPaint(Color.blue);
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 1,
				new SimpleDateFormat("HH:mm")));
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private static JFreeChart chartPeriodo(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				null, // x-axis label
				null, // y-axis label
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
		if (r instanceof XYLineAndShapeRenderer) {
			XYLineAndShapeRenderer renderer = (XYLineAndShapeRenderer) r;
			renderer.setPaint(Color.RED);
			renderer.setBaseShapesVisible(true);
			renderer.setBaseShapesFilled(true);
			renderer.setDrawSeriesLineAsPath(true);
		}

		DateAxis axis = (DateAxis) plot.getDomainAxis();
		axis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 24,
				new SimpleDateFormat("dd-MM")));
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setAutoRange(true);

		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private XYDataset datasetDiario(List<Aplicacion> listaAplicaciones) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (Aplicacion aplicacion : listaAplicaciones) {
			s1.add(new Minute(aplicacion.getTimestamp()),
					aplicacion.getConexionesActuales());
		}
		dataset.addSeries(s1);

		return dataset;

	}

	private XYDataset datasetPeriodo(List<Aplicacion> listaAplicaciones) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();
		for (Aplicacion aplicacion : listaAplicaciones) {
			s1.add(new Minute(aplicacion.getTimestamp()),
					aplicacion.getConexionesActuales());
		}
		dataset.addSeries(s1);

		return dataset;

	}

	public ValueChangeListener cboxChangeListener = new ValueChangeListener() {
		@Override
		public void valueChange(ValueChangeEvent event) {
			String periodo = (String) cboxPeriodo.getValue();
			String caption = "Oyentes " + periodo;
			captionInfoPeriodo.setValue(caption);
			captionTablaPeriodo.setValue(caption);
			fillTablePeriodo(periodo);
			fillContentWrapperGraficoPeriodo(periodo);
		}

		

	};
	public ValueChangeListener dfChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			Date fecha = dfFecha.getValue();
			String caption = "Oyentes "
					+ Utilidades.DATE_FORMAT_LOCAL.format(fecha);
			captionTablaDiaria.setValue(caption);
			captionInfoDiaria.setValue(caption);
			fillContentWrapperGraficoMinutos(fecha);
			fillTableDiario(fecha);

		}
	};

	public void fillData(Date fecha) {
		fillTableDiario(fecha);
		fillContentWrapperGraficoMinutos(fecha);
		fillContentWrapperGraficoPeriodo(Utilidades.LISTA_PERIODOS.get(0));
		fillTablePeriodo(Utilidades.LISTA_PERIODOS.get(0));

	}

	private void fillTablePeriodo(String periodo) {
		tablaInformacionPeriodo.removeAllItems();
		Date fechaFin = today;
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		switch (periodo) {
		case Utilidades.ULTIMA_SEMANA:
			fechaInicio.add(Calendar.DATE, -7);
			break;

		case Utilidades.ULTIMA_QUINCENA:

			fechaInicio.add(Calendar.DATE, -14);
			break;

		case Utilidades.ULTIMO_MES:

			fechaInicio.add(Calendar.DATE, -30);
			break;
		}
		LinkedHashMap<String, Double> info = clienteService
				.getInfoRangoFechas(emisora, fechaInicio.getTime(), fechaFin);
		Iterator it = info.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			tablaInformacionPeriodo.addItem(pairs);
			System.out.println(pairs.getKey());
			
			Item row = tablaInformacionPeriodo.getItem(pairs);
			row.getItemProperty("Key").setValue(pairs.getKey());
			row.getItemProperty("Value").setValue(pairs.getValue());

			it.remove();
		}

	}
	private void fillTableDiario(Date fecha) {
		tablaInformacionDiaria.removeAllItems();
		LinkedHashMap<String, Double> info = clienteService.getInfoRangoFechas(
				emisora, fecha, fecha);
		Iterator it = info.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			tablaInformacionDiaria.addItem(pairs);
			System.out.println(pairs.getKey());
			Item row = tablaInformacionDiaria.getItem(pairs);
			row.getItemProperty("Key").setValue(pairs.getKey());
			row.getItemProperty("Value").setValue(pairs.getValue());

			it.remove();
		}

	}

}
