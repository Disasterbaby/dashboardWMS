package com.dashboardwms.service;

import java.util.Date;
import java.util.LinkedHashMap;

public interface XLSReadingService {

	public LinkedHashMap<Date, Double> getStreamingMinutes(String nombreAplicacion, Date fechaInicio, Date fechaFin);
	public LinkedHashMap<Date, Integer> getStreamingSessions(String nombreAplicacion, Date fechaInicio, Date fechaFin);
	public LinkedHashMap<Date, Integer> getCustomRegistrations(String nombreAplicacion, Date fechaInicio, Date fechaFin);
	public LinkedHashMap<String, Integer> getEstadisticas(String nombreAplicacion, Date fechaInicio, Date fechaFin);

}
