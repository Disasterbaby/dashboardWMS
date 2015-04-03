package com.dashboardwms.dao;

import java.util.List;

import com.dashboardwms.domain.Usuario;

public interface UsuarioDAO {
	
	public String QUERY_GET_EMISORA = "SELECT APLICACION FROM USUARIO WHERE UPPER(NOMBRE) = UPPER(?) AND PASSWORD = ?;";
	public String QUERY_INSERT_USUARIO = "INSERT INTO USUARIO VALUES (?,?,?,?);";
	public String QUERY_CHANGE_PASSWORD = "UPDATE USUARIO SET PASSWORD = ? WHERE UPPER(NOMBRE) = UPPER(?)";
	public String QUERY_GET_LISTA_USUARIOS =  "SELECT NOMBRE, APLICACION, APLICACION_MOVIL FROM USUARIO WHERE NOMBRE != 'ADMIN'";
	
	public String getNombreEmisora(String usuario, String password);
	public void insertarUsuario(String usuario, String password, String aplicacion, String aplicacionMovil);
	public void cambiarPassword(String password, String usuario);
	public List<Usuario> getListaUsuarios();

}
