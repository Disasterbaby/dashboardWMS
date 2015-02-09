package com.dashboardwms.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.vaadin.server.VaadinService;

public class Utilidades {
 public static final String XML_URL = "http://s1.serverht.net:8086/serverinfo";

public static final String ABSOLUTE_PATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
 public static final String LOCATIONS_DB = ABSOLUTE_PATH + "/location/GeoLiteCity.dat";


 public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
 public static final DateFormat DATE_QUERY = new SimpleDateFormat("yyyy-MM-dd");
 
 public static final DateFormat DATE_FORMAT_LOCAL = new SimpleDateFormat("dd-MM-yyyy");
 
 public static final String ULTIMA_SEMANA = "Última Semana";
		 
 public static final String ULTIMA_QUINCENA = "Últimos 15 Días";
 
 public static final String ULTIMO_MES = "Últimos 30 Días";
 
 public static final List<String> LISTA_PERIODOS = Arrays.asList(ULTIMA_SEMANA, ULTIMA_QUINCENA, ULTIMO_MES);

 
 }
