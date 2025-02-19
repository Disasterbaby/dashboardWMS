package com.dashboardwms.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;

public interface ClienteDAO {

	

	public String QUERY_SPECIFIC_DATE_BY_NOMBRE = "SELECT * from cliente  WHERE strftime('%Y-%m-%d', timestamp) = ? and id_aplicacion = ?;";
	public String QUERY_DATE_RANGE = "SELECT * from cliente WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?";
	

	
	
	 public String QUERY_GET_IP_TODOS="SELECT * FROM cliente;";
	 public String QUERY_GET_IP_POR_APLICACION="SELECT * FROM cliente WHERE ID_APLICACION = ?;";
	 
	 public String QUERY_GET_IP_APLICACION_RANGO_FECHAS = "SELECT * FROM cliente WHERE ID_APLICACION = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 	 
	 public String QUERY_GET_CANTIDAD_USUARIOS_RANGO_FECHA = "select ifnull(count(*),0) from cliente where id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
	 public String QUERY_CANTIDAD_MINUTOS_RANGO_FECHA = "SELECT ifnull(SUM(TIME_RUNNING),0) FROM CLIENTE WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
	 
	 public String QUERY_AVG_MINUTOS_RANGO_FECHA = "SELECT ifnull(AVG(TIME_RUNNING),0) FROM CLIENTE WHERE id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ?;";
	 
		public String QUERY_DATE_RANGE_BY_HOUR = "select strftime('%Y-%m-%d %H:00:00', timestamp) hora, count() total from cliente where id_aplicacion = ? AND strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ? group by strftime('%Y-%m-%d %H:00:00', timestamp);";

		
	public String QUERY_TIPO_CLIENTE_RANGO_FECHA = "select protocolo, count(*) total from cliente where id_aplicacion = ? and  strftime('%Y-%m-%d', timestamp) BETWEEN ? AND ? group by protocolo order by total desc;";
	
	public SqlRowSet getClientesUsadosRangoFechas(String nombre, String fechaInicio, String fechaFin);
	
 public SqlRowSet getUsuariosConectadosPorHora(String nombre, String fechaInicio, String fechaFin);
	public List<Cliente> getListaIpPorAplicacion(String nombreAplicacion);
	
	public List<Cliente> getListaIPPorAplicacionFechas(String nombre, String fechaInicio, String fechaFin);
	
	public List<Cliente> getListaIpTodos();
	
	public List<Cliente> getClientesPorFecha(String fecha, String nombre);
	public List<Cliente> getClientesRangoFechas(String nombre, String fechaInicio, String fechaFin);
	
	public Integer getCantidadUsuariosRangoFecha(String nombre, String fechaInicio, String fechaFin);
	public Double getCantidadMinutosRangoFecha(String nombre, String fechaInicio, String fechaFin);
	
	public Double getAvgMinutosRangoFecha(String nombre, String fechaInicio, String fechaFin);
	
	
	
	
}
