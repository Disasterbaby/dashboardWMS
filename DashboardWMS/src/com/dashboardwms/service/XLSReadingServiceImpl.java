package com.dashboardwms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.dashboardwms.exceptions.RutaInvalidaException;
import com.dashboardwms.utilities.Utilidades;
import com.vaadin.ui.Notification;

@Service
public class XLSReadingServiceImpl implements XLSReadingService {

	@Override
	public LinkedHashMap<Date, Double> getStreamingMinutes(
			String nombreAplicacion, Date fechaInicio, Date fechaFin) {
		LinkedHashMap<Date, Double>  hashmap= new LinkedHashMap<>() ;
		String ruta = Utilidades.RUTA_XLS + nombreAplicacion.replaceAll("\\s","") + Utilidades.RUTA_STREAMING_MINUTES;
        try {
			FileInputStream file = new FileInputStream(new File(ruta));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
		
			 
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	             
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					   Cell cell = row.getCell(1);
	                	Double minutos = cell.getNumericCellValue();
					if(fecha.before(fechaFin) && fecha.after(fechaInicio))
	                 hashmap.put(fecha, minutos);
	          
	            }
	            file.close();
        } catch (IOException e) {
			System.out.println("archivo no encontrado");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("archivo mal formateado");
			e.printStackTrace();
		}
		return hashmap;
	}

	@Override
	public LinkedHashMap<Date, Integer> getStreamingSessions(
			String nombreAplicacion, Date fechaInicio, Date fechaFin) {

		LinkedHashMap<Date, Integer>  hashmap= new LinkedHashMap<>() ;
		String ruta = Utilidades.RUTA_XLS + nombreAplicacion.replaceAll("\\s","") + Utilidades.RUTA_STREAMING_SESSIONS;

        try {
			FileInputStream file = new FileInputStream(new File(ruta));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
		
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	              
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					  Cell cell = row.getCell(1);
	                	Double sesionesDouble = cell.getNumericCellValue();
	                	Integer sesiones = sesionesDouble.intValue();
					if(fecha.before(fechaFin) && fecha.after(fechaInicio))
	                 hashmap.put(fecha, sesiones);
	          
	            }
	            file.close();
        } catch (IOException e) {
			System.out.println("archivo no encontrado");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("archivo mal formateado");
			e.printStackTrace();
		}
		return hashmap;
	}

	@Override
	public LinkedHashMap<Date, Integer> getCustomRegistrations(
			String nombreAplicacion, Date fechaInicio, Date fechaFin) {

		LinkedHashMap<Date, Integer>  hashmap= new LinkedHashMap<>() ;

		String ruta = Utilidades.RUTA_XLS + nombreAplicacion.replaceAll("\\s","") + Utilidades.RUTA_CUSTOM_REGISTRATIONS;
        try {
			FileInputStream file = new FileInputStream(new File(ruta));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					
					Cell cell = row.getCell(1);
                	Double registrosDouble = cell.getNumericCellValue();
                	Integer registros = registrosDouble.intValue();
									if(fecha.before(fechaFin) && fecha.after(fechaInicio))
	                 hashmap.put(fecha, registros);
	          
	            }
	            file.close();
        } catch (IOException e) {
			System.out.println("archivo no encontrado");
			e.printStackTrace();
		} catch (ParseException e) {
			System.out.println("archivo mal formateado");
			e.printStackTrace();
		}
		return hashmap;
	}


	@Override
	public Boolean verificarAppMovil(String nombreAplicacion) {
	File directory = new File(Utilidades.RUTA_XLS);
		

		File[] fList = directory.listFiles();

		
	    for (File file : fList) {
	         if (file.isDirectory()) {
	        	 String nombreCarpeta = file.getName();
	        	 if(nombreAplicacion.replaceAll("\\s","").equalsIgnoreCase(nombreCarpeta.replaceAll("\\s","")))
	        		 return true;
	          
	        }
	    }

		return false;
	}

	@Override
	public List<String> getListaAplicacionesMoviles() throws RutaInvalidaException{
		List<String> listaAplicaciones = new ArrayList<String>();

		File directory = new File(Utilidades.RUTA_XLS);
		

		File[] fList = directory.listFiles();

		if(fList==null)
			throw new RutaInvalidaException("No Consigue nada en el directorio " + directory);
	    for (File file : fList) {
	         if (file.isDirectory()) {
	        	 
	        	 listaAplicaciones.add(file.getName());
	          
	        }
	    }


		return listaAplicaciones;
	}

	@Override
	public LinkedHashMap<String, Integer> getRegistrationsByDevice(String nombreAplicacion) {
		LinkedHashMap<String, Integer>  hashmap= new LinkedHashMap<>() ;

		String ruta = Utilidades.RUTA_XLS + nombreAplicacion.replaceAll("\\s","") + Utilidades.RUTA_REGISTRATIONS_DEVICE;
        try {
			FileInputStream file = new FileInputStream(new File(ruta));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 rowIterator.next();
              while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Cell cell = row.getCell(0);
                	String tipoDevice = cell.getStringCellValue();
                	Cell cellCantidad = row.getCell(1);
                	Double cantidadDouble = cellCantidad.getNumericCellValue();
                	Integer cantidad = cantidadDouble.intValue();
                	
					
	                 hashmap.put(tipoDevice, cantidad);
	          
	            }
	            file.close();
        } catch (IOException e) {
			System.out.println("archivo no encontrado");
			e.printStackTrace();
		}
		return hashmap;
	}

}
