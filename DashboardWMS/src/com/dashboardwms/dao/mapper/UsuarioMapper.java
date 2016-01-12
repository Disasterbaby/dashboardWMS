package com.dashboardwms.dao.mapper;

import java.sql.Blob;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.springframework.jdbc.core.RowMapper;

import com.dashboardwms.domain.Usuario;
import com.dashboardwms.utilities.Utilidades;

public class UsuarioMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setNombre(rs.getString("nombre"));
		String password = rs.getString("password");
		byte[] decodedBytes = Base64.decodeBase64(password);
		String  decodedPassword = new String(decodedBytes);
		usuario.setPassword(decodedPassword);
		String logo = rs.getString("logo");

		if(logo!=null)
			usuario.setLogo(logo);
		try {
			usuario.setFechaVencimiento(Utilidades.DATE_FORMAT.parse(rs.getString("vencimiento")));
			System.out.println("vencimiento" + usuario.getFechaVencimiento());
		} catch (Exception e) {
			usuario.setFechaVencimiento(null);
		}
	
		return usuario;
	}

}
