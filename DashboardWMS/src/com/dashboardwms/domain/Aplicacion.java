package com.dashboardwms.domain;

import java.io.Serializable;
import java.util.List;

public class Aplicacion implements Serializable {

	private String nombre;
	private String estatus;
	private Integer conexionesActuales = 0;
	private List<Cliente> listaClientes;
	private Double tiempoCorriendo;
	
	 private Integer httpSessionCount = 0;

	    private Integer sanJoseConnectionCount = 0;

	    private Integer cupertinoConnectionCount = 0;

	    private Integer rtmpSessionCount = 0;

	    private Integer rtmpConnectionCount = 0;

	    private Integer rtpConnectionCount = 0;

	    private Integer rtpSessionCount = 0;

	    private Integer smoothConnectionCount = 0;



	    
		public List<Cliente> getListaClientes() {
			return listaClientes;
		}

		public void setListaClientes(List<Cliente> listaClientes) {
			this.listaClientes = listaClientes;
		}

		public String getNombre() {
			return nombre;
		}

		public void setNombre(String nombre) {
			this.nombre = nombre;
		}

		public String getEstatus() {
			return estatus;
		}

		public void setEstatus(String estatus) {
			this.estatus = estatus;
		}

		public Integer getConexionesActuales() {
			return conexionesActuales;
		}

		public void setConexionesActuales(Integer conexionesActuales) {
			this.conexionesActuales = conexionesActuales;
		}

		public Double getTiempoCorriendo() {
			return tiempoCorriendo;
		}

		public void setTiempoCorriendo(Double tiempoCorriendo) {
			this.tiempoCorriendo = tiempoCorriendo;
		}

		public Integer getHttpSessionCount() {
			return httpSessionCount;
		}

		public void setHttpSessionCount(Integer httpSessionCount) {
			this.httpSessionCount = httpSessionCount + this.httpSessionCount;
		}

		public Integer getSanJoseConnectionCount() {
			return sanJoseConnectionCount;
		}

		public void setSanJoseConnectionCount(Integer sanJoseConnectionCount) {
			this.sanJoseConnectionCount = sanJoseConnectionCount + this.sanJoseConnectionCount;
		}

		public Integer getCupertinoConnectionCount() {
			return cupertinoConnectionCount;
		}

		public void setCupertinoConnectionCount(Integer cupertinoConnectionCount) {
			this.cupertinoConnectionCount = cupertinoConnectionCount + this.cupertinoConnectionCount;
		}

		public Integer getRtmpSessionCount() {
			return rtmpSessionCount;
		}

		public void setRtmpSessionCount(Integer rtmpSessionCount) {
			this.rtmpSessionCount = rtmpSessionCount + this.rtmpSessionCount;
		}

		public Integer getRtmpConnectionCount() {
			return rtmpConnectionCount;
		}

		public void setRtmpConnectionCount(Integer rtmpConnectionCount) {
			this.rtmpConnectionCount = rtmpConnectionCount + this.rtmpConnectionCount ;
		}

		public Integer getRtpConnectionCount() {
			return rtpConnectionCount;
		}

		public void setRtpConnectionCount(Integer rtpConnectionCount) {
			this.rtpConnectionCount = rtpConnectionCount + this.rtpConnectionCount;
		}

		public Integer getRtpSessionCount() {
			return rtpSessionCount;
		}

		public void setRtpSessionCount(Integer rtpSessionCount) {
			this.rtpSessionCount = rtpSessionCount + this.rtpSessionCount;
		}

		public Integer getSmoothConnectionCount() {
			return smoothConnectionCount;
		}

		public void setSmoothConnectionCount(Integer smoothConnectionCount) {
			this.smoothConnectionCount = smoothConnectionCount + this.smoothConnectionCount;
		}

		public Aplicacion() {
			super();
			// TODO Auto-generated constructor stub
		}



	    
	    
}
