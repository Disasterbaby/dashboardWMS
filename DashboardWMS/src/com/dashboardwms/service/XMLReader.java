package com.dashboardwms.service;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.dashboardwms.domain.connectioncounts.VHost;
import com.dashboardwms.domain.connectioncounts.WowzaMediaServer;

public class XMLReader {

	public XMLReader() {

	}

	public void getXML(String urllink) throws ParserConfigurationException,
			SAXException, IOException {
		try {
			// Crear URL
			URL url = new URL(urllink);
			// Crear conexion
			HttpURLConnection urlConnection = (HttpURLConnection) url
					.openConnection();
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
			WowzaMediaServer infoConnectionCounts = new WowzaMediaServer();
			for (int i = 0; i < nodeList.getLength(); i++) {
				Node node = nodeList.item(i);
				String nodeName = node.getNodeName();
				String valor = node.getLastChild().getTextContent().trim();
				switch (nodeName) {
				case "ConnectionsCurrent": {
					infoConnectionCounts.setConnectionsCurrent(valor);
					break;
				}
				case "ConnectionsTotal": {
					infoConnectionCounts.setConnectionsTotal(valor);
					break;
				}
				case "ConnectionsTotalAccepted": {
					infoConnectionCounts.setConnectionsTotalAccepted(valor);
					break;
				}

				case "ConnectionsTotalRejected": {
					infoConnectionCounts.setConnectionsTotalRejected(valor);
					break;
				}

				case "MessagesInBytesRate": {
					infoConnectionCounts.setMessagesInBytesRate(valor);
					break;
				}
				case "MessagesOutBytesRate": {
					infoConnectionCounts.setMessagesOutBytesRate(valor);
					break;
				}

				}

				if (nodeName.equalsIgnoreCase("VHost")) {
					VHost vHost = new VHost();
					NodeList childNodes = node.getChildNodes();
					for (int j = 0; j < childNodes.getLength(); j++) {
						Node cNode = childNodes.item(j);

						String nodeNameVHost = cNode.getNodeName();
						String valorVhost = cNode.getLastChild()
								.getTextContent().trim();

						switch (nodeNameVHost) {
						case "Name": {
							vHost.setName(valorVhost);
							break;
						}
						case "TimeRunning": {
							vHost.setTimeRunning(valorVhost);
							break;
						}
						case "ConnectionsLimit": {
							vHost.setConnectionsLimit(valorVhost);
							break;
						}
						case "ConnectionsCurrent": {
							vHost.setConnectionsCurrent(valorVhost);
							break;
						}
						case "ConnectionsTotal": {
							vHost.setConnectionsTotal(valorVhost);
							break;
						}
						case "ConnectionsTotalAccepted": {
							vHost.setConnectionsTotalAccepted(valorVhost);
							break;
						}
						case "ConnectionsTotalRejected": {
							vHost.setConnectionsTotalRejected(valorVhost);
							break;
						}
						case "MessagesInBytesRate": {
							vHost.setMessagesInBytesRate(valorVhost);
							break;
						}
						case "MessagesOutBytesRate": {
							vHost.setMessagesOutBytesRate(valorVhost);
							break;
						}
						}
						
						if(nodeNameVHost.equalsIgnoreCase("Application")){
							
						}
					}

					System.out.println(vHost.toString());
				}
			}
			System.out.println(infoConnectionCounts.toString());

			stream.close();
			urlConnection.disconnect();
		} catch (Exception e) {
			System.out.println("Exception:: " + e.getMessage());
		}

	}

}
