package com.esgi.pa.domain.exceptions;

import java.util.LinkedHashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class TechnicalNotFoundException extends Exception {

    private HttpStatus status;
    private final Map<String, Object> map = new LinkedHashMap<String, Object>();

    public TechnicalNotFoundException(String message) {
        super(message);
    }

    public TechnicalNotFoundException(HttpStatus statusCode, String message, Object object) {
        this.status = statusCode;
        this.map.put("message", message);
        this.map.put("data", object);
    }

    public TechnicalNotFoundException(HttpStatus statusCode, String message) {
        this.status = statusCode;
        this.map.put("message", message);
    }
}
