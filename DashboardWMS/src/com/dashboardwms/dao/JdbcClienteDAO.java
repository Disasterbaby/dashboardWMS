package com.dashboardwms.dao;

import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.dao.mapper.ClienteMapper;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
 
@Repository
@Transactional(readOnly = true)
public class JdbcClienteDAO implements ClienteDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired( required=true)
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	
	@Override
	public List<Cliente> getListaIpPorAplicacion(String nombreAplicacion) {
		 List<Cliente> listaClientesIp =
				 this.jdbcTemplate.query(QUERY_GET_IP_POR_APLICACION,
				 new ClienteMapper(),nombreAplicacion);

		return listaClientesIp;
	}


	@Override
	public List<Cliente> getListaIpTodos() {
		 List<Cliente> listaClientesIp = this.jdbcTemplate.query(QUERY_GET_IP_TODOS, new ClienteMapper());
		 
		return listaClientesIp;
	}


	@Override
	public List<Cliente> getClientesPorFecha(String fecha, String nombre) {
		 List<Cliente> listaClientes=
				 this.jdbcTemplate.query(QUERY_SPECIFIC_DATE_BY_NOMBRE,
				 new ClienteMapper(),fecha, nombre);
		return listaClientes;
	
	}


	@Override
	public List<Cliente> getClientesRangoFechas(String nombre,
			String fechaInicio, String fechaFin) {
		 List<Cliente> listaClientes=
				 this.jdbcTemplate.query(QUERY_DATE_RANGE,
				 new ClienteMapper(),nombre, fechaInicio, fechaFin);
		return listaClientes;
	
	}


	@Override
	public Double getCantidadMinutosRangoFecha(String nombre,
			String fechaInicio, String fechaFin) {
		Double cantidadMinutos = this.jdbcTemplate.queryForObject(QUERY_CANTIDAD_MINUTOS_RANGO_FECHA, new Object[] { nombre, fechaInicio, fechaFin },  Double.class);
		return cantidadMinutos;
	}

	

   @Override
	public Double getAvgMinutosRangoFecha(String nombre, String fechaInicio,
			String fechaFin) {
		Double avgMinutos = this.jdbcTemplate.queryForObject(QUERY_AVG_MINUTOS_RANGO_FECHA,  new Object[] { nombre, fechaInicio, fechaFin }, Double.class);
		return avgMinutos;
	}


@Override
public Integer getCantidadUsuariosRangoFecha(String nombre, String fechaInicio,
		String fechaFin) {
	Integer cantidadUsuarios = this.jdbcTemplate.queryForObject(QUERY_GET_CANTIDAD_USUARIOS_RANGO_FECHA, new Object[] { nombre, fechaInicio, fechaFin }, Integer.class);
	return cantidadUsuarios;
}


@Override
public List<Cliente> getListaIPPorAplicacionFechas(String nombre,
		String fechaInicio, String fechaFin) {
	List<Cliente> listaClientesIp =
			 this.jdbcTemplate.query(QUERY_GET_IP_APLICACION_RANGO_FECHAS,
					 new ClienteMapper(),nombre, fechaInicio, fechaFin);

			return listaClientesIp;
		}


@Override
public SqlRowSet getUsuariosConectadosPorHora(String nombre,
		String fechaInicio, String fechaFin) {
	SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(QUERY_DATE_RANGE_BY_HOUR, new Object[] { nombre, fechaInicio, fechaFin });
	return rowset;
}


}
