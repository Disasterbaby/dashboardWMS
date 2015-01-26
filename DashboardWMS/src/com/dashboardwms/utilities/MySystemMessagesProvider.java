package com.dashboardwms.utilities;

import com.vaadin.server.CustomizedSystemMessages;
import com.vaadin.server.SystemMessages;
import com.vaadin.server.SystemMessagesInfo;
import com.vaadin.server.SystemMessagesProvider;

public class MySystemMessagesProvider implements SystemMessagesProvider {

	@Override
	public SystemMessages getSystemMessages(
			SystemMessagesInfo systemMessagesInfo) {
		
		CustomizedSystemMessages messages = new CustomizedSystemMessages();
		
		
		messages.setCommunicationErrorURL(null);
		messages.setCommunicationErrorNotificationEnabled(true);
		messages.setCommunicationErrorCaption("Error de Comunicaci�n");
		messages.setCommunicationErrorMessage("Problemas de comunicaci�n con el servidor. Intente de nuevo. En caso de persistir comunicarse con el administrador del sistema");
		
		messages.setSessionExpiredURL(null);
		messages.setSessionExpiredNotificationEnabled(true);
		messages.setSessionExpiredCaption("Sesi�n Expirada");
		messages.setSessionExpiredMessage("Presione aqu� para ingresar de nuevo.");
		
		
		messages.setInternalErrorURL(null);
		messages.setInternalErrorNotificationEnabled(true);
		messages.setInternalErrorCaption("Error Interno");
		messages.setInternalErrorMessage("Por favor notifique al administrador del sistema");
				
		messages.setCookiesDisabledURL(null);
		messages.setCookiesDisabledNotificationEnabled(true);
		messages.setCookiesDisabledCaption("Cookies deshabilitadas");
		messages.setCookiesDisabledMessage("Esta aplicaci�n requiere el uso de cookies para funcionar. Por favor active las cookies en el navegador browser y presione aqu� para intentar de nuevo.");
		
		
		messages.setOutOfSyncURL(null);
		messages.setOutOfSyncNotificationEnabled(true);
		messages.setOutOfSyncCaption("Fuera de sincronizaci�n");
		messages.setOutOfSyncMessage("Algo ha causado la desincronizaci�n de la aplicaci�n. Tome nota de datos no guardados y presione aqu� para re-sincronizar");
	
		
		return messages;
	}

}
