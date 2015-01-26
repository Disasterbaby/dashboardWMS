package com.dashboardwms.service;

import java.io.IOException;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.dashboardwms.domain.Servidor;


public interface XMLConnectionService {
	
	public Servidor getLiveData() throws ParserConfigurationException, SAXException, IOException;

}
