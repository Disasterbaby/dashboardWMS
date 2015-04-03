package com.dashboardwms.components;

import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.ParserConfigurationException;

import org.springframework.jdbc.InvalidResultSetAccessException;
import org.xml.sax.SAXException;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.service.XLSReadingService;
import com.dashboardwms.service.XMLConnectionService;
import com.vaadin.data.Item;
import com.vaadin.server.FontAwesome;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Alignment;
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

public class HomePanel extends Panel {

	private CssLayout dashboardPanels;
	private final VerticalLayout root;
	private XLSReadingService xlsReadingService;
	private ClienteService clienteService;
    private XMLConnectionService xmlConnectionService;
	private Date today = new Date();
	private final Table tablaInformacionMovil = new Table();
	private final Table tablaInformacionDia = new Table();
	private final Table tablaInformacionTiempoReal = new Table();
    private LookupService cl;
    private final Table tablaUsuariosPaises = new Table();

	private String emisora;
	
	private Double totalSesiones = 0.0;
	private Double totalRegistros = 0.0;
	private Double totalMinutos = 0.0;
	private Double topeMinutos = 0.0;
	private Double topeSesiones = 0.0;
	private Double topeRegistros = 0.0;
	
	

	public void setXMLConnectionService(XMLConnectionService xmlConnectionService){
		this.xmlConnectionService = xmlConnectionService;
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
	
	public HomePanel() {
		addStyleName(ValoTheme.PANEL_BORDERLESS);
		setSizeFull();
		root = new VerticalLayout();
		root.setSizeFull();
		root.setMargin(true);
		root.addStyleName("dashboard-view");
		setContent(root);
		Responsive.makeResponsive(root);

		Responsive.makeResponsive(tablaInformacionMovil);
		Responsive.makeResponsive(tablaUsuariosPaises);
		Responsive.makeResponsive(tablaInformacionDia);
		Responsive.makeResponsive(tablaInformacionTiempoReal);
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
		Label title = new Label("Resumen Diario");
		title.setSizeUndefined();
		title.addStyleName(ValoTheme.LABEL_H1);
		title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
		header.addComponent(title);

		return header;
	}


	private Component buildContent() {
		dashboardPanels = new CssLayout();
		dashboardPanels.addStyleName("dashboard-panels");
		Responsive.makeResponsive(dashboardPanels);


		Component componentTablaTiempoReal = buildComponentTablaTiempoReal();
		dashboardPanels.addComponent(componentTablaTiempoReal);
		
		Component componentTablaInfoDia = buildComponentTablaInfoDia();
		dashboardPanels.addComponent(componentTablaInfoDia);
		
		Component componentInfoMovil = buildComponentTablaInfoMovil();
		dashboardPanels.addComponent(componentInfoMovil);
		
		Component componentInfoPaises = buildComponentTablaInfoPaises();
		dashboardPanels.addComponent(componentInfoPaises);
		
		
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
		
		fillTableMovil();
		fillTableDia(today, today);
		fillTablaUsuariosPaises();
		fillTablaTiempoReal();
		
	}

	private void fillTableMovil() {

		LinkedHashMap<Date, Double> listaMinutos = new LinkedHashMap<>();

		listaMinutos = xlsReadingService.getStreamingMinutes(emisora, today, today);
		

		LinkedHashMap<Date, Integer> listaSesiones = new LinkedHashMap<>();

		listaSesiones = xlsReadingService.getStreamingSessions(emisora, today, today);
		
		
		LinkedHashMap<Date, Integer> listaRegistros = new LinkedHashMap<>();

		listaRegistros = xlsReadingService.getCustomRegistrations(emisora, today, today);
		
		Iterator it = listaMinutos.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			Double minutos = (Double) pairs.getValue();
			
			totalMinutos = totalMinutos + minutos;
			if(minutos > topeMinutos)
				topeMinutos = minutos;
			it.remove();
		}
		
		Iterator it2 = listaSesiones.entrySet().iterator();
		while (it2.hasNext()) {

			Map.Entry pairs = (Map.Entry) it2.next();
			Integer sesiones = (Integer) pairs.getValue();
			
			totalSesiones = totalSesiones + sesiones;
			if(sesiones > topeSesiones)
				topeSesiones = sesiones.doubleValue();
			it2.remove();
		}
		
		Iterator it3 = listaRegistros.entrySet().iterator();
		while (it3.hasNext()) {

			Map.Entry pairs = (Map.Entry) it3.next();

			Integer registros = (Integer) pairs.getValue();
			totalRegistros = totalRegistros + registros;
			if(registros > topeRegistros)
				topeRegistros = registros.doubleValue();
			it3.remove();
		}
		
		tablaInformacionMovil.removeAllItems();				
		
		tablaInformacionMovil.addItem("TotalSesiones");

		tablaInformacionMovil.addItem("TopeSesiones");
		tablaInformacionMovil.addItem("TotalMinutos");

		tablaInformacionMovil.addItem("TopeMinutos");
		tablaInformacionMovil.addItem("TotalRegistros");
		tablaInformacionMovil.addItem("TopeRegistros");
			
			Item row = tablaInformacionMovil.getItem("TotalSesiones");
			row.getItemProperty("Key").setValue("Total de Sesiones");
			row.getItemProperty("Value").setValue(totalSesiones);

			Item row1 = tablaInformacionMovil.getItem("TotalMinutos");
			row1.getItemProperty("Key").setValue("Total de Minutos");
			row1.getItemProperty("Value").setValue(totalMinutos);

			Item row2 = tablaInformacionMovil.getItem("TotalRegistros");
			row2.getItemProperty("Key").setValue("Total de Registros");
			row2.getItemProperty("Value").setValue(totalRegistros);

			Item row3 = tablaInformacionMovil.getItem("TopeSesiones");
			row3.getItemProperty("Key").setValue("Tope de Sesiones");
			row3.getItemProperty("Value").setValue(topeSesiones);

			Item row4 = tablaInformacionMovil.getItem("TopeMinutos");
			row4.getItemProperty("Key").setValue("Tope de Minutos");
			row4.getItemProperty("Value").setValue(topeMinutos);

			Item row5 = tablaInformacionMovil.getItem("TopeRegistros");
			row5.getItemProperty("Key").setValue("Tope de Registros");
			row5.getItemProperty("Value").setValue(topeRegistros);

			
		

	}
	

