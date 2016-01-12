package com.dashboardwms.service;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dashboardwms.dao.UsuarioDAO;
import com.dashboardwms.domain.Usuario;
import com.dashboardwms.exceptions.UsuarioDuplicadoException;
import com.dashboardwms.utilities.Utilidades;

@Service
public class UsuarioServiceImpl implements UsuarioService {

	@Autowired
	UsuarioDAO usuarioDao;

	@Override
	public List<String> verificarCredenciales(String usuario, String password) {
		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);
		List<String> emisora =  usuarioDao.getListaEmisorasUsuario(usuario, encodedPassword);
		return emisora;

	}

	@Override
	public void cambiarPassword(String usuario, String password) {

		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);

		usuarioDao.cambiarPassword(encodedPassword, usuario);
	}

	@Override
	public void crearUsuario(String nombre, String password, String aplicacionMovil, Date fechaVencimiento, String logo, List<String> listaEmisoras)
			throws UsuarioDuplicadoException {

		String vencimiento = Utilidades.DATE_FORMAT.format(fechaVencimiento);
		byte[] encodedBytes = Base64.encodeBase64(password.getBytes());
		String encodedPassword = new String(encodedBytes);
		
		for (String emisora : listaEmisoras) {
			if (getListaEmisorasUsadas().contains(emisora))
				throw new UsuarioDuplicadoException(
						"La emisora " + emisora + " ya se encuentra asignada a otro usuario");
			}
			
		if (getListaNombreUsuarioExistentes().contains(nombre))
			throw new UsuarioDuplicadoException(
					"El nombre de usuario ya existe");
		
		else{
									
			usuarioDao.insertarUsuario(nombre, encodedPassword,vencimiento, logo);
			
			for (String emisora : listaEmisoras) {
				usuarioDao.insertarEmisora(nombre, emisora, aplicacionMovil);
			}
			
			if(logo !=null)
				usuarioDao.insertarLogo(logo, nombre);
		}
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
	public void modificarUsuario(Usuario usuario) throws UsuarioDuplicadoException {
		byte[] encodedBytes = Base64.encodeBase64(usuario.getPassword()
				.getBytes());
		String encodedPassword = new String(encodedBytes);
		String vencimiento = Utilidades.DATE_FORMAT.format(usuario
				.getFechaVencimiento());

		
		
		usuarioDao.modificarUsuario(encodedPassword,usuario.getNombre(), vencimiento);
		
		usuarioDao.borrarEmisoras(usuario.getNombre());
		for (String emisora : usuario.getListaEmisoras()) {
			if (getListaEmisorasUsadas().contains(emisora))
				throw new UsuarioDuplicadoException("La emisora " + emisora + " ya se encuentra asignada a otro usuario");
			else
			usuarioDao.insertarEmisora(usuario.getNombre(), emisora, usuario.getAplicacionMovil());
		}
		
		if(usuario.getLogo()!= null)
			usuarioDao.insertarLogo(usuario.getLogo(), usuario.getNombre());
		
	}

	@Override
	public Boolean verificarVencimiento(String usuario) {
		String fechaVencimientoString = usuarioDao.getFechaVencimiento(usuario);
		if(usuario.equalsIgnoreCase("ADMIN"))
			return true;
		if (fechaVencimientoString != null) {

			Date today = Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime();
			try {
				Date fechaVencimiento = Utilidades.DATE_FORMAT
						.parse(fechaVencimientoString);
				if (fechaVencimiento.before(today))
					return false;
			} catch (ParseException e) {
				return false;
			}
		} else
			return false;
return true;
	}

	@Override
	public String getLogo(String usuario) {
		
		return usuarioDao.getLogo(usuario);
	}

	@Override
	public List<String> getListaEmisorasPorUsuario(String usuario) {
		// TODO Auto-generated method stub
		return usuarioDao.getListaEmisorasPorUsuario(usuario);
	}

	@Override
	public void modificarLogo(String logo, String usuario) {

		if(logo!= null)
			usuarioDao.insertarLogo(logo, usuario);
	}

}
