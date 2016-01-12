package com.dashboardwms.service;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.util.ByteArrayBuffer;

import com.dashboardwms.domain.Usuario;
import com.vaadin.ui.Layout;
import com.vaadin.ui.Notification;
import com.vaadin.ui.ProgressBar;
import com.vaadin.ui.Upload.FinishedEvent;
import com.vaadin.ui.Upload.FinishedListener;
import com.vaadin.ui.Upload.ProgressListener;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.StartedEvent;
import com.vaadin.ui.Upload.StartedListener;


public class UploadReceiver implements Receiver, ProgressListener,
		StartedListener, FinishedListener {


	public ByteArrayOutputStream outputBuffer = null;
	private Usuario usuario;
	
	

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public UploadReceiver() {
		
		this.usuario = usuario;
	}

	@Override
	public void uploadStarted(StartedEvent event) {
		String filename = event.getFilename();
		String[] splitNombreArchivo = filename.split("\\.");
		String extension = splitNombreArchivo[1];
		if ((!extension.equals("png")) && (!extension.equals("ico")))
		{		Notification.show(
					"El archivo " + filename + " debe ser imagen con extensión .png o .ico",
					Notification.Type.ERROR_MESSAGE);
		event.getUpload().interruptUpload();
		}
	}

	@Override
	public void uploadFinished(FinishedEvent event) {
	String mensaje = "teeeeermino";
	InputStream input = null;
//	try{
//		 input = new ByteArrayInputStream(
//				((ByteArrayOutputStream) outputBuffer).toByteArray());
//		 BufferedInputStream bis = new BufferedInputStream(input);
//
//         ByteArrayBuffer baf = new ByteArrayBuffer(500);
//         int current = 0;
//         while ((current = bis.read()) != -1) {
//                 baf.append((byte) current);
//         }	 
//	
//	}
//		catch(Exception e){
//			System.out.println("Debe seleccionar un archivo");
//		}
	}

	@Override
	public void updateProgress(long readBytes, long contentLength) {
		try {
			// let's slow down upload a bit
			Thread.sleep(20);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		float newValue = readBytes / (float) contentLength;
		
	}


	@Override
	   public OutputStream receiveUpload(String filename, String MIMEType) {
        outputBuffer = new ByteArrayOutputStream();
        return outputBuffer;
    }

	



}
