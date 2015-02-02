/**
 * Location.java
 *
 * Copyright (C) 2004 MaxMind LLC.  All Rights Reserved.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU General Lesser Public
 * License as published by the Free Software Foundation; either
 * version 2 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA  02111-1307  USA
 */

package com.dashboardwms.geoip;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Location {
	public String countryCode;
	public String countryName;
	public String region;
	public String city;
	private Integer cantidadUsuarios =  0;
	private Double minutosEscuchados = Double.valueOf(0);
	private String minutosString;
	private BigDecimal minutosConvertidos;
	
	public BigDecimal getminutosConvertidos(){
		Double tiempo = this.minutosEscuchados.doubleValue();
		
		minutosConvertidos = BigDecimal.valueOf(tiempo).divide(BigDecimal.valueOf(60), 2, RoundingMode.CEILING);
		
		return minutosConvertidos;
	}
	
	public String getTiempoString() {
	double tiempo = this.minutosEscuchados.doubleValue();
	int hor,min,seg; 
	
		int total = (int)tiempo;
		hor=total/3600;  
        min=(total-(3600*hor))/60;  
        seg=total-((hor*3600)+(min*60));  
        minutosString = hor + " hrs, " + min + " mins, " + seg + " segs.";

		return minutosString;
	}
	
	public Double getMinutosEscuchados() {
		return minutosEscuchados;
	}
	public void setMinutosEscuchados(Double minutosEscuchados) {
		this.minutosEscuchados = minutosEscuchados;
	}
	public Integer getCantidadUsuarios() {
		return cantidadUsuarios;
	}
	public void setCantidadUsuarios(Integer cantidadUsuarios) {
		this.cantidadUsuarios = cantidadUsuarios;
	}
	
	public Location(String countryCode, String countryName) {
		super();
		this.countryCode = countryCode;
		this.countryName = countryName;
		
	}
	public Location() {
		super();
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((countryCode == null) ? 0 : countryCode.hashCode());
		result = prime * result
				+ ((countryName == null) ? 0 : countryName.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Location other = (Location) obj;
		if (countryCode == null) {
			if (other.countryCode != null)
				return false;
		} else if (!countryCode.equals(other.countryCode))
			return false;
		if (countryName == null) {
			if (other.countryName != null)
				return false;
		} else if (!countryName.equals(other.countryName))
			return false;
		return true;
	}
	
	
	

}
