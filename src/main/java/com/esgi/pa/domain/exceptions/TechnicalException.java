package com.esgi.pa.domain.exceptions;

public class TechnicalException extends Exception {
    
    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Object object) {
        super(String.format(message, object));
    }
}
