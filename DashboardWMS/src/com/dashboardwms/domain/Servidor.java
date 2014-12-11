package com.dashboardwms.domain;

import java.io.Serializable;
import java.util.List;

public class Servidor implements Serializable {
	private String conexionesActuales;
	private List<Aplicacion> listaAplicaciones;
	public String getConexionesActuales() {
		return conexionesActuales;
	}
	public void setConexionesActuales(String conexionesActuales) {
		this.conexionesActuales = conexionesActuales;
	}
	public List<Aplicacion> getListaAplicaciones() {
		return listaAplicaciones;
	}
	public void setListaAplicaciones(List<Aplicacion> listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}
	public Servidor(String conexionesActuales,
			List<Aplicacion> listaAplicaciones) {
		super();
		this.conexionesActuales = conexionesActuales;
		this.listaAplicaciones = listaAplicaciones;
	}
	public Servidor() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
