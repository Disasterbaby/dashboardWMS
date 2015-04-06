package com.dashboardwms.domain;

import java.io.Serializable;

public class Usuario implements Serializable {
	private String nombre;
	private String aplicacion;
	private String aplicacionMovil;
	private String password;
	
	
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getAplicacion() {
		return aplicacion;
	}
	public void setAplicacion(String aplicacion) {
		this.aplicacion = aplicacion;
	}
	public String getAplicacionMovil() {
		return aplicacionMovil;
	}
	public void setAplicacionMovil(String aplicacionMovil) {
		this.aplicacionMovil = aplicacionMovil;
	}
	public Usuario(String nombre, String aplicacion, String aplicacionMovil) {
		super();
		this.nombre = nombre;
		this.aplicacion = aplicacion;
		this.aplicacionMovil = aplicacionMovil;
	}
	public Usuario() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	

}
