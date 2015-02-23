package com.dashboardwms.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.ClienteDAO;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.geoip.Location;
import com.dashboardwms.geoip.LookupService;
import com.dashboardwms.utilities.Utilidades;

@Service
public class ClienteServiceImpl implements ClienteService {

	@Autowired
	ClienteDAO clienteDao;
	
	@Autowired
	AplicacionService aplicacionService;

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

	@Override
	public List<Cliente> getClientesPorFecha(Date fecha, String nombre) {
		String fechaString = Utilidades.DATE_QUERY.format(fecha);
		return clienteDao.getClientesPorFecha(fechaString, nombre);
	}

	@Override
	public List<Cliente> getClientesRangoFechas(String nombre,
			Date fechaInicio, Date fechaFin) {
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		return clienteDao.getClientesRangoFechas(nombre, fechaInicioString, fechaFinString);
	}

	@Override
	public Double getCantidadMinutosRangoFecha(String nombre,
			Date fechaInicio, Date fechaFin) {
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		return clienteDao.getCantidadMinutosRangoFecha(nombre, fechaInicioString, fechaFinString);
	}

	@Override
	public Double getAvgMinutosRangoFecha(String nombre, Date fechaInicio,
			Date fechaFin) {
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		return clienteDao.getAvgMinutosRangoFecha(nombre, fechaInicioString, fechaFinString);
	}
	
	private Integer getCantidadUsuariosRangoFecha(String nombre, Date fechaInicio, Date fechaFin){

		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		return clienteDao.getCantidadUsuariosRangoFecha(nombre, fechaInicioString, fechaFinString);
	}
	
	
	public LinkedHashMap<String, Double> getInfoRangoFechas(String nombre, Date fechaInicio, Date fechaFin){
		LinkedHashMap<String, Double> info = new LinkedHashMap<>();
		info.put("Total de Oyentes", getCantidadUsuariosRangoFecha(nombre, fechaInicio, fechaFin).doubleValue());
		info.put("Pico de Oyentes", aplicacionService.getPicoUsuariosRangoFecha(nombre, fechaInicio, fechaFin).doubleValue());
		info.put("Total Minutos Escuchados", getCantidadMinutosRangoFecha(nombre, fechaInicio, fechaFin)/60);
		info.put("Promedio Minutos por Oyente", getAvgMinutosRangoFecha(nombre, fechaInicio, fechaFin)/60);
	return info;
		
	}

	@Override
	public HashSet<Location> getClientesPorPaisFechas(String nombre,
			Date fechaInicio, Date fechaFin, final LookupService cl) {
		List<Cliente> listaClientesPais = new ArrayList<Cliente>();
		HashSet<Location> hsClientesPais = new HashSet<>();

		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		
			listaClientesPais = clienteDao.getListaIPPorAplicacionFechas(nombre, fechaInicioString, fechaFinString);
		

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

	@Override
	public LinkedHashMap<Date, Integer> getUsuariosConectadosPorHora(
			String nombre, Date fechaInicio, Date fechaFin) throws InvalidResultSetAccessException, ParseException {

		LinkedHashMap<Date, Integer>  hashmap= new LinkedHashMap<>() ;
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		SqlRowSet rowset = clienteDao.getUsuariosConectadosPorHora(nombre, fechaInicioString, fechaFinString);
		 while (rowset.next()) {
			 hashmap.put(Utilidades.DATE_FORMAT.parse(rowset.getString("hora")),rowset.getInt("total"));
			  }
		return hashmap;
	}

	@Override
	public LinkedHashMap<String, Integer> getCantidadDispositivosRangoFechas(
			String nombre, Date fechaInicio, Date fechaFin) {
		LinkedHashMap<String, Integer>  hashmap= new LinkedHashMap<>() ;
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		SqlRowSet rowset = clienteDao.getClientesUsadosRangoFechas(nombre, fechaInicioString, fechaFinString);

		while (rowset.next()) {
			String dispositivo = rowset.getString("protocolo");
			if(dispositivo.contains("smooth")){
				dispositivo = "Windows Phone";
			}
			if(dispositivo.contains("RTP")){
				dispositivo = "Android / BlackBerry";
			
			}
			if(dispositivo.contains("cupertino")){
				dispositivo = "iPad / iPhone";
			}
			if(dispositivo.contains("RTMP")){
				dispositivo = "Escritorio";
			
			}
			 hashmap.put(dispositivo,rowset.getInt("total"));
			  }
		return hashmap;
	}

}
