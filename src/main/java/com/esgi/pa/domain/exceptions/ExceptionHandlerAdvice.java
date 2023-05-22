package com.esgi.pa.domain.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ExceptionHandlerAdvice {

    @ExceptionHandler({FunctionalException.class, TechnicalException.class})
    public ResponseEntity handleException(FunctionalException e) {
        return ResponseEntity.status(e.getStatus()).body(e.getMessage());
    }
}