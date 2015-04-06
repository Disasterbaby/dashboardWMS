package com.dashboardwms.service;

import java.util.List;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;

public interface UsuarioService {

	public String verificarCredenciales(String usuario, String password);
	
	public void cambiarPassword(String usuario, String password);
	
	public void crearUsuario(String nombre, String password, String aplicacion, String aplicacionMovil) throws UsuarioDuplicadoException;
	
	public List<Usuario> getListaUsuarios();
	
	public List<String> getListaNombreUsuarioExistentes();
	
	public List<String> getListaEmisorasUsadas();
	
	public String getAppMovil(String emisora);
	
	public void modificarUsuario(Usuario usuario);
}
