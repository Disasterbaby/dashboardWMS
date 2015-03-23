package com.dashboardwms.dao;



import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;



@Repository
public class JdbcUsuarioDAO implements UsuarioDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired(required = true)
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	

	@Transactional(readOnly = true)
	public String getNombreEmisora(String usuario, String password) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		String emisora = null;
		while (!successfullyExecuted) {
			try {
				 emisora = this.jdbcTemplate.queryForObject(
							QUERY_GET_EMISORA, new Object[] {usuario,
									password}, String.class);
				successfullyExecuted = true;
			} catch (UncategorizedSQLException e) {
				System.out.println("reintentar");
				if (failedCount < 10) {
					failedCount++;
					try {
						java.lang.Thread.sleep(2 * 1000L); // Wait for 2 seconds
					} catch (java.lang.Exception exception) {
						System.out.println("Exception " + exception);
					}
				}
			}
			catch(EmptyResultDataAccessException e) {
				return null;
			}	
		}
		return emisora;
	}

	@Override
	public void insertarUsuario(String usuario, String password,
			String aplicacion) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		while (!successfullyExecuted) {
			try {
				 this.jdbcTemplate.update(QUERY_INSERT_USUARIO, usuario, password, aplicacion);
				successfullyExecuted = true;
			} catch (UncategorizedSQLException e) {
				System.out.println("reintentar");
				if (failedCount < 10) {
					failedCount++;
					try {
						java.lang.Thread.sleep(2 * 1000L); // Wait for 2 seconds
					} catch (java.lang.Exception exception) {
						System.out.println("Exception " + exception);
					}
				}
			}
		}	
	}

	@Override
	public void cambiarPassword(String password, String usuario) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		while (!successfullyExecuted) {
			try {
				 this.jdbcTemplate.update(QUERY_CHANGE_PASSWORD, password, usuario);
				successfullyExecuted = true;
			} catch (UncategorizedSQLException e) {
				System.out.println("reintentar");
				if (failedCount < 10) {
					failedCount++;
					try {
						java.lang.Thread.sleep(2 * 1000L); // Wait for 2 seconds
					} catch (java.lang.Exception exception) {
						System.out.println("Exception " + exception);
					}
				}
			}
		}	
	}

}
