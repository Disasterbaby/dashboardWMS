package com.dashboardwms.components;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.service.AplicacionService;
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

	public LookupService cl;
    private final Table table = new Table();
    public Servidor servidor;
    public AplicacionService aplicacionService;

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
    	table.addContainerProperty("Dirección IP",  String.class, null);
    	table.addContainerProperty("Protocolo", String.class, null);
    	table.addContainerProperty("Tiempo de Conexión", String.class, null);
    	table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
    	table.setFooterVisible(true);

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
