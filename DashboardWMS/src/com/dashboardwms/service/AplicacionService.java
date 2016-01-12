package com.dashboardwms.service;

import java.text.ParseException;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.InvalidResultSetAccessException;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;

public interface AplicacionService {

	public List<Cliente> getTodosClientes(Servidor servidor);
	
	public List<Aplicacion> getListaAplicaciones();
	
	public List<String> getListaAplicacionesDistinct();
	
	public List<Aplicacion> getTodasAplicacionesPorFecha(Date fecha);
	
	public List<Aplicacion> getAplicacionPorFecha(Date fecha, String nombre);
	
	public List<Aplicacion> getAplicacionRangoFechas(String nombre, Date fechaInicio, Date fechaFin);

	public Integer getPicoUsuariosRangoFecha(String nombre, Date fechaInicio, Date fechaFin);
	
	public LinkedHashMap<Date, Integer> getUsuariosConectadosPorHora(String nombre, Date fechaInicio, Date fechaFin) throws InvalidResultSetAccessException, ParseException;

	public LinkedHashMap<Date, Integer> getUsuariosHoraEspecifica(String nombre, Date fechaInicio, Date fechaFin) throws InvalidResultSetAccessException, ParseException;

}
