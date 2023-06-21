package com.esgi.pa.domain.exceptions;

import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.LinkedHashMap;
import java.util.Map;

@Getter
public class TechnicalFoundException extends Exception {

    public TechnicalFoundException(String message) {
        super(message);
    }

}
