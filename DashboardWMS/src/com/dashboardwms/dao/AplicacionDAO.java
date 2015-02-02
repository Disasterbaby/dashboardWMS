package com.dashboardwms.dao;

import java.util.Date;
import java.util.List;

import com.dashboardwms.domain.Aplicacion;

public interface AplicacionDAO {

	 public String QUERY_GET_HISTORIAL_APLICACION="SELECT * FROM aplicacion WHERE nombre = ?;";
	 public String QUERY_GET_TODAS_APLICACIONES="SELECT * FROM aplicacion;";
	 public String QUERY_GET_DISTINCT_APLICACIONES = "SELECT DISTINCT(nombre) FROM aplicacion;";  
	 
	public String QUERY_SPECIFIC_DATE_TODOS = "SELECT * from aplicacion  WHERE strftime('%Y-%m-%d', timestamp) = ?;";
	public String QUERY_SPECIFIC_DATE_BY_NOMBRE = "SELECT * from aplicacion  WHERE strftime('%Y-%m-%d', timestamp) = ? and nombre = ?;";
	public String QUERY_DATE_RANGE = "SELECT * from aplicacion WHERE strftime('%Y-%m-%d', timestamp) BETWEEN '2015-01-27' AND '2015-01-29'";
	
	public List<Aplicacion> getHistorialAplicacion (String nombreAplicacion);
	public List<Aplicacion> getTodasAplicaciones();
	public List<String> listaAplicaciones();
	public List<Aplicacion> getTodasAplicacionesPorFecha(String fecha);
	public List<Aplicacion> getAplicacionPorFecha(String fecha, String nombre);
}
