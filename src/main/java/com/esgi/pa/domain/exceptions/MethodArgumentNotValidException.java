package com.esgi.pa.domain.exceptions;

import java.util.Map;

public class MethodArgumentNotValidException extends RuntimeException {
    private final Map<String, Object> errors;

    public MethodArgumentNotValidException(Map<String, Object> errors) {
        super("Validation failed");
        this.errors = errors;
    }

    public Map<String, Object> getErrors() {
        return errors;
    }
}
