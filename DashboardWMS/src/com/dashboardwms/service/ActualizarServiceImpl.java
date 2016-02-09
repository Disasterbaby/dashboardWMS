package com.dashboardwms.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.stereotype.Service;
import org.xml.sax.SAXException;

import com.dashboardwms.dao.ConexionDAO;
import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;
import com.dashboardwms.utilities.Utilidades;



@Service
public class ActualizarServiceImpl implements ActuallizarService {

	
@Resource
ConexionDAO conexion;

	@Override
	public void insertUpdateInfo(String nombreAplicacion)throws ParserConfigurationException, SAXException, IOException, ClassNotFoundException, SQLException
 {
		Date fechaActual = new Date();
		String timestamp = Utilidades.DATE_FORMAT.format(fechaActual);
		XMLReader  xmlReader = new XMLReader();
		Servidor servidor = xmlReader.getXML(nombreAplicacion);
	List<Aplicacion> listaAplicaciones =	servidor.getListaAplicaciones();
		for (Aplicacion aplicacion : listaAplicaciones) {
			
			conexion.insertUpdateAplicacion(aplicacion, timestamp);
			List<Cliente> listaClientes = aplicacion.getListaClientes();
			if(listaClientes != null)
			for (Cliente cliente : listaClientes) {
				cliente.setIdAplicacion(aplicacion.getNombre());
				conexion.insertUpdateCliente(cliente, timestamp);
			}
		}
	}

}
