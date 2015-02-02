package com.dashboardwms.dao;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.dashboardwms.dao.mapper.ClienteMapper;
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

}
