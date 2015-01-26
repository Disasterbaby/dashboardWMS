package com.dashboard.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dashboardwms.domain.Aplicacion;

@Repository
public class JdbcAplicacionDAO implements AplicacionDAO {
	
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void init(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	@Override
	public List<Aplicacion> getHistorialAplicacion(String nombreAplicacion) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Aplicacion> getTodasAplicaciones() {
		// TODO Auto-generated method stub
		return null;
	}
	
	

}
