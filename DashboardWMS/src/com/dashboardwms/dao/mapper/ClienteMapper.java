package com.dashboardwms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.jdbc.core.RowMapper;

import com.dashboardwms.domain.Cliente;
import com.dashboardwms.utilities.Utilidades;

public class ClienteMapper implements RowMapper<Cliente> {

	@Override
	public Cliente mapRow(ResultSet rs, int rowNum) throws SQLException {
		Cliente cliente =new Cliente();
		cliente.setTipo(rs.getString("tipo"));
		cliente.setIpAddress(rs.getString("ip_address"));
		cliente.setProtocolo(rs.getString("protocolo"));
			cliente.setClientID(rs.getString("client_id"));

			cliente.setFlashVersion(rs.getString("flash_version"));
			
			cliente.setTimeRunning(rs.getDouble("time_running"));
			
			cliente.setIdAplicacion(rs.getString("id_aplicacion"));
		cliente.setFechaInicio(rs.getString("fecha_inicio"));
	


		try {
			cliente.setTimestamp(Utilidades.DATE_FORMAT.parse(rs.getString("timestamp")));
			System.out.println("timestamp" + cliente.getTimestamp());
		} catch (Exception e) {
			cliente.setTimestamp(Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime());
			e.printStackTrace();
		}
		

		 
		 
		 return cliente;
	}

}
