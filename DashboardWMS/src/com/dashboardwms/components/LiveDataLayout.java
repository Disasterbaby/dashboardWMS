package com.dashboardwms.components;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.service.DashboardService;
import com.geoip.Location;
import com.geoip.LookupService;
import com.vaadin.data.Item;
import com.vaadin.data.Property.ValueChangeEvent;
import com.vaadin.data.Property.ValueChangeListener;
import com.vaadin.data.util.BeanItemContainer;
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


    private final Table table;
    private final ComboBox cboxAplicaciones = new ComboBox();

    public LiveDataLayout(DashboardService dashboardService) {
        setSizeFull();
        addStyleName("transactions");
        addComponent(buildToolbar());

        table = buildTable();
        table.setSizeFull();
        addComponent(table);
        setExpandRatio(table, 1);
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
        HorizontalLayout tools = new HorizontalLayout(cboxAplicaciones);
        tools.setSpacing(true);
        tools.addStyleName("toolbar");
        header.addComponent(tools);

        return header;
    }


    private void buildFilter() {

		cboxAplicaciones.setImmediate(true);
		cboxAplicaciones.setNullSelectionAllowed(false);
		cboxAplicaciones.setInvalidAllowed(false);
		
    	
    }

    private Table buildTable() {
    	Table table = new Table();
    	table.addContainerProperty("Localidad", String.class, null);
    	table.addContainerProperty("Dirección IP",  String.class, null);
    	table.addContainerProperty("Protocolo", String.class, null);
    	table.addContainerProperty("Tiempo de Conexión", String.class, null);
    	table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
    	table.setFooterVisible(true);
    	
    	return table;
    }

 



    public void fillComboBox(Servidor servidor, DashboardService dashboardService, final LookupService cl){
    	cboxAplicaciones.removeAllItems();
		BeanItemContainer<Aplicacion> listaAplicaciones = new BeanItemContainer<Aplicacion>(Aplicacion.class);
		Aplicacion aplicacionTodas = new Aplicacion();
		aplicacionTodas.setNombre("Todas");
		aplicacionTodas.setListaClientes(dashboardService.getTodosClientes(servidor));
		listaAplicaciones.addItemAt(0, aplicacionTodas);
		listaAplicaciones.addAll(servidor.getListaAplicaciones());
		cboxAplicaciones.setContainerDataSource(listaAplicaciones);
		cboxAplicaciones.setItemCaptionPropertyId("nombre");
		
		cboxAplicaciones.addValueChangeListener(new ValueChangeListener() {
			
			@Override
			public void valueChange(ValueChangeEvent event) {
				Aplicacion aplicacionSeleccionada = (Aplicacion)cboxAplicaciones.getValue();
			
				fillTable(cl, aplicacionSeleccionada.getListaClientes());
			}		});
		
		cboxAplicaciones.setValue(aplicacionTodas);
    }
    
	public void fillTable(LookupService cl, List<Cliente> listaClientes){
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
			row.getItemProperty("Dirección IP").setValue(cliente.getIpAddress());
			row.getItemProperty("Protocolo").setValue(cliente.getProtocolo());
			row.getItemProperty("Tiempo de Conexión").setValue(cliente.getTiempoString());
			table.setItemIcon(cliente, new ThemeResource("../flags/" + location.countryCode.toLowerCase() + ".png"));
			table.setColumnWidth(null, 40);
		}
		}
		

	
		table.setColumnFooter("Localidad", "Total: " + usuariosConectados);
			cl.close();
			
		
		}
  
}
