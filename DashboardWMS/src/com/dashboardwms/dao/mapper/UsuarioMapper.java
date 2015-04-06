package com.dashboardwms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.commons.codec.binary.Base64;
import org.springframework.jdbc.core.RowMapper;

import com.dashboardwms.domain.Usuario;

public class UsuarioMapper implements RowMapper<Usuario> {

	@Override
	public Usuario mapRow(ResultSet rs, int rowNum) throws SQLException {
		Usuario usuario = new Usuario();
		usuario.setAplicacion(rs.getString("aplicacion"));
		usuario.setNombre(rs.getString("nombre"));
		usuario.setAplicacionMovil(rs.getString("aplicacion_movil"));
		String password = rs.getString("password");
		byte[] decodedBytes = Base64.decodeBase64(password);
		String  decodedPassword = new String(decodedBytes);
		usuario.setPassword(decodedPassword);
		return usuario;
	}

}
