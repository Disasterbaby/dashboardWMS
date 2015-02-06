package com.dashboardwms.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.dao.mapper.ClienteMapper;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
 
@Repository
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

}
