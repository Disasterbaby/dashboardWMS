package com.dashboardwms.exceptions;

public class UsuarioDuplicadoException extends Exception {
 
    
	private String message = null;
 
    public UsuarioDuplicadoException() {
        super();
    }
 
    public UsuarioDuplicadoException(String message) {
        super(message);
        this.message = message;
    }
 
    public UsuarioDuplicadoException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }


}
