package com.dashboardwms.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.utilities.Utilidades;

public class AplicacionMapper implements RowMapper<Aplicacion> {

	@Override
	public Aplicacion mapRow(ResultSet rs, int rowNum) throws SQLException {
		 Aplicacion aplicacion =new Aplicacion();
		 aplicacion.setNombre(rs.getString("nombre"));
		 aplicacion.setEstatus(rs.getString("estatus"));
		 aplicacion.setConexionesActuales(rs.getInt("conexiones_actuales"));
		 aplicacion.setTiempoCorriendo(rs.getDouble("tiempo_corriendo"));
		 aplicacion.setHttpSessionCount(rs.getInt("http_session_count"));
		 aplicacion.setSanJoseConnectionCount(rs.getInt("san_jose_connection_count"));
		 aplicacion.setCupertinoConnectionCount(rs.getInt("cupertino_connection_count"));
		 aplicacion.setRtmpSessionCount(rs.getInt("rtmp_session_count"));
		 aplicacion.setRtmpConnectionCount(rs.getInt("rtmp_connection_count"));
		 aplicacion.setRtpConnectionCount(rs.getInt("rtp_connection_count"));
		 aplicacion.setRtpSessionCount(rs.getInt("rtp_session_count"));
		 aplicacion.setSmoothConnectionCount(rs.getInt("smooth_connection_count"));
		 try {
			 aplicacion.setTimestamp(Utilidades.DATE_FORMAT.parse(rs.getString("timestamp")));
				System.out.println("timestamp" + aplicacion.getTimestamp());
			} catch (Exception e) {
				aplicacion.setTimestamp(new Date());
				e.printStackTrace();
			}
		 return aplicacion;
	}

	
	
	
}
