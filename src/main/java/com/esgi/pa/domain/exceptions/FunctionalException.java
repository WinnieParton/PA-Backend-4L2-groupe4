package com.esgi.pa.domain.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionalException extends Exception {
    
    public FunctionalException(String message) {
        log.error(message, getCause());
    }

    public FunctionalException(String message, Object object) {
        log.error(String.format(message, object));
    }
}
