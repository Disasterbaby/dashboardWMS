package com.dashboardwms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.domain.Aplicacion;


@Repository

@Transactional(readOnly = true)
public class JdbcAplicacionDAO implements AplicacionDAO {
	
    private JdbcTemplate jdbcTemplate;

    @Autowired( required=true)
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public List<Aplicacion> getHistorialAplicacion(String nombreAplicacion) {
		 List<Aplicacion> historialAplicacion=
				 this.jdbcTemplate.query(QUERY_GET_HISTORIAL_APLICACION,
				 new AplicacionMapper(),nombreAplicacion);

		return historialAplicacion;
	}

	@Override
	public List<Aplicacion> getTodasAplicaciones() {
		 List<Aplicacion> historialAplicacion=
				 this.jdbcTemplate.query(QUERY_GET_TODAS_APLICACIONES,
				 new AplicacionMapper());

		return historialAplicacion;
	}

	@Override
	public List<String> listaAplicaciones() {
		List<String> listaAplicaciones = this.jdbcTemplate.query(
				QUERY_GET_DISTINCT_APLICACIONES, new RowMapper() {
      public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(1);
      }
      }
      );
	
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getTodasAplicacionesPorFecha(String fecha) {
		
		System.out.println("string " + QUERY_SPECIFIC_DATE_TODOS);
		 List<Aplicacion> listaAplicaciones=
				 this.jdbcTemplate.query(QUERY_SPECIFIC_DATE_TODOS,
				 new AplicacionMapper(),fecha);

		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getAplicacionPorFecha(String fecha, String nombre) {

		 List<Aplicacion> listaAplicaciones=
				 this.jdbcTemplate.query(QUERY_SPECIFIC_DATE_BY_NOMBRE,
				 new AplicacionMapper(),fecha, nombre);
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getAplicacionRangoFechas(String nombre, String fechaInicio, String fechaFin) {
		List<Aplicacion> listaAplicaciones=  this.jdbcTemplate.query(QUERY_DATE_RANGE,
				 new AplicacionMapper(), nombre, fechaInicio, fechaFin);
		return listaAplicaciones;
	}

	@Override
	public Integer getPicoConexiones(String nombre, String fechaInicio,
			String fechaFin) {
		Integer cantidadUsuarios = this.jdbcTemplate.queryForObject(QUERY_PICO_USUARIOS_BY_RANGE, new Object[] { nombre, fechaInicio, fechaFin }, Integer.class);
		return cantidadUsuarios;
	}
	
	@Override
	public SqlRowSet getUsuariosConectadosPorHora(String nombre,
			String fechaInicio, String fechaFin) {
		SqlRowSet rowset = this.jdbcTemplate.queryForRowSet(QUERY_DATE_RANGE_BY_HOUR, new Object[] { nombre, fechaInicio, fechaFin });
		return rowset;
	}


}
