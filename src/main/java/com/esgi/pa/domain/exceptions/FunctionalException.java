package com.esgi.pa.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class FunctionalException extends Exception {

    private HttpStatus status;

    public FunctionalException(String message) {
        super(message);
    }

    public FunctionalException(String message, Object object) {
        super(String.format(message, object));
    }

    public FunctionalException(HttpStatus statusCode, String message, Object object) {
        super(String.format(String.valueOf(statusCode), message, object));
        this.status = statusCode;
    }
}
