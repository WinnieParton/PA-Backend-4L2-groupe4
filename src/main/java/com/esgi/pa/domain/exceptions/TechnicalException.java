package com.esgi.pa.domain.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class TechnicalException extends Exception {
    
    public TechnicalException(String message) {
        log.error(message, getCause());
    }

    public TechnicalException(String message, Object object) {
        log.error(String.format(message, object));
    }
}
