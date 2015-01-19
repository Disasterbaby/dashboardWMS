package com.dashboardwms.domain;

import java.io.Serializable;
import java.util.List;

public class Servidor implements Serializable {

	private Integer conexionesActuales = 0;
	private List<Aplicacion> listaAplicaciones;
	
	
	public Integer getConexionesActuales() {
		return conexionesActuales;
	}
	public void setConexionesActualesInt(Integer conexionesActualesInt) {
		this.conexionesActuales = conexionesActualesInt + this.conexionesActuales;
	}

	public List<Aplicacion> getListaAplicaciones() {
		return listaAplicaciones;
	}
	public void setListaAplicaciones(List<Aplicacion> listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}
	public Servidor(Integer conexionesActuales,
			List<Aplicacion> listaAplicaciones) {
		super();
		this.conexionesActuales = conexionesActuales;
		this.listaAplicaciones = listaAplicaciones;
	}
	public Servidor() {
		
		super();
		this.conexionesActuales = 0;
		// TODO Auto-generated constructor stub
	}
	
	
	
	

}
