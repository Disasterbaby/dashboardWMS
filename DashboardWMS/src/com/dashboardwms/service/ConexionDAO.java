package com.dashboardwms.service;

import java.sql.SQLException;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;



@Repository
@Transactional
public class ConexionDAO {
	
    private JdbcTemplate jdbcTemplate;

    @Autowired( required=true)
    public void setDataSource(DataSource dataSource) {
    this.jdbcTemplate = new JdbcTemplate(dataSource);
    }
	    
    
    public void insertUpdateCliente(Cliente cliente, String timestamp){
    	
    	this.jdbcTemplate.update("INSERT OR REPLACE INTO cliente values (?,?,?,?,?,?,?,?,?);", cliente.getTipo(),
    			cliente.getIpAddress(), cliente.getProtocolo(), cliente.getClientID(), cliente.getFlashVersion(),
    			cliente.getTimeRunning(),cliente.getIdAplicacion(),cliente.getFechaInicio(), timestamp);

    }
       

    public void insertUpdateAplicacion(Aplicacion aplicacion, String timestamp) throws SQLException, ClassNotFoundException{
    	this.jdbcTemplate.update("INSERT INTO APLICACION values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);", 
    			aplicacion.getNombre(), aplicacion.getEstatus(), aplicacion.getConexionesActuales()
    			, aplicacion.getTiempoCorriendo(), aplicacion.getHttpSessionCount(), aplicacion.getSanJoseConnectionCount()
    			, aplicacion.getCupertinoConnectionCount(), aplicacion.getRtmpSessionCount()
    			, aplicacion.getRtmpConnectionCount(), aplicacion.getRtpConnectionCount(), aplicacion.getRtpSessionCount(),
    			 aplicacion.getSmoothConnectionCount(), timestamp);
    }

    public void deleteOldRowsCliente(){
    	this.jdbcTemplate.update("DELETE from cliente WHERE strftime('%Y-%m-%d',timestamp) <= date('now','-30 days')");
    }

    public void deleteOldRowsAplicacion(){
    	this.jdbcTemplate.update("DELETE from aplicacion WHERE strftime('%Y-%m-%d',timestamp) <= date('now','-30 days')");
    }
    
}

