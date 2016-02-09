package com.dashboardwms.utilities;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimplePdfExporterConfiguration;

import com.vaadin.addon.charts.model.Configuration;
import com.vaadin.addon.charts.util.SVGGenerator;
import com.vaadin.server.StreamResource;
import com.vaadin.server.VaadinService;
import com.vaadin.ui.Embedded;

public class Utilidades {
 public static final String XML_URL = "http://server.plugstreaming.com:8086/serverinfo";
 
 

public static final String ABSOLUTE_PATH = VaadinService.getCurrent()
			.getBaseDirectory().getAbsolutePath();
 public static final String LOCATIONS_DB = ABSOLUTE_PATH + "/location/GeoLiteCity.dat";
 
 

public static final String TODAS_EMISORAS = "Todas";
 
 public static final DateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 
 
 public static final Date TODAY = Calendar.getInstance(TimeZone.getTimeZone("America/Caracas")).getTime();
 
 public static final DateFormat DATE_QUERY = new SimpleDateFormat("yyyy-MM-dd");
 
 
 public static final DateFormat DATE_FORMAT_LOCAL = new SimpleDateFormat("dd-MM-yyyy");
 
 public static final String HOY = "Hoy";
 
 public static final String ULTIMA_SEMANA = "Última Semana";
		 
 public static final String ULTIMA_QUINCENA = "Últimos 15 Días";
 
 public static final String ULTIMO_MES = "Últimos 30 Días";
 
 public static final String CUSTOM_DATE = "Período Específico";
 
 public static final List<String> LISTA_PERIODOS = Arrays.asList(HOY, ULTIMA_SEMANA, ULTIMA_QUINCENA, ULTIMO_MES, CUSTOM_DATE);

 public static final String URL_IMG = "/images/plugstreaming.png";
 
public static final String REPORT_JRXML = "/reports/PlugStreaming.jrxml";


public static final String REPORT_JASPER = "/reports/PlugStreaming.jasper";
 
 public static final String STYLE_SELECTED = "selected";

 public static final String STYLE_VISIBLE = "valo-menu-visible";
 
//public static final String RUTA_XLS = "C:/Users/Virgilio Melo/Desktop/Pantallas estadisticas/archivos/aplicaciones/";

 public static final String RUTA_XLS = "/home/plugradio/plugstreaming/plugstds/aplicaciones/";

 public static final String RUTA_STREAMING_MINUTES = "/StreamingMinutes.xls";
 
 public static final String RUTA_STREAMING_SESSIONS = "/StreamingSessions.xls";
 
 public static final String RUTA_CUSTOM_REGISTRATIONS = "/CustomRegistrations.xls";
 
 public static final String RUTA_REGISTRATIONS_DEVICE = "/RegistrationsByDevice.xls";
 
 

public static final SimpleDateFormat formatoFechaArchivos = new SimpleDateFormat("yyyyMMdd");
 
 public static String timeRunning(double tiempo){
	 
		int hor; 
			int total = (int)tiempo;
			hor=total/3600;  
			return "Horas Transmitidas: " + hor;
			
		
 }

	public static Embedded buildPDF(final Configuration conf, final String titulo) {
		Embedded object = new Embedded();
		try {


			StreamResource.StreamSource source = new StreamResource.StreamSource() {
				public InputStream getStream() {
					byte[] b = null;
					String pathReporte = ABSOLUTE_PATH + REPORT_JASPER;
					String pathImg = ABSOLUTE_PATH + URL_IMG;
					Map<String, Object> params = new HashMap<String, Object>();
					
					
					params.put("PATH_LOGO", pathImg);
					params.put("SVG_STRING", SVGGenerator.getInstance().generate(conf));
					params.put("TITULO", titulo.toUpperCase());

					JRPdfExporter exporter = new JRPdfExporter();
			        JasperPrint jprint;

			        ByteArrayOutputStream baos = new ByteArrayOutputStream();
					try {
						jprint = JasperFillManager.fillReport(pathReporte, params,  new JREmptyDataSource());
						
					exporter.setExporterInput(new SimpleExporterInput(jprint));
					exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(baos));
					exporter.exportReport();
					b = baos.toByteArray();
					} catch (Exception e) {
						e.printStackTrace();
					}

			
					return new ByteArrayInputStream(
							baos.toByteArray());
				}

			};



			SimpleDateFormat df = new SimpleDateFormat("yMMddHHmS");
			String filename = "grafico-" + df.format(new Date()) + ".pdf";
			StreamResource resource = new StreamResource(source, filename);
			resource.setCacheTime(0);
			resource.setMIMEType("application/pdf");

			
			object = new Embedded();
			object.setSizeFull();
			object.setSource(resource);
			object.setType(Embedded.TYPE_BROWSER);
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		return object;
		
	}
 
 }
