package com.dashboardwms.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;

import com.dashboardwms.utilities.Utilidades;
import com.vaadin.server.Page;
import com.vaadin.ui.Notification;
import com.vaadin.ui.Upload.Receiver;
import com.vaadin.ui.Upload.SucceededEvent;
import com.vaadin.ui.Upload.SucceededListener;

public class ImageUploader implements Receiver, SucceededListener {
   
	public File file;
    
    public OutputStream receiveUpload(String filename,
                                      String mimeType) {
        // Create upload stream
        FileOutputStream fos = null; // Stream to write to
        try {
            // Open the file for writing.
            file = new File(Utilidades.RUTA_XLS + filename);
            fos = new FileOutputStream(file);
        } catch (final java.io.FileNotFoundException e) {
            new Notification("Debe seleccionar un archivo",
                             Notification.Type.ERROR_MESSAGE)
                .show(Page.getCurrent());
            return null;
        }
        return fos; // Return the output stream to write to
    }

	@Override
	public void uploadSucceeded(SucceededEvent event) {
		
		
	}

 
};