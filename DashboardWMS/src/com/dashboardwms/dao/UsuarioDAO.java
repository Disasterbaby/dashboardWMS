package com.dashboardwms.dao;

import java.util.List;

import com.dashboardwms.domain.Usuario;

public interface UsuarioDAO {
	

	public String QUERY_GET_LISTA_EMISORAS_POR_USUARIO = "SELECT USUARIO_APLICACION.NOMBRE_APLICACION FROM USUARIO_APLICACION, USUARIO WHERE UPPER(USUARIO.NOMBRE) "
			+ "= UPPER(USUARIO_APLICACION.NOMBRE_USUARIO) "
			+ "AND UPPER(USUARIO.NOMBRE) = UPPER(?);";
	
	public String QUERY_GET_LISTA_EMISORAS = "SELECT USUARIO_APLICACION.NOMBRE_APLICACION FROM USUARIO_APLICACION, USUARIO WHERE UPPER(USUARIO.NOMBRE) "
			+ "= UPPER(USUARIO_APLICACION.NOMBRE_USUARIO) "
			+ "AND UPPER(USUARIO.NOMBRE) = UPPER(?) AND  USUARIO.PASSWORD = ?;";
	public String QUERY_GET_APP_MOVIL = "SELECT NOMBRE_APLICACION_MOVIL FROM USUARIO_APLICACION WHERE UPPER(NOMBRE_APLICACION) = UPPER(?);";
	public String QUERY_GET_LISTA_NOMBRES_USUARIO = "SELECT NOMBRE FROM USUARIO;";
	public String QUERY_GET_LISTA_EMISORAS_ASIGNADAS = "select distinct nombre_aplicacion from usuario_APLICACION;";
	public String QUERY_CHANGE_PASSWORD = "UPDATE USUARIO SET PASSWORD = ? WHERE UPPER(NOMBRE) = UPPER(?);";
	public String QUERY_GET_FECHA_VENCIMIENTO = "SELECT VENCIMIENTO FROM USUARIO WHERE UPPER(NOMBRE) = UPPER(?);";
	public String QUERY_INSERTAR_LOGO = "UPDATE USUARIO SET LOGO = ? WHERE UPPER(NOMBRE) = UPPER(?);";
	public String QUERY_GET_LOGO = "SELECT LOGO FROM USUARIO WHERE UPPER(NOMBRE) = UPPER(?);";
	
	
	public String QUERY_INSERT_USUARIO = "INSERT INTO USUARIO VALUES (?,?,?,?);";
	public String QUERY_GET_LISTA_USUARIOS =  "SELECT NOMBRE, PASSWORD, VENCIMIENTO, LOGO FROM USUARIO WHERE UPPER(NOMBRE)!= 'ADMIN';";
	public String QUERY_UPDATE_USUARIO = "UPDATE USUARIO SET PASSWORD = ?, VENCIMIENTO = ? WHERE UPPER(NOMBRE) = UPPER(?);";
	
	public String QUERY_INSERT_EMISORA = "INSERT INTO USUARIO_APLICACION VALUES (?,?,?);";
	public String QUERY_REMOVE_EMISORAS = "DELETE FROM USUARIO_APLICACION WHERE UPPER(NOMBRE_USUARIO) = UPPER(?)";
	
	public String QUERY_GET_APP_MOVIL_BY_USER = "SELECT NOMBRE_APLICACION_MOVIL FROM USUARIO_APLICACION WHERE UPPER(NOMBRE_USUARIO) = UPPER(?);";
	public List<String> getListaEmisorasUsuario(String usuario, String password);

	public List<String> getListaEmisorasPorUsuario(String usuario);
	public void insertarUsuario(String usuario, String password, String vencimiento, String logo);
	public void cambiarPassword(String password, String usuario);
	public List<Usuario> getListaUsuarios();
	public String getAppMovilUsuario(String emisora);
	public List<String> getListaNombresUsuarios();
	public List<String> getListaEmisorasAsignadas();
	public void modificarUsuario(String password, String usuario, String vencimiento);
	public String getFechaVencimiento(String emisora);
	public void insertarLogo(String logo, String usuario);
	public String getLogo(String usuario);
	
	public void insertarEmisora(String usuario, String emisora, String aplicacionMovil);
	public void borrarEmisoras(String usuario);
	
	public String getAppMovilPorNombreUsuario(String nombreUsuario);
}

