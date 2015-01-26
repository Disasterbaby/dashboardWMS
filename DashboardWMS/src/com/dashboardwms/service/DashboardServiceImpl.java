package com.dashboardwms.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;

@Service
public class DashboardServiceImpl implements DashboardService {


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
