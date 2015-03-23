package com.dashboardwms.service;

public interface UsuarioService {

	public String verificarCredenciales(String usuario, String password);
	
	public void cambiarPassword(String usuario, String password);
	
	public void crearUsuario(String nombre, String password, String aplicacion);
}
