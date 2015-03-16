package com.dashboardwms.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.domain.Aplicacion;

@Repository
@Transactional(readOnly = true)
public class JdbcAplicacionDAO implements AplicacionDAO {

	private JdbcTemplate jdbcTemplate;

	@Autowired(required = true)
	public void setDataSource(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Aplicacion> getHistorialAplicacion(String nombreAplicacion) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Aplicacion> historialAplicacion = new ArrayList<Aplicacion>();
		while (!successfullyExecuted) {
			try {
				historialAplicacion = this.jdbcTemplate.query(
						QUERY_GET_HISTORIAL_APLICACION, new AplicacionMapper(),
						nombreAplicacion);
				successfullyExecuted = true;
			} catch (UncategorizedSQLException e) {
				System.out.println("Reintentar");
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
		return historialAplicacion;
	}

	@Override
	public List<Aplicacion> getTodasAplicaciones() {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Aplicacion> historialAplicacion = new ArrayList<Aplicacion>();
		while (!successfullyExecuted) {
			try {
				historialAplicacion = this.jdbcTemplate.query(
						QUERY_GET_TODAS_APLICACIONES, new AplicacionMapper());
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
		return historialAplicacion;
	}

	@Override
	public List<String> listaAplicaciones() {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<String> listaAplicaciones = new ArrayList<String>();	
		while (!successfullyExecuted){
		 try {
			 listaAplicaciones = this.jdbcTemplate.query(QUERY_GET_DISTINCT_APLICACIONES, new RowMapper() {
      public Object mapRow(ResultSet resultSet, int i) throws SQLException {
        return resultSet.getString(1);
      } });
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
	
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getTodasAplicacionesPorFecha(String fecha) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();	
		while (!successfullyExecuted){
		 try {
		 listaAplicaciones = this.jdbcTemplate.query(
				QUERY_SPECIFIC_DATE_TODOS, new AplicacionMapper(), fecha);
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
	
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getAplicacionPorFecha(String fecha, String nombre) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();	
		while (!successfullyExecuted){
		 try { listaAplicaciones = this.jdbcTemplate.query(
				QUERY_SPECIFIC_DATE_BY_NOMBRE, new AplicacionMapper(), fecha,
				nombre);
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
	
		return listaAplicaciones;
	}

	@Override
	public List<Aplicacion> getAplicacionRangoFechas(String nombre,
			String fechaInicio, String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();	
		while (!successfullyExecuted){
		 try { listaAplicaciones = this.jdbcTemplate.query(
				QUERY_DATE_RANGE, new AplicacionMapper(), nombre, fechaInicio,
				fechaFin);
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
		return listaAplicaciones;
	}

	@Override
	public Integer getPicoConexiones(String nombre, String fechaInicio,
			String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		Integer cantidadUsuarios = 0;	
		while (!successfullyExecuted){
			try {
		
		 cantidadUsuarios = this.jdbcTemplate.queryForObject(
				QUERY_PICO_USUARIOS_BY_RANGE, new Object[] { nombre,
						fechaInicio, fechaFin }, Integer.class);
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
		return cantidadUsuarios;
	}

	@Override
	public SqlRowSet getUsuariosConectadosPorHora(String nombre,
			String fechaInicio, String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		SqlRowSet rowset = null;	
		while (!successfullyExecuted){
			try {
		
		 rowset = this.jdbcTemplate.queryForRowSet(
				QUERY_DATE_RANGE_BY_HOUR, new Object[] { nombre, fechaInicio,
						fechaFin });
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
		return rowset;
	}

}
