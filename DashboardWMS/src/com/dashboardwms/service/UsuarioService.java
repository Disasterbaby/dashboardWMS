package com.dashboardwms.service;

import java.util.List;

import com.dashboardwms.domain.Usuario;

public interface UsuarioService {

	public String verificarCredenciales(String usuario, String password);
	
	public void cambiarPassword(String usuario, String password);
	
	public void crearUsuario(String nombre, String password, String aplicacion, String aplicacionMovil);
	
	public List<Usuario> getListaUsuarios();
}
