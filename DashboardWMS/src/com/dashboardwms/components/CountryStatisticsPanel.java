package com.dashboardwms.components;

import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.TimeZone;





import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.addon.charts.Chart;
import com.vaadin.addon.charts.model.ChartType;
import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.model.Cursor;
import com.vaadin.addon.charts.model.DataSeries;
import com.vaadin.addon.charts.model.DataSeriesItem;
import com.vaadin.addon.charts.model.Labels;
import com.vaadin.addon.charts.model.PlotOptionsPie;
import com.vaadin.addon.charts.model.Tooltip;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.CssLayout;
import com.vaadin.ui.Embedded;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.MenuBar;
import com.vaadin.ui.Window;
import com.vaadin.ui.MenuBar.Command;
import com.vaadin.ui.MenuBar.MenuItem;
import com.vaadin.ui.Panel;
import com.vaadin.ui.PopupDateField;
import com.vaadin.ui.Table;
import com.vaadin.ui.Table.Align;
import com.vaadin.ui.Table.ColumnHeaderMode;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class CountryStatisticsPanel extends Panel {
	
	  private CssLayout dashboardPanels;
	    private final VerticalLayout root;
	    private final Table tablaUsuariosConectados = new Table();
	    private final Table tablaMinutosTotales = new Table();
	    private CssLayout componentGraficoUsuarios =  new CssLayout();
	    private CssLayout componentGraficoMinutos = new CssLayout();
	    private ClienteService clienteService;
	    private LookupService cl;
		public ComboBox cboxPeriodo = new ComboBox();

		private Date today = Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime();
		Calendar calendar = Calendar.getInstance();
		public PopupDateField dfFechaInicial = new PopupDateField();
		public PopupDateField dfFechaFinal = new PopupDateField();
	    
		private String emisora;

		
		public void setEmisora(String emisora){
			this.emisora = emisora;
		}
    public CountryStatisticsPanel() {

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
        Responsive.makeResponsive(tablaMinutosTotales);
        Responsive.makeResponsive(tablaUsuariosConectados);

		Responsive.makeResponsive(cboxPeriodo);
		Responsive.makeResponsive(dfFechaInicial);

		Responsive.makeResponsive(dfFechaFinal);
        Responsive.makeResponsive(componentGraficoUsuarios);
        Responsive.makeResponsive(componentGraficoMinutos);
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
        Label title = new Label("Estadísticas Por Países");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        Label lbVer = new Label("Ver: ");
        HorizontalLayout tools = new HorizontalLayout(lbVer, cboxPeriodo, dfFechaInicial, dfFechaFinal);
        tools.setComponentAlignment(lbVer, Alignment.MIDDLE_CENTER);
        dfFechaInicial.setVisible(false);
        dfFechaFinal.setVisible(false);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }

    
    private void buildFilter() {
    	dfFechaInicial.setValue(today);
    	dfFechaInicial.setDateFormat("dd-MM-yyyy");
    	dfFechaInicial.setImmediate(true);
    	dfFechaInicial.setRangeEnd(today);
    	dfFechaInicial.setRangeStart(calendar.getTime());
    	dfFechaInicial.addValueChangeListener(dfChangeListener);
    	dfFechaInicial.setTextFieldEnabled(false);
    	dfFechaFinal.setValue(today);
    	dfFechaFinal.setDateFormat("dd-MM-yyyy");
    	dfFechaFinal.setImmediate(true);
    	dfFechaFinal.setRangeEnd(today);
    	dfFechaFinal.setRangeStart(calendar.getTime());
    	dfFechaFinal.addValueChangeListener(dfChangeListener);
    	dfFechaFinal.setTextFieldEnabled(false);

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
    


	public ValueChangeListener cboxChangeListener = new ValueChangeListener() {
		@Override
		public void valueChange(ValueChangeEvent event) {
			String periodo = (String) cboxPeriodo.getValue();
			Date fechaFin = today;
			dfFechaInicial.setVisible(false);
			dfFechaInicial.setDescription(null);
			dfFechaInicial.removeValueChangeListener(dfChangeListener);

			dfFechaFinal.setVisible(false);
			dfFechaFinal.setDescription(null);
			dfFechaFinal.removeValueChangeListener(dfChangeListener);
			Calendar fechaInicio = Calendar.getInstance();
			fechaInicio.setTime(fechaFin);
			switch (periodo) {
			
			case Utilidades.HOY:{
				fechaInicio.setTime(fechaFin);

				fillData(clienteService.getClientesPorPaisFechas(emisora, fechaInicio.getTime(), fechaFin, cl));
				break;
			}
			
			case Utilidades.ULTIMA_SEMANA:{
				fechaInicio.add(Calendar.DATE, -7);

				fillData(clienteService.getClientesPorPaisFechas(emisora, fechaInicio.getTime(), fechaFin, cl));
				break;
			}

			case Utilidades.ULTIMA_QUINCENA:
			{
				fechaInicio.add(Calendar.DATE, -14);

				fillData(clienteService.getClientesPorPaisFechas(emisora, fechaInicio.getTime(), fechaFin, cl));
				break;
			}
			case Utilidades.ULTIMO_MES:
			{
				fechaInicio.add(Calendar.DATE, -30);

				fillData(clienteService.getClientesPorPaisFechas(emisora, fechaInicio.getTime(), fechaFin, cl));
				break;
			}
				
			case Utilidades.CUSTOM_DATE:{
				dfFechaInicial.setVisible(true);
				dfFechaInicial.focus();
				dfFechaInicial.setDescription("Seleccionar Fecha");
				dfFechaInicial.addValueChangeListener(dfChangeListener);
				

				dfFechaFinal.setVisible(true);
				dfFechaFinal.focus();
				dfFechaFinal.setDescription("Seleccionar Fecha");
				dfFechaFinal.addValueChangeListener(dfChangeListener);
				
				break;
			}
			}
			
			
	
		}

		

	};
	public ValueChangeListener dfChangeListener = new ValueChangeListener() {

		@Override
		public void valueChange(ValueChangeEvent event) {
			Date fechaInicial = dfFechaInicial.getValue();
			Date fechaFinal = dfFechaFinal.getValue();

			fillData(clienteService.getClientesPorPaisFechas(emisora, fechaInicial, fechaFinal, cl));


		}
	};
    
    
    private Component buildContent() {
        dashboardPanels = new CssLayout();
        dashboardPanels.setSizeFull();
        dashboardPanels.addStyleName("dashboard-panels");
        Responsive.makeResponsive(dashboardPanels);
    	buildTableMinutosTotales();
        buildTableUsuariosConectados();
        
        dashboardPanels.addComponent(componentGraficoUsuarios);
//        dashboardPanels.addComponent(buildComponentTablaTopSesiones());
       dashboardPanels.addComponent(componentGraficoMinutos);
//        dashboardPanels.addComponent(buildComponentTablaTopTiempo());

        return dashboardPanels;
    }
    
    

 
    
   private void fillContentWrapperGraficoMinutos(HashSet<Location> listaPaises){
	   
	   Chart chart = new Chart(ChartType.PIE);
       final Configuration conf = chart.getConfiguration();

       conf.setTitle("");

       PlotOptionsPie plotOptions = new PlotOptionsPie();
       plotOptions.setCursor(Cursor.POINTER);
      
       Labels dataLabels = new Labels();
       dataLabels.setEnabled(false);
       dataLabels.setEnabled(true);
       dataLabels
               .setFormatter("this.point.name");
       plotOptions.setDataLabels(dataLabels);
       conf.setPlotOptions(plotOptions);
       Tooltip tooltip = new Tooltip();
       tooltip.setValueDecimals(2);
       tooltip.setFormatter("''+ this.point.name +': '+ Highcharts.numberFormat(this.percentage, 2) +' %'");
       conf.setTooltip(tooltip);

       final DataSeries series = new DataSeries();
       
       for (Location location : listaPaises) {
    	   series.add(new DataSeriesItem(location.countryName, location.getMinutosEscuchados()));
  	
  			
  		}
       
       conf.setSeries(series);

   
       chart.drawChart(conf);
	   
HorizontalLayout hLayout = new HorizontalLayout();
hLayout.setSizeFull();
    	Responsive.makeResponsive(chart);
    	chart.setCaption("Minutos Conectados");
    	chart.setSizeFull();
    	componentGraficoMinutos.removeAllComponents();
    	componentGraficoMinutos.setWidth("100%");
    	componentGraficoMinutos.addStyleName("dashboard-pais");
        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

      final  Label caption = new Label(chart.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        chart.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        final MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

   				@Override
   				public void menuSelected(final MenuItem selectedItem) {
   					
   					
   					generarPDF(conf, caption.getValue());
   				}
   			});
   			print.setStyleName("icon-only");
        
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
   
    
    	tablaMinutosTotales.addStyleName("top10-revenue");
        Responsive.makeResponsive(tablaMinutosTotales);

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        hLayout.addComponent(chart);
        hLayout.addComponent(tablaMinutosTotales);
        hLayout.setComponentAlignment(tablaMinutosTotales, Alignment.MIDDLE_CENTER);
        hLayout.setSpacing(true);
        card.addComponents(toolbar, hLayout);
        componentGraficoMinutos.addComponent(card);
    }
    
    
    
    
    private void fillContentWrapperGraficoUsuarios(HashSet<Location> listaPaises){
    	HorizontalLayout hLayout = new HorizontalLayout();
    	hLayout.setSizeFull();   Chart chart = new Chart(ChartType.PIE);
 	   
       final Configuration conf = chart.getConfiguration();

        conf.setTitle("");

        PlotOptionsPie plotOptions = new PlotOptionsPie();
        plotOptions.setCursor(Cursor.POINTER);
       
        Labels dataLabels = new Labels();
        dataLabels.setEnabled(false);
        dataLabels.setEnabled(true);
        dataLabels
                .setFormatter("this.point.name");
        plotOptions.setDataLabels(dataLabels);
        conf.setPlotOptions(plotOptions);
        Tooltip tooltip = new Tooltip();
        tooltip.setValueDecimals(2);
        tooltip.setFormatter("''+ this.point.name +': '+ Highcharts.numberFormat(this.percentage, 2) +' %'");
        conf.setTooltip(tooltip);

        final DataSeries series = new DataSeries();
        
        for (Location location : listaPaises) {
     	   series.add(new DataSeriesItem(location.countryName, location.getCantidadUsuarios()));
   	
   			
   		}
        
        conf.setSeries(series);

    
        chart.drawChart(conf);
    	Responsive.makeResponsive(chart);
    	chart.setCaption("Sesiones");
    	chart.setSizeFull();
    	componentGraficoUsuarios.removeAllComponents();
    	componentGraficoUsuarios.setWidth("100%");
    	componentGraficoUsuarios.addStyleName("dashboard-pais");

    	

        CssLayout card = new CssLayout();
        card.setWidth("100%");
        card.addStyleName(ValoTheme.LAYOUT_CARD);

        HorizontalLayout toolbar = new HorizontalLayout();
        toolbar.addStyleName("dashboard-panel-toolbar");
        toolbar.setWidth("100%");

      final  Label caption = new Label(chart.getCaption());
        caption.addStyleName(ValoTheme.LABEL_H4);
        caption.addStyleName(ValoTheme.LABEL_COLORED);
        caption.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        chart.setCaption(null);

        MenuBar tools = new MenuBar();
        tools.addStyleName(ValoTheme.MENUBAR_BORDERLESS);
        final MenuItem print = tools.addItem("", FontAwesome.PRINT, new Command() {

   				@Override
   				public void menuSelected(final MenuItem selectedItem) {
   					
   					
   					generarPDF(conf, caption.getValue());
   				}
   			});
   			print.setStyleName("icon-only");
        MenuItem max = tools.addItem("", FontAwesome.EXPAND, new Command() {

            @Override
            public void menuSelected(final MenuItem selectedItem) {
                if (!componentGraficoUsuarios.getStyleName().contains("max")) {
                    selectedItem.setIcon(FontAwesome.COMPRESS);
                    toggleMaximized(componentGraficoUsuarios, true);
                } else {
                	componentGraficoUsuarios.removeStyleName("max");
                    selectedItem.setIcon(FontAwesome.EXPAND);
                    toggleMaximized(componentGraficoUsuarios, false);
                }
            }
        });
        max.setStyleName("icon-only");
   
       
      
        tablaUsuariosConectados.addStyleName("top10-revenue");
        Responsive.makeResponsive(tablaUsuariosConectados);
        

        toolbar.addComponents(caption, tools);
        toolbar.setExpandRatio(caption, 1);
        toolbar.setComponentAlignment(caption, Alignment.MIDDLE_LEFT);
        hLayout.addComponent(chart);
        hLayout.addComponent(tablaUsuariosConectados);
        hLayout.setSpacing(true);
        hLayout.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
        card.addComponents(toolbar, hLayout);
        componentGraficoUsuarios.addComponent(card);
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
 
  
    private void buildTableMinutosTotales(){
    	
    	tablaMinutosTotales.setSizeFull();
    	tablaMinutosTotales.addStyleName(ValoTheme.TABLE_BORDERLESS);
    	tablaMinutosTotales.addStyleName(ValoTheme.TABLE_NO_STRIPES);
    	tablaMinutosTotales.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
    	tablaMinutosTotales.addStyleName(ValoTheme.TABLE_SMALL);
      	tablaMinutosTotales.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    	tablaMinutosTotales.addContainerProperty("País", String.class, null);
    	tablaMinutosTotales.addContainerProperty("Tiempo",  BigDecimal.class, null);
    	tablaMinutosTotales.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
    	tablaMinutosTotales.setColumnAlignment("Tiempo", Align.RIGHT);
    	tablaMinutosTotales.setSortContainerPropertyId("Tiempo");
    	tablaMinutosTotales.setSortAscending(false);
    	tablaMinutosTotales.setColumnWidth(null, 40);
    	
    }
      
     private void fillTableMinutosTotales(HashSet<Location> listaPaises){
    	tablaMinutosTotales.removeAllItems();
    	if(listaPaises != null)
        	for (Location pais : listaPaises) {
    			tablaMinutosTotales.addItem(pais);
    			Item row = tablaMinutosTotales.getItem(pais);
        		row.getItemProperty("País").setValue(pais.countryName);
    			row.getItemProperty("Tiempo").setValue(pais.getminutosConvertidos());
    			tablaMinutosTotales.setItemIcon(pais, new ThemeResource("../flags/" + pais.countryCode.toLowerCase() + ".png"));
    			
    		}

    	 tablaMinutosTotales.sort();
    

    }
    
    
    private void buildTableUsuariosConectados(){
    	tablaUsuariosConectados.setSizeFull();
    	tablaUsuariosConectados.addStyleName(ValoTheme.TABLE_BORDERLESS);
    	 tablaUsuariosConectados.addStyleName(ValoTheme.TABLE_NO_STRIPES);
    	 tablaUsuariosConectados.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
    	 tablaUsuariosConectados.addStyleName(ValoTheme.TABLE_SMALL);
    
    	 tablaUsuariosConectados.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    	
    	
    	 tablaUsuariosConectados.addContainerProperty("País", String.class, null);
    	 tablaUsuariosConectados.addContainerProperty("Sesiones",  Integer.class, null);
    	 tablaUsuariosConectados.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
  
    	 tablaUsuariosConectados.setColumnWidth(null, 40);
    	 tablaUsuariosConectados.setColumnAlignment("Sesiones", Align.RIGHT);
    	 tablaUsuariosConectados.setSortContainerPropertyId("Sesiones");
    	 tablaUsuariosConectados.setSortAscending(false);
    	
    	
    }
    
    private void fillTablaUsuariosConectados(HashSet<Location> listaPaises){
    	tablaUsuariosConectados.removeAllItems();
    	if(listaPaises != null)
        	for (Location pais : listaPaises) {
        		tablaUsuariosConectados.addItem(pais);
    			Item row = tablaUsuariosConectados.getItem(pais);
        		row.getItemProperty("País").setValue(pais.countryName);
    			row.getItemProperty("Sesiones").setValue(pais.getCantidadUsuarios());
    			tablaUsuariosConectados.setItemIcon(pais, new ThemeResource("../flags/" + pais.countryCode.toLowerCase() + ".png"));
    			
    		}

    	tablaUsuariosConectados.sort();
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
		
	    
	    public void fillData(HashSet<Location> listaPaises){
	    	fillTableMinutosTotales(listaPaises);
	    	fillTablaUsuariosConectados(listaPaises);
	    	fillContentWrapperGraficoUsuarios(listaPaises);
	    	fillContentWrapperGraficoMinutos(listaPaises);
	    	
	    }
	  
	    public void setClienteService(ClienteService clienteService){
	    	this.clienteService = clienteService;
	    }
	    
	    public void setLookupService(LookupService cl){
	    	this.cl = cl;
	    }
}
