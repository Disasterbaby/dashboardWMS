package com.dashboardwms.service;

import java.util.List;

import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;

public interface DashboardService {

	public List<Cliente> getTodosClientes(Servidor servidor);
}
