package com.dashboardwms.service;

import java.util.ArrayList;
import java.util.List;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;

public class TiempoRealService {

	public TiempoRealService() {

	}

	public List<Cliente> getTodosClientes(Servidor servidor) {
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		List<Aplicacion> listaAplicaciones = servidor.getListaAplicaciones();
		if (listaAplicaciones != null) {
			for (Aplicacion aplicacion : listaAplicaciones) {
				List<Cliente> clientesAplicacion = aplicacion
						.getListaClientes();
				if (clientesAplicacion != null) {
					for (Cliente cliente : clientesAplicacion) {
						listaClientes.add(cliente);
					}
				}
			}
		}
		return listaClientes;
	}
	


}
