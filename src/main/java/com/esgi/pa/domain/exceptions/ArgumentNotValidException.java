package com.esgi.pa.domain.exceptions;

import java.util.Map;

public class ArgumentNotValidException extends RuntimeException {

    private final Map<String, Object> errors;

    public ArgumentNotValidException(Map<String, Object> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }

}