	private Component buildComponentTablaInfoMovil() {
		buildTableInfoMovil();
		Component contentWrapper = createContentWrapper(tablaInformacionMovil);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}
	
	private Component buildComponentTablaTiempoReal() {
		buildTableTiempoReal();
		Component contentWrapper = createContentWrapper(tablaInformacionTiempoReal);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}
	
	private Component buildComponentTablaInfoPaises() {
		buildTableUsuariosPaises();
		Component contentWrapper = createContentWrapper(tablaUsuariosPaises);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}
	
	private void buildTableInfoMovil() {

		tablaInformacionMovil.setSizeFull();
		tablaInformacionMovil.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaInformacionMovil.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaInformacionMovil.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaInformacionMovil.addStyleName(ValoTheme.TABLE_SMALL);
		tablaInformacionMovil.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaInformacionMovil.addContainerProperty("Key", String.class, null);
		tablaInformacionMovil.addContainerProperty("Value", Double.class,
				null);
		tablaInformacionMovil.setColumnAlignment("Value", Align.RIGHT);
		tablaInformacionMovil.setCaption("Aplicación Móvil");
	}
	
	private void buildTableTiempoReal() {

		tablaInformacionTiempoReal.setSizeFull();
		tablaInformacionTiempoReal.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaInformacionTiempoReal.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaInformacionTiempoReal.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaInformacionTiempoReal.addStyleName(ValoTheme.TABLE_SMALL);
		tablaInformacionTiempoReal.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaInformacionTiempoReal.addContainerProperty("Key", String.class, null);
		tablaInformacionTiempoReal.addContainerProperty("Value", String.class,
				null);
		tablaInformacionTiempoReal.setColumnAlignment("Value", Align.RIGHT);
		tablaInformacionTiempoReal.setCaption("Tiempo Real");
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

	private Component buildComponentTablaInfoDia() {
		buildTableInfoDia();
		Component contentWrapper = createContentWrapper(tablaInformacionDia);

		contentWrapper.addStyleName("top10-revenue");
		Responsive.makeResponsive(contentWrapper);
		return contentWrapper;
	}


	private void buildTableInfoDia() {

		tablaInformacionDia.setSizeFull();
		tablaInformacionDia.addStyleName(ValoTheme.TABLE_BORDERLESS);
		tablaInformacionDia.addStyleName(ValoTheme.TABLE_NO_STRIPES);
		tablaInformacionDia.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
		tablaInformacionDia.addStyleName(ValoTheme.TABLE_SMALL);
		tablaInformacionDia.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
		tablaInformacionDia.addContainerProperty("Key", String.class, null);
		tablaInformacionDia.addContainerProperty("Value", Double.class,
				null);
		tablaInformacionDia.setColumnAlignment("Value", Align.RIGHT);
		tablaInformacionDia.setCaption("Oyentes");
	}
	
    private void buildTableUsuariosPaises(){
    	tablaUsuariosPaises.setSizeFull();
    	tablaUsuariosPaises.addStyleName(ValoTheme.TABLE_BORDERLESS);
    	tablaUsuariosPaises.addStyleName(ValoTheme.TABLE_NO_STRIPES);
    	tablaUsuariosPaises.addStyleName(ValoTheme.TABLE_NO_VERTICAL_LINES);
    	tablaUsuariosPaises.addStyleName(ValoTheme.TABLE_SMALL);
    
    	tablaUsuariosPaises.setColumnHeaderMode(ColumnHeaderMode.HIDDEN);
    	
    	
    	tablaUsuariosPaises.addContainerProperty("País", String.class, null);
    	tablaUsuariosPaises.addContainerProperty("Sesiones",  Integer.class, null);
    	tablaUsuariosPaises.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
  
    	tablaUsuariosPaises.setColumnWidth(null, 40);
    	 tablaUsuariosPaises.setColumnAlignment("Sesiones", Align.RIGHT);
    	 tablaUsuariosPaises.setSortContainerPropertyId("Sesiones");
    	 tablaUsuariosPaises.setSortAscending(false);
    	 tablaUsuariosPaises.setCaption("Sesiones Por País");
    	
    }
    
    
    private void fillTablaUsuariosPaises(){

    	HashSet<Location> listaPaises = clienteService.getClientesPorPaisFechas(emisora, today, today, cl);
    	tablaUsuariosPaises.removeAllItems();
    	if(listaPaises != null)
        	for (Location pais : listaPaises) {
        		tablaUsuariosPaises.addItem(pais);
    			Item row = tablaUsuariosPaises.getItem(pais);
        		row.getItemProperty("País").setValue(pais.countryName);
    			row.getItemProperty("Sesiones").setValue(pais.getCantidadUsuarios());
    			tablaUsuariosPaises.setItemIcon(pais, new ThemeResource("../flags/" + pais.countryCode.toLowerCase() + ".png"));
    			
    		}

    	tablaUsuariosPaises.sort();
    }
	
    private void fillTablaTiempoReal() throws ParserConfigurationException, SAXException, IOException{
    	Servidor servidor = xmlConnectionService.getLiveData();
    	Integer horas = 0;
Integer dispositivosMoviles=0;
Integer escritorio=0;
		List<Cliente> listaClientes = new ArrayList<Cliente>();
    	
    			for (Aplicacion aplicacion : servidor.getListaAplicaciones()) {
					if(aplicacion.getNombre().equalsIgnoreCase(emisora)){

						listaClientes = aplicacion.getListaClientes();
						horas = (int) (aplicacion.getTiempoCorriendo()/3600);
						break;
					}
				}
   		Integer usuariosConectados = 0;
		tablaInformacionTiempoReal.removeAllItems();
		if(!listaClientes.isEmpty())
		{	
			usuariosConectados = listaClientes.size();
	
			
			tablaInformacionTiempoReal.addItem("Usuarios");

			Item row = tablaInformacionTiempoReal.getItem("Usuarios");
			row.getItemProperty("Key").setValue("Usuarios Conectados");
			row.getItemProperty("Value").setValue(usuariosConectados.toString());
			

			tablaInformacionTiempoReal.addItem("Horas");

			Item row2 = tablaInformacionTiempoReal.getItem("Horas");
			row2.getItemProperty("Key").setValue("Horas Transmitidas");
			row2.getItemProperty("Value").setValue(horas.toString());
			
			
			for (Cliente cliente : listaClientes) {
				if(cliente.getDispositivo().equalsIgnoreCase("Escritorio"))
					escritorio++;
				else 
					dispositivosMoviles++;
		
				}
			
			tablaInformacionTiempoReal.addItem("Escritorio");
			

			tablaInformacionTiempoReal.addItem("Movil");

			Item row3 = tablaInformacionTiempoReal.getItem("Escritorio");
			row3.getItemProperty("Key").setValue("Conexiones Escritorio");
			row3.getItemProperty("Value").setValue(escritorio.toString());
			
			Item row4 = tablaInformacionTiempoReal.getItem("Movil");
			row4.getItemProperty("Key").setValue("Conexiones Disp. Móviles");
			row4.getItemProperty("Value").setValue(dispositivosMoviles.toString());
			
		}


    }
	
    
    
	private void fillTableDia(Date fechaInicio, Date fechaFin) {
		tablaInformacionDia.removeAllItems();				
		LinkedHashMap<String, Double> info = clienteService.getInfoRangoFechas(
				emisora, fechaInicio, fechaFin);
		Iterator it = info.entrySet().iterator();
		while (it.hasNext()) {

			Map.Entry pairs = (Map.Entry) it.next();
			tablaInformacionDia.addItem(pairs);
			System.out.println(pairs.getKey());

			Item row = tablaInformacionDia.getItem(pairs);
			row.getItemProperty("Key").setValue(pairs.getKey());
			row.getItemProperty("Value").setValue(pairs.getValue());

			it.remove();
		}

	}
	

    public void setLookupService(LookupService cl){
    	this.cl = cl;
    }
}

