package com.dashboardwms.service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.geoip.Country;
import com.geoip.LookupService;
import com.vaadin.data.Item;
import com.vaadin.server.ThemeResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Table;
import com.vaadin.ui.themes.Runo;
import com.vaadin.ui.themes.ValoTheme;

public class GeoLocalization {
	
	public GeoLocalization(){
		
		
	}

	public Table getLocalization(){
		Table table = new Table("tabla");
	
		try {
			
			
			String basepath = VaadinService.getCurrent().getBaseDirectory().getAbsolutePath();
			String dbfile = basepath + "/location/GeoIP.dat";
			// You should only call LookupService once, especially if you use
			// GEOIP_MEMORY_CACHE mode, since the LookupService constructor takes up
			// resources to load the GeoIP.dat file into memory
			//LookupService cl = new LookupService(dbfile,LookupService.GEOIP_STANDARD);
			LookupService cl = new LookupService(dbfile,LookupService.GEOIP_MEMORY_CACHE);

			Country country1 = new Country(cl.getCountry("151.38.39.114").getCode(), cl.getCountry("151.38.39.114").getName(), "151.38.39.114");
			Country country2 = new Country(cl.getCountry("12.25.205.51").getCode(), cl.getCountry("12.25.205.51").getName(), "12.25.205.51");
			Country country3 = new Country(cl.getCountry("64.81.104.131").getCode(), cl.getCountry("64.81.104.131").getName(), "64.81.104.131");
			Country country4 = new Country(cl.getCountry("200.21.225.82").getCode(), cl.getCountry("200.21.225.82").getName(), "200.21.225.82" );
			List<Country> listaPaises = new ArrayList<Country>();
			listaPaises.add(country1);
			listaPaises.add(country2);
			listaPaises.add(country3);
			listaPaises.add(country4);
			
			table.addContainerProperty("Localidad", String.class, null);
			table.addContainerProperty("Dirección IP",  String.class, null);
			for (Country pais : listaPaises) {
				table.addItem(pais);
				
				Item row = table.getItem(pais);
				
				row.getItemProperty("Dirección IP").setValue(pais.getIp());
				row.getItemProperty("Localidad").setValue(pais.getName());
				table.setItemIcon(pais, new ThemeResource("../flags/" + pais.getCode().toLowerCase() + ".png"));
			}

	table.setRowHeaderMode(Table.RowHeaderMode.ICON_ONLY);
	table.addStyleName(ValoTheme.TABLE_COMPACT);
	table.addStyleName(Runo.TABLE_SMALL);
		
			cl.close();
			}
			catch (IOException e) {
			System.out.println("IO Exception");
			}
		return table;
		}
	
	}

