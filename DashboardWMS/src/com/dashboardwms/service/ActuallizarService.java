package com.dashboardwms.service;

import java.io.IOException;
import java.sql.SQLException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

public interface ActuallizarService {
	
	public void insertUpdateInfo(String nombreAplicacion) throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException;
 

}