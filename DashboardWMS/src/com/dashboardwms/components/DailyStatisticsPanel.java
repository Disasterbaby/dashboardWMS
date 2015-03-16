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
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.axis.CategoryLabelPositions;
import org.jfree.chart.axis.DateAxis;
import org.jfree.chart.axis.DateTickMarkPosition;
import org.jfree.chart.axis.DateTickUnit;
import org.jfree.chart.axis.DateTickUnitType;
import org.jfree.chart.axis.NumberAxis;
import org.jfree.chart.axis.ValueAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.plot.XYPlot;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.StandardBarPainter;
import org.jfree.chart.renderer.xy.XYItemRenderer;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.time.Hour;
import org.jfree.data.time.TimeSeries;
import org.jfree.data.time.TimeSeriesCollection;
import org.jfree.data.xy.XYDataset;
import org.jfree.ui.RectangleInsets;
import org.jfree.util.ShapeUtilities;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.vaadin.addon.JFreeChartWrapper;

import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
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
	private CssLayout componentGraficoPeriodo = new CssLayout();
	private CssLayout componentGraficoDispositivos = new CssLayout();
	private AplicacionService aplicacionService;
	private ClienteService clienteService;
	private Date today = new Date();
	public ComboBox cboxPeriodo = new ComboBox();
	private final Table tablaInformacionPeriodo = new Table();
	private final Table tablaDispositivosPeriodo = new Table();
	private Label captionTablaPeriodo = new Label();
	private Label captionTablaDispositivos = new Label();
	private Label captionInfoPeriodo = new Label();
	private Label captionInfoDispositivos = new Label();
	private String emisora;
	private static Boolean tipoRango = true;
	Calendar calendar = Calendar.getInstance();

	public void setEmisora(String emisora) {
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

		Responsive.makeResponsive(tablaInformacionPeriodo);
		Responsive.makeResponsive(tablaDispositivosPeriodo);
		Responsive.makeResponsive(componentGraficoPeriodo);
		Responsive.makeResponsive(componentGraficoDispositivos);
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
	     Label lbVer = new Label("Ver: ");
	        HorizontalLayout tools = new HorizontalLayout(lbVer, cboxPeriodo, dfFecha);
	        tools.setComponentAlignment(lbVer, Alignment.MIDDLE_CENTER);
		dfFecha.setVisible(false);
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

		dashboardPanels.addComponent(componentGraficoPeriodo);
	
		Component componentTablaPeriodo = buildComponentTablaInfoPeriodo();
		dashboardPanels.addComponent(componentTablaPeriodo);
	
		dashboardPanels.addComponent(componentGraficoDispositivos);
		
		Component componentTablaDispositivos = buildComponentTablaDispositivos();
		dashboardPanels.addComponent(componentTablaDispositivos);
		String captionPeriodo = "Cantidad de Oyentes " + Utilidades.LISTA_PERIODOS.get(0);
		String captionDispositivos = "Tipos de Conexión " + Utilidades.LISTA_PERIODOS.get(0);
		captionTablaPeriodo.setValue(captionPeriodo);
		captionTablaDispositivos.setValue(captionDispositivos);
		captionInfoPeriodo.setValue(captionPeriodo);
		captionInfoDispositivos.setValue(captionDispositivos);

		return dashboardPanels;
	}

	private Component buildComponentTablaInfoPeriodo() {
		buildTableInfoPeriodo();
		Component contentWrapper = createContentWrapperPeriodo(tablaInformacionPeriodo);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}

	private Component buildComponentTablaDispositivos(){
		buildTableDispositivos();
		Component contentWrapper = createContentWrapperDispositivos(tablaDispositivosPeriodo);
		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}
	
	private void buildTableDispositivos(){
		tablaDispositivosPeriodo.setSizeFull();
		tablaDispositivosPeriodo.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaDispositivosPeriodo.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaDispositivosPeriodo.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaDispositivosPeriodo.addStyleName(ValoTheme.TABLE_SMALL);
		tablaDispositivosPeriodo.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaDispositivosPeriodo.addContainerProperty("Key", String.class, null);
		tablaDispositivosPeriodo.addContainerProperty("Value", Integer.class,
				null);
		tablaDispositivosPeriodo.setColumnAlignment("Value", Align.RIGHT);
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
	
	private void fillContentWrapperGraficoDispositivos(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<String, Integer> lista = new LinkedHashMap<>();

		lista = clienteService.getCantidadDispositivosRangoFechas(emisora, fechaInicio, fechaFin);

		CategoryDataset datasetDispositivos = datasetDispositivos(lista);
		Component chartDispositivos = wrapperChartDispositivos(datasetDispositivos);
		Responsive.makeResponsive(chartDispositivos);
		chartDispositivos.setSizeFull();
		componentGraficoDispositivos.removeAllComponents();
		componentGraficoDispositivos.setWidth("100%");
		componentGraficoDispositivos.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");

		captionInfoDispositivos.addStyleName(ValoTheme.LABEL_H4);
		captionInfoDispositivos.addStyleName(ValoTheme.LABEL_COLORED);
		captionInfoDispositivos.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		chartDispositivos.setCaption(null);

		MenuBar tools = new MenuBar();
		tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);

		MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

			@Override
			public void menuSelected(final MenuItem selectedItem) {
				if (!componentGraficoDispositivos.getStyleName().contains("max")) {
					selectedItem.setIcon(FontAwesome.COMPRESS);
					toggleMaximized(componentGraficoDispositivos, true);
				} else {
					componentGraficoDispositivos.removeStyleName("max");
					selectedItem.setIcon(FontAwesome.EXPAND);
					toggleMaximized(componentGraficoDispositivos, false);
				}
			}
		});
		max.setStyleName("icon-only");

		toolbar.addComponents(captionInfoDispositivos, tools);
		toolbar.setExpandRatio(captionInfoDispositivos, 1);
		toolbar.setComponentAlignment(captionInfoDispositivos, Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, chartDispositivos);
		componentGraficoDispositivos.addComponent(card);
	}
		
	private void fillContentWrapperGraficoPeriodo(Date fechaInicio, Date fechaFin)
			throws InvalidResultSetAccessException, ParseException {
		LinkedHashMap<Date, Integer> listaAplicaciones = new LinkedHashMap<>();

		listaAplicaciones = aplicacionService.getUsuariosConectadosPorHora(
				emisora, fechaInicio, fechaFin);

		XYDataset datasetPeriodo = datasetPeriodo(listaAplicaciones);
		Component chartPeriodo = wrapperChartPeriodo(datasetPeriodo);
		Responsive.makeResponsive(chartPeriodo);
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
		
	private Component createContentWrapperDispositivos(final Component content) {
		final CssLayout slot = new CssLayout();
		slot.setWidth("100%");
		slot.addStyleName("dashboard-panel-slot");

		CssLayout card = new CssLayout();
		card.setWidth("100%");
		card.addStyleName(ValoTheme.LAYOUT_CARD);

		HorizontalLayout toolbar = new HorizontalLayout();
		toolbar.addStyleName("dashboard-panel-toolbar");
		toolbar.setWidth("100%");
		captionTablaDispositivos.addStyleName(ValoTheme.LABEL_H4);
		captionTablaDispositivos.addStyleName(ValoTheme.LABEL_COLORED);
		captionTablaDispositivos.addStyleName(ValoTheme.LABEL_NO_MARGIN);
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

		toolbar.addComponents(captionTablaDispositivos, tools);
		toolbar.setExpandRatio(captionTablaDispositivos, 1);
		toolbar.setComponentAlignment(captionTablaDispositivos,
				Alignment.MIDDLE_LEFT);

		card.addComponents(toolbar, content);
		slot.addComponent(card);
		return slot;
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

	private static JFreeChartWrapper wrapperChartDispositivos(CategoryDataset  dataset) {

		JFreeChart createchart = chartDispositivos(dataset);

		return new JFreeChartWrapper(createchart);
	}
	
    private static JFreeChart chartDispositivos(CategoryDataset dataset) {
        
        // create the chart...
        final JFreeChart chart = ChartFactory.createBarChart(
            "",         // chart title
            "Cantidad",               // domain axis label
            "Medio",                  // range axis label
            dataset,                  // data
            PlotOrientation.VERTICAL, // orientation
            true,                     // include legend
            true,                     // tooltips?
            false                     // URLs?
        );

        // NOW DO SOME OPTIONAL CUSTOMISATION OF THE CHART...

        // set the background color for the chart...
        chart.setBackgroundPaint(Color.WHITE);
        
        // get a reference to the plot for further customisation...
        final CategoryPlot plot = chart.getCategoryPlot();
        plot.setBackgroundPaint(Color.WHITE);
        plot.setDomainGridlinePaint(Color.GRAY);
        plot.setRangeGridlinePaint(Color.GRAY);
		plot.getRangeAxis().setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
		plot.getRangeAxis().setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
	
        // set the range axis to display integers only...
        final NumberAxis rangeAxis = (NumberAxis) plot.getRangeAxis();
        rangeAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

        // disable bar outlines...
        final BarRenderer renderer = (BarRenderer) plot.getRenderer();
        renderer.setDrawBarOutline(true);
        renderer.setMaximumBarWidth(0.25); 
       
         renderer.setItemMargin(0);
       renderer.setBarPainter(new StandardBarPainter());
       	renderer.setOutlinePaint(Color.BLACK);
       	
        final CategoryAxis domainAxis = plot.getDomainAxis();
        
        domainAxis.setLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
        domainAxis.setTickLabelFont(new Font(Font.SANS_SERIF, Font.PLAIN, 8));
      
        // OPTIONAL CUSTOMISATION COMPLETED.
        
        return chart;
        
    }
	
	private static JFreeChart chartPeriodo(XYDataset dataset) {

		JFreeChart chart = ChartFactory.createTimeSeriesChart(null, // title
				"Fecha", // x-axis label
				"Oyentes", // y-axis label
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
		if (tipoRango)
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.DAY, 1,
					new SimpleDateFormat("dd-MM")));
		else
		{	axis.setLabel("Hora");
			axis.setTickUnit(new DateTickUnit(DateTickUnitType.HOUR, 1,
					new SimpleDateFormat("HH:mm")));}
		
		axis.setTickMarkPosition(DateTickMarkPosition.START);
		axis.setAutoRange(true);

		ValueAxis numberAxis = plot.getRangeAxis();
		numberAxis.setStandardTickUnits(NumberAxis.createIntegerTickUnits());

		return chart;

	}

	private CategoryDataset datasetDispositivos(LinkedHashMap<String, Integer> lista){

        final DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        
		Iterator it = lista.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			dataset.addValue((Integer) pairs.getValue(), (String) pairs.getKey(), "");
			it.remove();
		}

		

		return dataset;
	}
	
	private XYDataset datasetPeriodo(
			LinkedHashMap<Date, Integer> listaAplicaciones) {

		TimeSeries s1 = new TimeSeries("nombre", "domain", "range");

		TimeSeriesCollection dataset = new TimeSeriesCollection();

		Iterator it = listaAplicaciones.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			s1.add(new Hour((Date) pairs.getKey()), (Integer) pairs.getValue());

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
	public ValueChangeListener dfChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			Date fecha = dfFecha.getValue();
			String periodo = Utilidades.DATE_FORMAT_LOCAL.format(fecha);
			String captionGrafico = "Cantidad de Oyentes " + periodo;
			String captionTabla = "Estadísticas " + periodo;
			String captionDispositivos = "Tipos de Conexión " + periodo;
			captionInfoPeriodo.setValue(captionGrafico);
			captionTablaDispositivos.setValue(captionDispositivos);
			captionTablaPeriodo.setValue(captionTabla);
			captionInfoDispositivos.setValue(captionDispositivos);
			fillTablePeriodo(fecha, fecha);
			fillTableDispositivos(fecha, fecha);
			try {
				fillContentWrapperGraficoPeriodo(fecha, fecha);
				fillContentWrapperGraficoDispositivos(fecha, fecha);
			} catch (InvalidResultSetAccessException | ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			

		}
	};

	public void fillData() throws InvalidResultSetAccessException,
			ParseException {
		String periodo = (String) cboxPeriodo.getValue();
		String captionGrafico = "Cantidad de Oyentes " + periodo;
		String captionTabla = "Estadísticas " + periodo;

		String captionDispositivos = "Tipos de Conexión " + periodo;
		dfFecha.setVisible(false);
		dfFecha.setDescription(null);

		dfFecha.removeValueChangeListener(dfChangeListener);
		Date fechaFin = today;
		tipoRango = true;
		Calendar fechaInicio = Calendar.getInstance();
		fechaInicio.setTime(fechaFin);
		switch (periodo) {

		case Utilidades.HOY: {
			fechaInicio.setTime(fechaFin);
			tipoRango = false;

			fillContentWrapperGraficoPeriodo(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoDispositivos(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			fillTableDispositivos(fechaInicio.getTime(), fechaFin);
			captionInfoPeriodo.setValue(captionGrafico);
			captionTablaPeriodo.setValue(captionTabla);
			captionTablaDispositivos.setValue(captionDispositivos);
			captionInfoDispositivos.setValue(captionDispositivos);
			break;
		}

		case Utilidades.ULTIMA_SEMANA: {
			fechaInicio.add(Calendar.DATE, -7);

			fillContentWrapperGraficoPeriodo(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoDispositivos(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			fillTableDispositivos(fechaInicio.getTime(), fechaFin);
			captionInfoPeriodo.setValue(captionGrafico);
			captionTablaPeriodo.setValue(captionTabla);
			captionTablaDispositivos.setValue(captionDispositivos);
			captionInfoDispositivos.setValue(captionDispositivos);
			break;
		}
		case Utilidades.ULTIMA_QUINCENA:{

			fechaInicio.add(Calendar.DATE, -14);

			fillContentWrapperGraficoPeriodo(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoDispositivos(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			fillTableDispositivos(fechaInicio.getTime(), fechaFin);
			captionInfoPeriodo.setValue(captionGrafico);
			captionTablaPeriodo.setValue(captionTabla);
			captionInfoDispositivos.setValue(captionDispositivos);
			captionTablaDispositivos.setValue(captionDispositivos);
			break;
		}
		case Utilidades.ULTIMO_MES:{

			fechaInicio.add(Calendar.DATE, -30);
			

			fillContentWrapperGraficoPeriodo(fechaInicio.getTime(), fechaFin);
			fillContentWrapperGraficoDispositivos(fechaInicio.getTime(), fechaFin);
			fillTablePeriodo(fechaInicio.getTime(), fechaFin);
			fillTableDispositivos(fechaInicio.getTime(), fechaFin);
			captionInfoPeriodo.setValue(captionGrafico);
			captionTablaPeriodo.setValue(captionTabla);
			captionInfoDispositivos.setValue(captionDispositivos);
			captionTablaDispositivos.setValue(captionDispositivos);
			break;
		}
		
			case Utilidades.CUSTOM_DATE: {
				tipoRango = false;
				dfFecha.setVisible(true);
				dfFecha.focus();
				dfFecha.setDescription("Seleccionar Fecha");

				dfFecha.addValueChangeListener(dfChangeListener);
				break;
			}

		}

	}

	
	private void fillTablePeriodo(Date fechaInicio, Date fechaFin) {
		tablaInformacionPeriodo.removeAllItems();				
		LinkedHashMap<String, Double> info = clienteService.getInfoRangoFechas(
				emisora, fechaInicio, fechaFin);
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
	
	private void fillTableDispositivos(Date fechaInicio, Date fechaFin){
		tablaDispositivosPeriodo.removeAllItems();				
		LinkedHashMap<String, Integer> info = clienteService.getCantidadDispositivosRangoFechas(emisora, fechaInicio, fechaFin);
		Iterator it = info.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry<String, Integer> pairs = (Map.Entry<String, Integer>) it.next();
			tablaDispositivosPeriodo.addItem(pairs);
			System.out.println(pairs.getKey());

			Item row = tablaDispositivosPeriodo.getItem(pairs);
			row.getItemProperty("Key").setValue(pairs.getKey());
			row.getItemProperty("Value").setValue(pairs.getValue());

			it.remove();
		}

		
	}
	
	

}
