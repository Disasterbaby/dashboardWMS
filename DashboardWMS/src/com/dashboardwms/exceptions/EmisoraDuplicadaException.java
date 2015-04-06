package com.dashboardwms.exceptions;

public class EmisoraDuplicadaException extends Exception {
	 
    
		private String message = null;
	 
	    public EmisoraDuplicadaException() {
	        super();
	    }
	 
	    public EmisoraDuplicadaException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public EmisoraDuplicadaException(Throwable cause) {
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
