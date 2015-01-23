package com.dashboardwms.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dashboardwms.domain.Aplicacion;
import com.dashboardwms.domain.Cliente;
import com.dashboardwms.domain.Servidor;

public class XMLReader {

	public XMLReader() {

	}

	public Servidor getXML(String urllink) throws ParserConfigurationException,
			SAXException, IOException {
		Servidor servidor = new Servidor();
		try {
			Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("svnplcm13", 8080));
			// Crear URL
			URL url = new URL(urllink);
			// Crear conexion
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection(proxy);
			urlConnection.setRequestMethod("GET");
			urlConnection.setDoOutput(true);
			urlConnection.setDoInput(true);
			urlConnection
					.setRequestProperty("Content-type", "application/json");
			urlConnection.setAllowUserInteraction(true);

			urlConnection.connect();

			InputStream stream = urlConnection.getInputStream();

			// Get the DOM Builder Factory
			DocumentBuilderFactory factory = DocumentBuilderFactory
					.newInstance();

			// Get the DOM Builder
			DocumentBuilder builder = factory.newDocumentBuilder();

			// Load and Parse the XML document
			// document contains the complete XML as a Tree.
			Document document = builder.parse(stream);

			NodeList nodeList = document.getDocumentElement().getChildNodes();
			List<Aplicacion> listaAplicaciones = new ArrayList<Aplicacion>();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String nodeName = node.getNodeName();
				if (nodeName.equalsIgnoreCase("VHost")) {

					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);

						String nodeNameVHost = cNode.getNodeName();
						String valorVhost = cNode.getLastChild()
								.getTextContent().trim();

						if (nodeNameVHost
								.equalsIgnoreCase("ConnectionsCurrent")) {
							Integer conexionesActuales = Integer
									.parseInt(valorVhost);
							servidor.setConexionesActualesInt(conexionesActuales);

						}

						if (nodeNameVHost.equalsIgnoreCase("Application")) {
							System.out.println("Aplicacion");
							Aplicacion aplicacion = new Aplicacion();
							List<Cliente> listaClientes = new ArrayList<Cliente>();
							NodeList nodosAplicacion = cNode.getChildNodes();
							for (int k = 0; k < nodosAplicacion.getLength(); k++) {
								Node nodoAplicacion = nodosAplicacion.item(k);
								String nombreNodoAplicacion = nodoAplicacion
										.getNodeName();
								String valorNodoAplicacion = nodoAplicacion
										.getLastChild().getTextContent().trim();

								switch (nombreNodoAplicacion) {
								case "Name": {
									aplicacion.setNombre(valorNodoAplicacion);
									break;
								}
								case "Status": {
									aplicacion.setEstatus(valorNodoAplicacion);
									break;
								}
								case "TimeRunning": {
									Double tiempoCorriendo = Double
											.valueOf(valorNodoAplicacion);
									aplicacion
											.setTiempoCorriendo(tiempoCorriendo);
									break;
								}

								case "ConnectionsCurrent": {
									Integer conexiones = Integer
											.parseInt(valorNodoAplicacion);
									aplicacion
											.setConexionesActuales(conexiones);
									break;
								}
								}
								if (nombreNodoAplicacion
										.equalsIgnoreCase("ApplicationInstance")) {
									System.out.println("Aplicacion Instance");
									NodeList nodosInstancia = nodoAplicacion
											.getChildNodes();
									for (int l = 0; l < nodosInstancia
											.getLength(); l++) {

										Node nodoInstancia = nodosInstancia
												.item(l);
										String nombreNodoInstancia = nodoInstancia
												.getNodeName();
										String valorNodoInstancia = nodoInstancia
												.getLastChild()
												.getTextContent().trim();

										switch (nombreNodoInstancia) {
										case "RTMPConnectionCount": {
											Integer rtmpConnectionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setRtmpConnectionCount(rtmpConnectionCount);
											break;
										}
										case "RTPConnectionCount": {
											Integer rtpConnectionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setRtpConnectionCount(rtpConnectionCount);
											break;
										}
										case "CupertinoConnectionCount": {
											Integer cupertinoConnectionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setCupertinoConnectionCount(cupertinoConnectionCount);
											break;
										}
										case "SmoothConnectionCount": {
											Integer smoothConnectionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setSmoothConnectionCount(smoothConnectionCount);
											break;
										}
										case "SanJoseConnectionCount": {
											Integer sanJoseConnectionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setSanJoseConnectionCount(sanJoseConnectionCount);
											break;
										}
										case "RTMPSessionCount": {
											Integer rtmpSessionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setRtmpSessionCount(rtmpSessionCount);
											break;
										}
										case "HTTPSessionCount": {
											Integer httpSessionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setHttpSessionCount(httpSessionCount);
											break;
										}
										case "RTPSessionCount": {
											Integer rtpSessionCount = Integer
													.parseInt(valorNodoInstancia);
											aplicacion
													.setRtpSessionCount(rtpSessionCount);
											break;
										}

										}

										if (nombreNodoInstancia
												.equalsIgnoreCase("Client")) {
											System.out.println("client");
											Cliente cliente = new Cliente();
											cliente.setTipo("Cliente");
											NodeList nodosCliente = nodoInstancia
													.getChildNodes();
											for (int m = 0; m < nodosCliente
													.getLength(); m++) {
												Node nodoCliente = nodosCliente
														.item(m);
												String nombreNodoCliente = nodoCliente
														.getNodeName();
												String valorNodoCliente = nodoCliente
														.getLastChild()
														.getTextContent()
														.trim();

												switch (nombreNodoCliente) {
												case "ClientId": {
													cliente.setClientID(valorNodoCliente);
													break;
												}
												case "IpAddress": {
													cliente.setIpAddress(valorNodoCliente);
													break;
												}
												case "Protocol": {
													cliente.setProtocolo(valorNodoCliente);
													break;
												}
												case "TimeRunning": {
													Double tiempoCorriendo = Double
															.parseDouble(valorNodoCliente);
													cliente.setTimeRunning(tiempoCorriendo);
													break;
												}
												case "FlashVersion": {
													cliente.setFlashVersion(valorNodoCliente);
													break;
												}
												case "DateStarted":{
													cliente.setFechaInicio(valorNodoCliente);
													break;
												}
												}
											}
											listaClientes.add(cliente);

										}
										if (nombreNodoInstancia
												.equalsIgnoreCase("HTTPSession")) {
											System.out.println("Cliente http ");
											Cliente cliente = new Cliente();
											cliente.setTipo("HTTPSession");
											NodeList nodosCliente = nodoInstancia
													.getChildNodes();
											for (int m = 0; m < nodosCliente
													.getLength(); m++) {
												Node nodoCliente = nodosCliente
														.item(m);
												String nombreNodoCliente = nodoCliente
														.getNodeName();
												String valorNodoCliente = nodoCliente
														.getLastChild()
														.getTextContent()
														.trim();

												switch (nombreNodoCliente) {
												case "SessionId": {
													cliente.setClientID(valorNodoCliente);
													break;
												}
												case "IpAddress": {
													cliente.setIpAddress(valorNodoCliente);
													break;
												}
												case "Protocol": {
													cliente.setProtocolo(valorNodoCliente);
													break;
												}
												case "TimeRunning": {
													Double tiempoCorriendo = Double
															.parseDouble(valorNodoCliente);
													cliente.setTimeRunning(tiempoCorriendo);
													break;
												}
												case "DateStarted":{
													cliente.setFechaInicio(valorNodoCliente);
													break;
												}
												}
											}
											listaClientes.add(cliente);
										}
										if (nombreNodoInstancia
												.equalsIgnoreCase("RTPSession")) {
											System.out.println("Cliente rtp");
											Cliente cliente = new Cliente();
											cliente.setTipo("RTPSession");
											cliente.setProtocolo("RTP");
											NodeList nodosCliente = nodoInstancia
													.getChildNodes();
											for (int m = 0; m < nodosCliente
													.getLength(); m++) {
												Node nodoCliente = nodosCliente
														.item(m);
												String nombreNodoCliente = nodoCliente
														.getNodeName();
												String valorNodoCliente = nodoCliente
														.getLastChild()
														.getTextContent()
														.trim();

												switch (nombreNodoCliente) {
												case "SessionId": {
													cliente.setClientID(valorNodoCliente);
													break;
												}
												case "IpAddress": {
													cliente.setIpAddress(valorNodoCliente);
													break;
												}
												case "TimeRunning": {
													Double tiempoCorriendo = Double
															.parseDouble(valorNodoCliente);
													cliente.setTimeRunning(tiempoCorriendo);
													break;
												}
												case "DateStarted":{
													cliente.setFechaInicio(valorNodoCliente);
													break;
												}
												}
											}
											listaClientes.add(cliente);
										}

									}
									aplicacion.setListaClientes(listaClientes);
								}
							}
						listaAplicaciones.add(aplicacion);
						}

					}
				}
			}
			servidor.setListaAplicaciones(listaAplicaciones);
			stream.close();
			urlConnection.disconnect();
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Exception:: " + e.getMessage());
		}
		return servidor;

	}

}
