package com.dashboardwms.service;

import java.util.Date;
import java.util.List;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;

public interface UsuarioService {


	public List<String> getListaEmisorasPorUsuario(String usuario);
	
	public List<String> verificarCredenciales(String usuario, String password);
	
	public void cambiarPassword(String usuario, String password);
	
	public void crearUsuario(String nombre, String password,String aplicacionMovil, Date fechaVencimiento, String logo, List<String> listaEmisoras) throws UsuarioDuplicadoException;
	
	public List<Usuario> getListaUsuarios();
	
	public List<String> getListaNombreUsuarioExistentes();
	
	public List<String> getListaEmisorasUsadas();
	
	public String getAppMovil(String emisora);
	
	public void modificarUsuario(Usuario usuario) throws UsuarioDuplicadoException;
	
	public Boolean verificarVencimiento(String usuario);
	
	public String getLogo(String usuario);
	
	public void modificarLogo(String logo, String usuario);
	
}
