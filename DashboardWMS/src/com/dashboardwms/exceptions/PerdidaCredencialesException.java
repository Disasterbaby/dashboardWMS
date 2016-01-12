package com.dashboardwms.exceptions;

public class PerdidaCredencialesException extends Exception {
	 
    
		private String message = null;
	 
	    public PerdidaCredencialesException() {
	        super();
	    }
	 
	    public PerdidaCredencialesException(String message) {
	        super(message);
	        this.message = message;
	    }
	 
	    public PerdidaCredencialesException(Throwable cause) {
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
