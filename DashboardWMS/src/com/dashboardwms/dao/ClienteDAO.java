package com.dashboardwms.dao;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;

public interface ClienteDAO {

	

	public String QUERY_SPECIFIC_DATE_BY_NOMBRE = "SELECT * from cliente  WHERE strftime('%Y-%m-%d', timestamp) = ? and id_aplicacion = ?;";
	public String QUERY_DATE_RANGE = "SELECT * from cliente WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?";
	

	
	
	 public String QUERY_GET_IP_TODOS="SELECT * FROM cliente;";
	 public String QUERY_GET_IP_POR_APLICACION="SELECT * FROM cliente WHERE ID_APLICACION = ?;";
	 
	 public String QUERY_GET_CANTIDAD_USUARIOS_RANGO_FECHA = "select count(*) from cliente where id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
	 public String QUERY_CANTIDAD_MINUTOS_RANGO_FECHA = "SELECT SUM(TIME_RUNNING) FROM CLIENTE WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
	 
	 public String QUERY_AVG_MINUTOS_RANGO_FECHA = "SELECT AVG(TIME_RUNNING) FROM CLIENTE WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
	 

	public List<Cliente> getListaIpPorAplicacion(String nombreAplicacion);
	
	public List<Cliente> getListaIpTodos();
	
	public List<Cliente> getClientesPorFecha(String fecha, String nombre);
	public List<Cliente> getClientesRangoFechas(String nombre, String fechaInicio, String fechaFin);
	
	public Double getCantidadMinutosRangoFecha(String nombre, String fechaInicio, String fechaFin);
	
	public Double getAvgMinutosRangoFecha(String nombre, String fechaInicio, String fechaFin);
	
	
	
}
