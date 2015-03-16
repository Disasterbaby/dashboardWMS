package com.dashboardwms.dao;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;

import javax.sql.DataSource;
import javax.sql.RowSet;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.UncategorizedSQLException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dashboardwms.dao.mapper.AplicacionMapper;
import com.dashboardwms.dao.mapper.ClienteMapper;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
 
@Repository
@Transactional(readOnly = true)
public class JdbcClienteDAO implements ClienteDAO {

    private JdbcTemplate jdbcTemplate;

    @Autowired( required=true)
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

	
	@Override
	public List<Cliente> getListaIpPorAplicacion(String nombreAplicacion) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Cliente> listaClientesIp = new ArrayList<Cliente>();
		while (!successfullyExecuted) {
			try {
		listaClientesIp =
				 this.jdbcTemplate.query(QUERY_GET_IP_POR_APLICACION,
				 new ClienteMapper(),nombreAplicacion);

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
		return listaClientesIp;
	}


	@Override
	public List<Cliente> getListaIpTodos() {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Cliente> listaClientesIp = new ArrayList<Cliente>();
		while (!successfullyExecuted) {
			try {
		listaClientesIp = this.jdbcTemplate.query(QUERY_GET_IP_TODOS, new ClienteMapper());
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
		return listaClientesIp;
	}


	@Override
	public List<Cliente> getClientesPorFecha(String fecha, String nombre) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		while (!successfullyExecuted) {
			try {
		 listaClientes=
				 this.jdbcTemplate.query(QUERY_SPECIFIC_DATE_BY_NOMBRE,
				 new ClienteMapper(),fecha, nombre);

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
		return listaClientes;
	
	}


	@Override
	public List<Cliente> getClientesRangoFechas(String nombre,
			String fechaInicio, String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		List<Cliente> listaClientes = new ArrayList<Cliente>();
		while (!successfullyExecuted) {
			try {
				listaClientes=
			
				 this.jdbcTemplate.query(QUERY_DATE_RANGE,
				 new ClienteMapper(),nombre, fechaInicio, fechaFin);
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
		return listaClientes;
	
	}


	@Override
	public Double getCantidadMinutosRangoFecha(String nombre,
			String fechaInicio, String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		Double cantidadMinutos = 0.0;
		while (!successfullyExecuted) {
			try {
		 cantidadMinutos = this.jdbcTemplate.queryForObject(QUERY_CANTIDAD_MINUTOS_RANGO_FECHA, new Object[] { nombre, fechaInicio, fechaFin },  Double.class);
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
		return cantidadMinutos;
	}

	

   @Override
	public Double getAvgMinutosRangoFecha(String nombre, String fechaInicio,
			String fechaFin) {
		boolean successfullyExecuted = false;
		int failedCount = 0;
		Double avgMinutos = 0.0;
		while (!successfullyExecuted) {
			try {
		 avgMinutos = this.jdbcTemplate.queryForObject(QUERY_AVG_MINUTOS_RANGO_FECHA,  new Object[] { nombre, fechaInicio, fechaFin }, Double.class);
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
		}return avgMinutos;
	}


@Override
public Integer getCantidadUsuariosRangoFecha(String nombre, String fechaInicio,
		String fechaFin) {
	boolean successfullyExecuted = false;
	int failedCount = 0;
	Integer cantidadUsuarios = 0;
	while (!successfullyExecuted) {
		try {
			cantidadUsuarios = this.jdbcTemplate.queryForObject(QUERY_GET_CANTIDAD_USUARIOS_RANGO_FECHA, new Object[] { nombre, fechaInicio, fechaFin }, Integer.class);
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
	return cantidadUsuarios;
}


@Override
public List<Cliente> getListaIPPorAplicacionFechas(String nombre,
		String fechaInicio, String fechaFin) {
	boolean successfullyExecuted = false;
	int failedCount = 0;
	List<Cliente> listaClientesIp = new ArrayList<Cliente>();
	while (!successfullyExecuted) {
		try {
	listaClientesIp =
			 this.jdbcTemplate.query(QUERY_GET_IP_APLICACION_RANGO_FECHAS,
					 new ClienteMapper(),nombre, fechaInicio, fechaFin);
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
			return listaClientesIp;
		}


@Override
public SqlRowSet getUsuariosConectadosPorHora(String nombre,
		String fechaInicio, String fechaFin) {
	boolean successfullyExecuted = false;
	int failedCount = 0;
	SqlRowSet rowset = null;
	while (!successfullyExecuted) {
		try {
	rowset = this.jdbcTemplate.queryForRowSet(QUERY_DATE_RANGE_BY_HOUR, new Object[] { nombre, fechaInicio, fechaFin });
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
	return rowset;
}


@Override
public SqlRowSet getClientesUsadosRangoFechas(String nombre,
		String fechaInicio, String fechaFin) {
	boolean successfullyExecuted = false;
	int failedCount = 0;
	SqlRowSet rowset = null;
	while (!successfullyExecuted) {
		try {
	rowset = this.jdbcTemplate.queryForRowSet(QUERY_TIPO_CLIENTE_RANGO_FECHA, new Object[] { nombre, fechaInicio, fechaFin });
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
	return rowset;
}


}
