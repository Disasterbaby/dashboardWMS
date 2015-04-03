package com.dashboardwms.service;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.UsuarioDAO;
import com.dashboardwms.domain.Usuario;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDAO usuarioDao;
	
	@Override
	public String verificarCredenciales(String usuario, String password) {
		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);
		String emisora = usuarioDao.getNombreEmisora(usuario, encodedPassword);
		return emisora;
		
		
		
	}

	@Override
	public void cambiarPassword(String usuario, String password) {

		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);

		usuarioDao.cambiarPassword(encodedPassword, usuario);
	}

	@Override
	public void crearUsuario(String nombre, String password, String aplicacion, String aplicacionMovil) {
		usuarioDao.insertarUsuario(nombre, password, aplicacion, aplicacionMovil);
		
	}

	@Override
	public List<Usuario> getListaUsuarios() {
		
		return usuarioDao.getListaUsuarios();
	}

}
