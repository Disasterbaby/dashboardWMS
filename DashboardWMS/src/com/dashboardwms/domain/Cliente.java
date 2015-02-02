package com.dashboardwms.domain;

import java.io.Serializable;
import java.util.Date;

import com.dashboardwms.geoip.Location;

public class Cliente implements Serializable {
	
	private String tipo;
	private String ipAddress;
	private String protocolo;
	private Double timeRunning;
	private String clientID;
	private String flashVersion;
	private String tiempoString;
    private String fechaInicio;
     private String idAplicacion;
    private Location location;
    private Date timestamp;
    
    
    
    
    
    
    
    
	public Date getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(Date timestamp) {
		this.timestamp = timestamp;
	}

	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
	}

	public String getIdAplicacion() {
		return idAplicacion;
	}

	public void setIdAplicacion(String idAplicacion) {
		this.idAplicacion = idAplicacion;
	}

	

	public String getFechaInicio() {
		return fechaInicio;
	}

	public void setFechaInicio(String fechaInicio) {
		this.fechaInicio = fechaInicio;
	}
	
	
	
	
	public String getTiempoString() {
	double tiempo = this.timeRunning.doubleValue();
	int hor,min,seg; 
		int total = (int)tiempo;
		hor=total/3600;  
        min=(total-(3600*hor))/60;  
        seg=total-((hor*3600)+(min*60));  
		tiempoString = hor + " hrs, " + min + " mins, " + seg + " segs.";
		System.out.println(tiempoString);
		return tiempoString;
	}
	public void setTiempoString(String tiempoString) {
		
	
		this.tiempoString = tiempoString;
	}
	public String getFlashVersion() {
		return flashVersion;
	}
	public void setFlashVersion(String flashVersion) {
		this.flashVersion = flashVersion;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getProtocolo() {
		return protocolo;
	}
	public void setProtocolo(String protocolo) {
		this.protocolo = protocolo;
	}
	public Double getTimeRunning() {
		
		return timeRunning;
	}
	public void setTimeRunning(Double timeRunning) {
		this.timeRunning = timeRunning;
	}
	public String getClientID() {
		return clientID;
	}
	public void setClientID(String clientID) {
		this.clientID = clientID;
	}
	
	
	

}
