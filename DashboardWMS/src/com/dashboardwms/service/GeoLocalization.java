package com.dashboardwms.service;


import java.util.List;

import com.dashboardwms.domain.Cliente;
import com.geoip.Location;
import com.geoip.LookupService;
import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;

public class GeoLocalization extends Table {
	
	public GeoLocalization(){
		setCaption("Usuarios Conectados");
		addContainerProperty("Localidad", String.class, null);
		addContainerProperty("Dirección IP",  String.class, null);
		addContainerProperty("Protocolo", String.class, null);
		addContainerProperty("Tiempo de Conexión", String.class, null);
		setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
		addStyleName(ValoTheme.TABLE_COMPACT);
		addStyleName(Runo.TABLE_SMALL);
		setFooterVisible(true);
	}

	public void setLocalization(LookupService cl, List<Cliente> listaClientes){
		Integer usuariosConectados = 0;
		removeAllItems();
		if(listaClientes!=null)
		{	
			usuariosConectados = listaClientes.size();
			for (Cliente cliente : listaClientes) {
			addItem(cliente);
			Location location = cl.getLocation(cliente.getIpAddress());
			Item row = getItem(cliente);
			String ciudad = "";
			if(location.city != null)
				ciudad = location.city + ", ";
			row.getItemProperty("Localidad").setValue(ciudad + location.countryName);
			row.getItemProperty("Dirección IP").setValue(cliente.getIpAddress());
			row.getItemProperty("Protocolo").setValue(cliente.getProtocolo());
			row.getItemProperty("Tiempo de Conexión").setValue(cliente.getTiempoString());
			setItemIcon(cliente, new ThemeResource("../flags/" + location.countryCode.toLowerCase() + ".png"));
			setColumnWidth(null, 30);
		}
		}
		

	
	setColumnFooter("Localidad", "Usuarios Conectados: " + usuariosConectados);
			cl.close();
			
		
		}
	
	}

