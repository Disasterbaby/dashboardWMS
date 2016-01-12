package com.dashboardwms.utilities;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import com.vaadin.server.VaadinService;

public class Utilidades {
 public static final String XML_URL = "http://server.plugstreaming.com:8086/serverinfo";
 
 

public static final String ABSOLUTE_PATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
 public static final String LOCATIONS_DB = ABSOLUTE_PATH + "/location/GeoLiteCity.dat";

public static final String TODAS_EMISORAS = "Todas";
 
 public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
 
 public static final Date TODAY = Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime();
 
 public static final DateFormat DATE_QUERY = new SimpleDateFormat("yyyy-MM-dd");
 
 
 public static final DateFormat DATE_FORMAT_LOCAL = new SimpleDateFormat("dd-MM-yyyy");
 
 public static final String HOY = "Hoy";
 
 public static final String ULTIMA_SEMANA = "Última Semana";
		 
 public static final String ULTIMA_QUINCENA = "Últimos 15 Días";
 
 public static final String ULTIMO_MES = "Últimos 30 Días";
 
 public static final String CUSTOM_DATE = "Período Específico";
 
 public static final List<String> LISTA_PERIODOS = Arrays.asList(HOY, ULTIMA_SEMANA, ULTIMA_QUINCENA, ULTIMO_MES, CUSTOM_DATE);

 
 public static final String STYLE_SELECTED = "selected";

 public static final String STYLE_VISIBLE = "valo-menu-visible";
 
public static final String RUTA_XLS = "C:/Users/Virgilio Melo/Desktop/Pantallas estadisticas/archivos/aplicaciones/";
 

 //public static final String RUTA_XLS = "/home/plugradio/plugstreaming/plugstds/aplicaciones/";

 public static final String RUTA_STREAMING_MINUTES = "/StreamingMinutes.xls";
 
 public static final String RUTA_STREAMING_SESSIONS = "/StreamingSessions.xls";
 
 public static final String RUTA_CUSTOM_REGISTRATIONS = "/CustomRegistrations.xls";
 
 public static final String RUTA_REGISTRATIONS_DEVICE = "/RegistrationsByDevice.xls";
 
 

public static final SimpleDateFormat formatoFechaArchivos = new SimpleDateFormat("yyyyMMdd");
 
 public static String timeRunning(double tiempo){
	 
		int hor,min; 
			int total = (int)tiempo;
			hor=total/3600;  
	        min=(total-(3600*hor))/60;  
			return "Horas Transmitidas: " + hor;
			
		
 }
 
 }
