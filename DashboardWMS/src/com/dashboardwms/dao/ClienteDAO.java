package com.dashboardwms.dao;

import java.util.List;

import com.dashboardwms.domain.Cliente;

public interface ClienteDAO {

	
	

	
	
	 public String QUERY_GET_IP_TODOS="SELECT * FROM cliente;";
	 public String QUERY_GET_IP_POR_APLICACION="SELECT * FROM cliente WHERE ID_APLICACION = ?;";

	public List<Cliente> getListaIpPorAplicacion(String nombreAplicacion);
	
	public List<Cliente> getListaIpTodos();
	
	
}
