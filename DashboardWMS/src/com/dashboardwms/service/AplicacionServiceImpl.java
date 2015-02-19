package com.dashboardwms.service;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.InvalidResultSetAccessException;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.AplicacionDAO;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.utilities.Utilidades;

@Service
public class AplicacionServiceImpl implements AplicacionService {

@Autowired
AplicacionDAO aplicacionDao;
	
	public List<Cliente> getTodosClientes(Servidor servidor) {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		List<Aplicacion> listaAplicaciones = servidor.getListaAplicaciones();
		if (listaAplicaciones != null) {
			for (Aplicacion aplicacion : listaAplicaciones) {
				List<Cliente> clientesAplicacion = aplicacion
						.getListaClientes();
				if (clientesAplicacion != null) {
					for (Cliente cliente : clientesAplicacion) {
						listaClientes.add(cliente);
					}
				}
			}
		}
		return listaClientes;
	}
	
	public List<Aplicacion> getListaAplicaciones(){
	return	aplicacionDao.getTodasAplicaciones();
	}

	@Override
	public List<String> getListaAplicacionesDistinct() {
		
		return aplicacionDao.listaAplicaciones();
	}

	@Override
	public List<Aplicacion> getTodasAplicacionesPorFecha(Date fecha) {
		String fechaString = Utilidades.DATE_QUERY.format(fecha);
		return aplicacionDao.getTodasAplicacionesPorFecha(fechaString);
	}

	@Override
	public List<Aplicacion> getAplicacionPorFecha(Date fecha, String nombre) {
		String fechaString = Utilidades.DATE_QUERY.format(fecha);
	
		return aplicacionDao.getAplicacionPorFecha(fechaString, nombre);
	}

	@Override
	public List<Aplicacion> getAplicacionRangoFechas(String nombre,
			Date fechaInicio, Date fechaFin) {
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		
		return aplicacionDao.getAplicacionRangoFechas(nombre, fechaInicioString, fechaFinString);
	}

	@Override
	public Integer getPicoUsuariosRangoFecha(String nombre, Date fechaInicio,
			Date fechaFin) {

		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		return aplicacionDao.getPicoConexiones(nombre, fechaInicioString, fechaFinString);
	}
	
	
	@Override
	public LinkedHashMap<Date, Integer> getUsuariosConectadosPorHora(
			String nombre, Date fechaInicio, Date fechaFin) throws InvalidResultSetAccessException, ParseException {

		LinkedHashMap<Date, Integer>  hashmap= new LinkedHashMap<>() ;
		String fechaInicioString = Utilidades.DATE_QUERY.format(fechaInicio);
		String fechaFinString = Utilidades.DATE_QUERY.format(fechaFin);
		SqlRowSet rowset = aplicacionDao.getUsuariosConectadosPorHora(nombre, fechaInicioString, fechaFinString);
		 while (rowset.next()) {
			 hashmap.put(Utilidades.DATE_FORMAT.parse(rowset.getString("hora")),rowset.getInt("total"));
			  }
		return hashmap;
	}

}
