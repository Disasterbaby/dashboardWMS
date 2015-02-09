package com.dashboardwms.service;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import com.dashboardwms.domain.Cliente;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;

public interface ClienteService {

	
	public HashSet<Location> getCantidadClientesPorPais(String nombreAplicacion, final LookupService cl);
	

	public List<Cliente> getClientesPorFecha(Date fecha, String nombre);
	
	public List<Cliente> getClientesRangoFechas(String nombre, Date fechaInicio, Date fechaFin);
	
	public Double getCantidadMinutosRangoFecha(String nombre,
			Date fechaInicio, Date fechaFin);
	
	public Double getAvgMinutosRangoFecha(String nombre, Date fechaInicio, Date fechaFin);

	public LinkedHashMap<String, Double> getInfoRangoFechas(String nombre, Date fechaInicio, Date fechaFin);
}
