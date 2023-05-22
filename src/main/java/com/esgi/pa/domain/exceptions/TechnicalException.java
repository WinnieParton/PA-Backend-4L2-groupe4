package com.esgi.pa.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class TechnicalException extends Exception {

    private HttpStatus status;

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(String message, Object object) {
        super(String.format(message, object));
    }

    public TechnicalException(HttpStatus statusCode, String message, Object object) {
        super(String.format(String.valueOf(statusCode), message, object));
        this.status = statusCode;
    }
}
