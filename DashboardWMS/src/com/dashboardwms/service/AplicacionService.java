package com.dashboardwms.service;

import java.util.Date;
import java.util.List;

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
}
