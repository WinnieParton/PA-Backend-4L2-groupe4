package com.esgi.pa.domain.exceptions;

public class FunctionalException extends Exception {
    
    public FunctionalException(String message) {
        super(message);
    }

    public FunctionalException(String message, Object object) {
        super(String.format(message, object));
    }
}
