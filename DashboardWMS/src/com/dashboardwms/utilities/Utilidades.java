package com.dashboardwms.utilities;

import com.vaadin.server.VaadinService;

public class Utilidades {
 public static final String XML_URL = "http://s1.serverht.net:8086/serverinfo";

public static final String ABSOLUTE_PATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
 public static final String LOCATIONS_DB = ABSOLUTE_PATH + "/location/GeoLiteCity.dat";
}
