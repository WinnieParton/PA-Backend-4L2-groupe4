package com.esgi.pa.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import java.util.LinkedHashMap;
import java.util.Map;
@Getter
public class TechnicalException extends Exception {

    private HttpStatus status;
    private final Map<String, Object> map = new LinkedHashMap<String, Object>();

    public TechnicalException(String message) {
        super(message);
    }

    public TechnicalException(HttpStatus statusCode, String message, Object object) {
        this.status = statusCode;
        this.map.put("message", message);
        this.map.put("data", object);
    }

    public TechnicalException(HttpStatus statusCode, String message) {
        this.status = statusCode;
        this.map.put("message", message);
    }
}
