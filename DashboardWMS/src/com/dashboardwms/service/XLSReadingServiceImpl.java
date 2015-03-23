package com.dashboardwms.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedHashMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.stereotype.Service;

import com.dashboardwms.utilities.Utilidades;

@Service
public class XLSReadingServiceImpl implements XLSReadingService {

	@Override
	public LinkedHashMap<Date, Double> getStreamingMinutes(
			String nombreAplicacion, Date fechaInicio, Date fechaFin) {
		LinkedHashMap<Date, Double>  hashmap= new LinkedHashMap<>() ;
		
        try {
			FileInputStream file = new FileInputStream(new File(Utilidades.RUTA_STREAMING_MINUTES));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 Row firstRow = sheet.getRow(0);
			 int indice = 0;
             Iterator<Cell> firstRowIterator = firstRow.cellIterator();
             while (firstRowIterator.hasNext())
             {
            	 
                 Cell cell = firstRowIterator.next();
                 String nombreAplicacionCelda = cell.getStringCellValue();
                 nombreAplicacionCelda =  nombreAplicacionCelda.trim();
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(".", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(" ", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace("&", "");
              
                 if(nombreAplicacion.equalsIgnoreCase(nombreAplicacionCelda))
                 {
                	 System.out.println("bingo");
                	 indice =  cell.getColumnIndex();
                	 System.out.println(indice);
                	 break;}
             }
			 
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Cell cell = row.getCell(indice);
                	Double minutos = cell.getNumericCellValue();
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					System.out.print("periodo " + periodo + " - ");
					System.out.println("minutos " + minutos);
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					System.out.println("fecha " + fecha);

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
		
        try {
			FileInputStream file = new FileInputStream(new File(Utilidades.RUTA_STREAMING_SESSIONS));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 Row firstRow = sheet.getRow(0);
			 int indice = 0;
             Iterator<Cell> firstRowIterator = firstRow.cellIterator();
             while (firstRowIterator.hasNext())
             {
            	 
                 Cell cell = firstRowIterator.next();
                 String nombreAplicacionCelda = cell.getStringCellValue();
                 nombreAplicacionCelda =  nombreAplicacionCelda.trim();
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(".", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(" ", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace("&", "");
              
                 if(nombreAplicacion.equalsIgnoreCase(nombreAplicacionCelda))
                 {
                	 System.out.println("bingo");
                	 indice =  cell.getColumnIndex();
                	 System.out.println(indice);
                	 break;}
             }
			 
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Cell cell = row.getCell(indice);
                	Double sesionesDouble = cell.getNumericCellValue();
                	Integer sesiones = sesionesDouble.intValue();
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					System.out.print("periodo " + periodo + " - ");
					System.out.println("sesiones " + sesiones);
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					System.out.println("fecha " + fecha);
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
		
        try {
			FileInputStream file = new FileInputStream(new File(Utilidades.RUTA_CUSTOM_REGISTRATIONS));
			HSSFWorkbook wb = new HSSFWorkbook(file);
			HSSFSheet sheet = wb.getSheetAt(0);
			 Iterator<Row> rowIterator = sheet.iterator();
			 Row firstRow = sheet.getRow(0);
			 int indice = 0;
             Iterator<Cell> firstRowIterator = firstRow.cellIterator();
             while (firstRowIterator.hasNext())
             {
            	 
                 Cell cell = firstRowIterator.next();
                 String nombreAplicacionCelda = cell.getStringCellValue();
                 nombreAplicacionCelda =  nombreAplicacionCelda.trim();
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(".", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace(" ", "");
                 nombreAplicacionCelda = nombreAplicacionCelda.replace("&", "");
              
                 if(nombreAplicacion.equalsIgnoreCase(nombreAplicacionCelda))
                 {
                	 System.out.println("bingo");
                	 indice =  cell.getColumnIndex();
                	 System.out.println(indice);
                	 break;}
             }
			 
             rowIterator.next();
			 
	           while (rowIterator.hasNext())
	            {
	                Row row = rowIterator.next();
	                //For each row, iterate through all the columns
	                Cell cell = row.getCell(indice);
                	Double registrosDouble = cell.getNumericCellValue();
                	Integer registros = registrosDouble.intValue();
                	
                	Double periodoDouble = row.getCell(0)
							.getNumericCellValue();
					Integer periodoInt = periodoDouble.intValue();
					String periodo = periodoInt.toString();
					System.out.print("periodo " + periodo + " - ");
					System.out.println("registros " + registros);
					Date fecha = Utilidades.formatoFechaArchivos.parse(periodo);
					System.out.println("fecha " + fecha);

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
	public LinkedHashMap<String, Integer> getEstadisticas(
			String nombreAplicacion, Date fechaInicio, Date fechaFin) {
		// TODO Auto-generated method stub
		return null;
	}

}
