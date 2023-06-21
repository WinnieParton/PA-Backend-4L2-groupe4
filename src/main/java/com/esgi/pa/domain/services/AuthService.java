package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.UserAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final UserAdapter adapter;

    public String createBase64Token(User user) throws TechnicalNotFoundException {
        try {
            return Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(user));
        } catch (JsonProcessingException exception) {
            throw new TechnicalNotFoundException(HttpStatus.FORBIDDEN, "Json Parsing exception : "+ exception.getMessage());
        }
    }
    
    public boolean validateBase64Token(String token) throws TechnicalNotFoundException {
        try {
            String encodeUserString = Arrays.toString(Base64.getDecoder().decode(token));
            User user = objectMapper.readValue(encodeUserString, User.class);
            return adapter.findById(user.getId()).isPresent();
        } catch (JsonProcessingException exception) {
            throw new TechnicalNotFoundException(HttpStatus.FORBIDDEN,"Json Processing exception: "+ exception.getMessage());
        }
    }
}
