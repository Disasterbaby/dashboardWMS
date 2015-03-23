package com.dashboardwms.dao;

public interface UsuarioDAO {
	
	public String QUERY_GET_EMISORA = "SELECT APLICACION FROM USUARIO WHERE UPPER(NOMBRE) = UPPER(?) AND PASSWORD = ?;";
	public String QUERY_INSERT_USUARIO = "INSERT INTO USUARIO VALUES (?,?,?);";
	public String QUERY_CHANGE_PASSWORD = "UPDATE USUARIO SET PASSWORD = ? WHERE UPPER(NOMBRE) = UPPER(?)";
	
	public String getNombreEmisora(String usuario, String password);
	public void insertarUsuario(String usuario, String password, String aplicacion);
	public void cambiarPassword(String password, String usuario);

}
