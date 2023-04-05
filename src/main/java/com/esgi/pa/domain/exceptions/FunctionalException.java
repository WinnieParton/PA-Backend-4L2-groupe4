package com.esgi.pa.domain.exceptions;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FunctionalException extends Exception {
    
    public void error(String message) {
        log.error(message, getCause());
    }
}
