package com.dashboardwms.components;

import java.util.Calendar;
import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.dashboardwms.service.ClienteService;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.data.Item;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.shared.ui.MarginInfo;
import com.vaadin.ui.Alignment;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LiveDataLayout extends VerticalLayout {
	public LookupService cl;
    private final Table table = new Table();
    private AplicacionService aplicacionService;
    private Label horasTransmitidas = new Label("horas transmitidas");
    private Label numeroConexiones = new Label();
    Label picoDiario = new Label("pico");
	public ComboBox cboxPeriodo = new ComboBox();
	Calendar calendar = Calendar.getInstance();
	
	
 public LiveDataLayout() {
	 calendar.setTime(Utilidades.TODAY);
	 cboxPeriodo.setImmediate(true);
    	Responsive.makeResponsive(this);
        setSizeFull();
        addStyleName("transactions");
        HorizontalLayout hLayoutTitulo = buildToolbar();
        VerticalLayout vLayout = new VerticalLayout();
        vLayout.addComponent(hLayoutTitulo);
        HorizontalLayout hLayoutTitulo2 = buildSecondToolbar();
 
        vLayout.addComponent(hLayoutTitulo2);
        vLayout.setWidth("100%");

        Responsive.makeResponsive(vLayout);
        buildTable();
        table.setSizeFull();
        addComponent(vLayout);
        addComponent(table);
        setExpandRatio(table, 1);
        

    }
 
 private HorizontalLayout buildSecondToolbar() {
     HorizontalLayout header = new HorizontalLayout();
     header.addStyleName("viewsecondheader");
     header.setSpacing(true);
     header.addStyleName(ValoTheme.LAYOUT_HORIZONTAL_WRAPPING);
     header.addStyleName("toolbar");
     Responsive.makeResponsive(header);
     horasTransmitidas.setSizeFull();
     horasTransmitidas.addStyleName(ValoTheme.LABEL_H3);
     picoDiario.setSizeFull();
     picoDiario.addStyleName(ValoTheme.LABEL_H3);        
     picoDiario.addStyleName(ValoTheme.LABEL_NO_MARGIN);
     horasTransmitidas.addStyleName(ValoTheme.LABEL_NO_MARGIN);
 
     buildFilter();
     HorizontalLayout principal = new HorizontalLayout(picoDiario, horasTransmitidas);
     header.addComponent(principal);
     principal.setSizeFull();
     principal.setComponentAlignment(picoDiario, Alignment.MIDDLE_LEFT);
     principal.setComponentAlignment(horasTransmitidas, Alignment.MIDDLE_LEFT);
     Label lbVer = new Label("Ver: ");
     HorizontalLayout tools = new HorizontalLayout(lbVer, cboxPeriodo);
     tools.setComponentAlignment(lbVer, Alignment.MIDDLE_LEFT);

	Responsive.makeResponsive(tools);
	tools.setSpacing(true);
	tools.addStyleName("toolbar");
	header.addComponent(tools);
	header.setMargin(new MarginInfo(false, false, false, true));
	header.setExpandRatio(principal, 1);
	header.setComponentAlignment(principal, Alignment.MIDDLE_LEFT);
	header.setComponentAlignment(tools, Alignment.MIDDLE_RIGHT);
	header.setSizeFull();
     return header;
 }

 

    private HorizontalLayout buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        
        header.addStyleName("viewheader2");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
       
        Label title = new Label("Oyentes en Línea");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
        numeroConexiones.addStyleName(ValoTheme.LABEL_H2);
        numeroConexiones.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        
	       HorizontalLayout tools = new HorizontalLayout(numeroConexiones);
			Responsive.makeResponsive(tools);
		tools.setSpacing(true);
		tools.addStyleName("toolbar");
		tools.setSizeUndefined();
		header.addComponent(tools);


		header.setComponentAlignment(tools, Alignment.MIDDLE_LEFT);
        return header;
    }


    private void buildFilter() {

		cboxPeriodo.setImmediate(true);
		cboxPeriodo.setNullSelectionAllowed(false);
		cboxPeriodo.setInvalidAllowed(false);
		BeanItemContainer<String> listaPeriodosContainer = new BeanItemContainer<String>(
				String.class);

		listaPeriodosContainer.addAll(Utilidades.LISTA_PERIODOS);
		cboxPeriodo.setContainerDataSource(listaPeriodosContainer);
		cboxPeriodo.setTextInputAllowed(false);
		cboxPeriodo.select(Utilidades.LISTA_PERIODOS.get(0));
		
    	
    }

    private void buildTable() {
    	table.addContainerProperty("Localidad", String.class, null);
    	table.addContainerProperty("Dirección IP",  String.class, null);
    	table.addContainerProperty("Tipo de Conexión", String.class, null);
    	table.addContainerProperty("Tipo de Cliente", String.class, null);
    	table.addContainerProperty("Tiempo de Conexión", String.class, null);
    	table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
    	table.setFooterVisible(false);
    	table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

          table.setColumnCollapsingAllowed(true);
          table.setColumnCollapsible("Localidad", false);
          table.setColumnCollapsible("Dirección IP", false);
          table.setColumnCollapsible("Tiempo de Conexión", false);
          table.setColumnReorderingAllowed(true);

    }

 



    
	public void fillTable(Aplicacion aplicacion){
		Integer picoOyentes = aplicacionService.getPicoUsuariosRangoFecha(aplicacion.getNombre(), Utilidades.TODAY, Utilidades.TODAY);
	
		Integer usuariosConectados = 0;
		table.removeAllItems();
		List<Cliente> listaClientes = aplicacion.getListaClientes();
		if(listaClientes!=null)
		{	
			usuariosConectados = listaClientes.size();
			for (Cliente cliente : listaClientes) {
				table.addItem(cliente);
			Location location = cl.getLocation(cliente.getIpAddress());
			Item row = table.getItem(cliente);
			String ciudad = "";
			if(location.city != null)
				ciudad = location.city + ", ";
			row.getItemProperty("Localidad").setValue(ciudad + location.countryName);
			row.getItemProperty("Dirección IP").setValue(cliente.getIpAddress());
			row.getItemProperty("Tipo de Conexión").setValue(cliente.getDispositivo());
			row.getItemProperty("Tipo de Cliente").setValue(cliente.getCliente());
			row.getItemProperty("Tiempo de Conexión").setValue(cliente.getTiempoString());
			table.setItemIcon(cliente, new ThemeResource("../flags/" + location.countryCode.toLowerCase() + ".png"));
			table.setColumnWidth(null, 40);
		}
		}
		

		numeroConexiones.setValue(usuariosConectados + " Usuarios Conectados");
		if(usuariosConectados > picoOyentes)
			picoOyentes = usuariosConectados;
		picoDiario.setValue("Pico de Oyentes Hoy: " + picoOyentes);

		horasTransmitidas.setValue(Utilidades.timeRunning(aplicacion.getTiempoCorriendo()));
			cl.close();
			
		
		}




	public void setAplicacionService(AplicacionService aplicacionService){
		this.aplicacionService = aplicacionService;
	}
	
	

}
