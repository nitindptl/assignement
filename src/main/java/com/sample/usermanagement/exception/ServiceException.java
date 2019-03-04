package com.sample.usermanagement.exception;

public class ServiceException extends Exception {

    private static final long serialVersionUID = 2520578473273042344L;

    public ServiceException() {
    }
    
    public ServiceException(String errorCode, Throwable cause) {
        super(errorCode, cause);
    }
    
    public ServiceException(String message) {
        super(message);
    }
    
    public ServiceException(Throwable cause) {
        super(cause);
    }
}
