package com.dashboardwms.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import com.vaadin.server.VaadinService;

public class Utilidades {
 public static final String XML_URL = "http://s1.serverht.net:8086/serverinfo";

public static final String ABSOLUTE_PATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
 public static final String LOCATIONS_DB = ABSOLUTE_PATH + "/location/GeoLiteCity.dat";


 public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 

 public static final DateFormat DATE_QUERY = new SimpleDateFormat("yyyy-MM-dd");
 
}
