package com.dashboardwms.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;

public interface ClienteService {

	
	public HashSet<Location> getCantidadClientesPorPais(String nombreAplicacion, final LookupService cl);
}
