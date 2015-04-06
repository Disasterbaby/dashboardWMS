package com.dashboardwms.service;

import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.UsuarioDAO;
import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;

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
	public void crearUsuario(String nombre, String password, String aplicacion, String aplicacionMovil) throws UsuarioDuplicadoException {

		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);
		if(getListaNombreUsuarioExistentes().contains(nombre))
		 throw new UsuarioDuplicadoException("El nombre de usuario ya existe");
		else if (getListaEmisorasUsadas().contains(aplicacion))
			throw new UsuarioDuplicadoException("La emisora ya se encuentra asignada a otro usuario");
		else
			usuarioDao.insertarUsuario(nombre, encodedPassword, aplicacion, aplicacionMovil);
	}

	@Override
	public List<Usuario> getListaUsuarios() {
		
		return usuarioDao.getListaUsuarios();
	}




	@Override
	public String getAppMovil(String emisora) {
		// TODO Auto-generated method stub
		return usuarioDao.getAppMovilUsuario(emisora);
	}




	@Override
	public List<String> getListaNombreUsuarioExistentes() {
		// TODO Auto-generated method stub
		return usuarioDao.getListaNombresUsuarios();
	}




	@Override
	public List<String> getListaEmisorasUsadas() {
		
		return usuarioDao.getListaEmisorasAsignadas();
	}




	@Override
	public void modificarUsuario(Usuario usuario) {
		byte[] encodedBytes = Base64.encodeBase64(usuario.getPassword().getBytes());
		String encodedPassword = new String(encodedBytes);
		usuarioDao.modificarUsuario(usuario.getAplicacion(), encodedPassword, usuario.getAplicacionMovil(), usuario.getNombre());
		
	}

}
