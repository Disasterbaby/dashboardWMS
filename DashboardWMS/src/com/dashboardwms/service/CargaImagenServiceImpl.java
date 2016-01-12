package com.dashboardwms.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.ByteArrayBuffer;
import org.springframework.stereotype.Service;

@Service
public class CargaImagenServiceImpl implements CargaImagenService {

	@Override
	public void cargaImagen(OutputStream os) {
		System.out.println("Cargando imagen");
		InputStream input = null;
		try{
			 input = new ByteArrayInputStream(
					((ByteArrayOutputStream) os).toByteArray());
			 BufferedInputStream bis = new BufferedInputStream(input);

             ByteArrayBuffer baf = new ByteArrayBuffer(500);
             int current = 0;
             while ((current = bis.read()) != -1) {
                     baf.append((byte) current);
             }	 
		
		}
			catch(Exception e){
				System.out.println("Debe seleccionar un archivo");
			}
	}

}
