package com.esgi.pa.domain.services;

import org.springframework.stereotype.Service;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ErrorFormatService {

    public Map<String, Object> ErrorFormatExceptionHandle(List<ObjectError> errors) {
        Map<String, Object> map = new LinkedHashMap<>();
        List<FieldError> fieldErrors = errors.stream()
                .filter(FieldError.class::isInstance)
                .map(FieldError.class::cast)
                .toList();

        List<String> errorMessages = fieldErrors.stream()
                .map(fieldError -> fieldError.getField() + ": " + fieldError.getDefaultMessage())
                .collect(Collectors.toList());
        map.put("message", "Validation Failed");
        map.put("data", errorMessages);
        return map;
    }
}
