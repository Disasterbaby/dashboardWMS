package com.dashboardwms.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.ClienteDAO;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteDAO clienteDao;

	public HashSet<Location> getCantidadClientesPorPais(
			String nombreAplicacion, final LookupService cl) {
		List<Cliente> listaClientesPais = new ArrayList<Cliente>();
		HashSet<Location> hsClientesPais = new HashSet<>();

		if (nombreAplicacion.equalsIgnoreCase("Todas")) {
			listaClientesPais = clienteDao.getListaIpTodos();
		} else {
			listaClientesPais = clienteDao
					.getListaIpPorAplicacion(nombreAplicacion);
		}

		for (Cliente cliente : listaClientesPais) {
			Location location = cl.getLocation(cliente.getIpAddress());
			Location pais = new Location(location.countryCode,
					location.countryName);
			cliente.setLocation(pais);
				hsClientesPais.add(pais);
		}

		for (Location location : hsClientesPais) {
			double minutos = 0;
			int total = 0;
			for (Cliente cliente : listaClientesPais) {
				if (cliente.getLocation().equals(location)) {
					total = total + 1;
					minutos = minutos + cliente.getTimeRunning();
				}
			}
			
			location.setCantidadUsuarios(total);
			location.setMinutosEscuchados(minutos);
		}

	
		return hsClientesPais;
	}
	
	
	

}
