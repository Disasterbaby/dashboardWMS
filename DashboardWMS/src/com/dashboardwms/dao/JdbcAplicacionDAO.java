package com.dashboardwms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.domain.Aplicacion;


@Repository
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
		System.out.println("fecha " + fecha);
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
	
	

}
