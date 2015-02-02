package com.dashboard.dao;

import java.util.List;

import com.dashboardwms.domain.Aplicacion;

public interface AplicacionDAO {

	public List<Aplicacion> getHistorialAplicacion (String nombreAplicacion);
	public List<Aplicacion> getTodasAplicaciones();
}
