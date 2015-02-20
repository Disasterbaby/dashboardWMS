package com.dashboardwms.components;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.events.DashboardEvent.BrowserResizeEvent;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
import com.google.common.eventbus.Subscribe;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
import com.vaadin.server.Page;
import com.vaadin.server.Responsive;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.ComboBox;
import com.vaadin.ui.Component;
import com.vaadin.ui.HorizontalLayout;
import com.vaadin.ui.Label;
import com.vaadin.ui.Table;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.themes.ValoTheme;

public class LiveDataLayout extends VerticalLayout {

	public LookupService cl;
    private final Table table = new Table();
    public Servidor servidor;
    public AplicacionService aplicacionService;
    private static final String[] DEFAULT_COLLAPSIBLE = {"Protocolo", "Cliente", "Sistema Operativo"};
 
    
 public LiveDataLayout() {
    	Responsive.makeResponsive(this);
        setSizeFull();
        addStyleName("transactions");
        addComponent(buildToolbar());
        buildTable();
        table.setSizeFull();
        addComponent(table);
        setExpandRatio(table, 1);
        
//	cboxAplicaciones.addValueChangeListener(new ValueChangeListener() {
//			
//			@Override
//			public void valueChange(ValueChangeEvent event) {
//				Aplicacion aplicacionSeleccionada = (Aplicacion)cboxAplicaciones.getValue();
//			
//				fillTable(aplicacionSeleccionada.getListaClientes());
//			}		});
//		
		
    }

    private Component buildToolbar() {
        HorizontalLayout header = new HorizontalLayout();
        header.addStyleName("viewheader");
        header.setSpacing(true);
        Responsive.makeResponsive(header);
        buildFilter();
        Label title = new Label("Usuarios Conectados");
        title.setSizeUndefined();
        title.addStyleName(ValoTheme.LABEL_H1);
        title.addStyleName(ValoTheme.LABEL_NO_MARGIN);
        header.addComponent(title);
//        HorizontalLayout tools = new HorizontalLayout(cboxAplicaciones);
//        tools.setSpacing(true);
//        tools.addStyleName("toolbar");
//        header.addComponent(tools);

        return header;
    }


    private void buildFilter() {

	
		
    	
    }

    private void buildTable() {
    	table.addContainerProperty("Localidad", String.class, null);
    	table.addContainerProperty("Direcci�n IP",  String.class, null);
    	table.addContainerProperty("Dispositivo", String.class, null);
    	table.addContainerProperty("Cliente", String.class, null);
    	table.addContainerProperty("Sistema Operativo", String.class, null);
    	table.addContainerProperty("Tiempo de Conexi�n", String.class, null);
    	table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
    	table.setFooterVisible(true);
    	table.addStyleName(ValoTheme.TABLE_BORDERLESS);
        table.addStyleName(ValoTheme.TABLE_NO_HORIZONTAL_LINES);
        table.addStyleName(ValoTheme.TABLE_COMPACT);
        table.setSelectable(true);

          table.setColumnCollapsingAllowed(true);
          table.setColumnCollapsible("Localidad", false);
          table.setColumnCollapsible("Direcci�n IP", false);
          table.setColumnCollapsible("Tiempo de Conexi�n", false);
          table.setColumnReorderingAllowed(true);

    }

 


//
//    public void fillComboBox(Servidor servidor){
//    	
//    	
//    	listaAplicaciones.removeAllItems();
//		aplicacionTodas.setListaClientes(aplicacionService.getTodosClientes(servidor));
//		listaAplicaciones.addItemAt(0, aplicacionTodas);
//		listaAplicaciones.addAll(servidor.getListaAplicaciones());
//		cboxAplicaciones.setContainerDataSource(listaAplicaciones);
//		cboxAplicaciones.setItemCaptionPropertyId("nombre");
//		cboxAplicaciones.setValue(aplicacionTodas);
//		
//	
//    }
//    
    private boolean defaultColumnsVisible() {
        boolean result = true;
        for (String propertyId : DEFAULT_COLLAPSIBLE) {
            if (table.isColumnCollapsed(propertyId) == Page.getCurrent()
                    .getBrowserWindowWidth() < 800) {
                result = false;
            }
        }
        return result;
    }

    @Subscribe
    public void browserResized(final BrowserResizeEvent event) {
        // Some columns are collapsed when browser window width gets small
        // enough to make the table fit better.
        if (defaultColumnsVisible()) {
            for (String propertyId : DEFAULT_COLLAPSIBLE) {
                table.setColumnCollapsed(propertyId, Page.getCurrent()
                        .getBrowserWindowWidth() < 800);
            }
        }
    }

    
	public void fillTable(List<Cliente> listaClientes){
		Integer usuariosConectados = 0;
		table.removeAllItems();
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
			row.getItemProperty("Direcci�n IP").setValue(cliente.getIpAddress());
			row.getItemProperty("Protocolo").setValue(cliente.getProtocolo());
			row.getItemProperty("Cliente").setValue(cliente.getCliente());
			row.getItemProperty("Sistema Operativo").setValue(cliente.getSistemaOperativo());
			row.getItemProperty("Tiempo de Conexi�n").setValue(cliente.getTiempoString());
			table.setItemIcon(cliente, new ThemeResource("../flags/" + location.countryCode.toLowerCase() + ".png"));
			table.setColumnWidth(null, 40);
		}
		}
		

	
		table.setColumnFooter("Localidad", "Total: " + usuariosConectados);
			cl.close();
			
		
		}
  
}
