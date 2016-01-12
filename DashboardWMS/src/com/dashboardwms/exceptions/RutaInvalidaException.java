package com.dashboardwms.exceptions;

public class RutaInvalidaException extends Exception {


	private String message = null;
 
    public RutaInvalidaException() {
        super();
    }
 
    public RutaInvalidaException(String message) {
        super(message);
        this.message = message;
    }
 
    public RutaInvalidaException(Throwable cause) {
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
