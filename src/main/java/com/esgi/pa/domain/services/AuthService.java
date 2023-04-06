package com.esgi.pa.domain.services;

import java.util.Base64;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.server.adapter.UserAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final UserAdapter adapter;

    public String createBase64Token(User user) throws JsonProcessingException {
        return Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(user));
    }
    
    public boolean validateBase64Token(String token) throws JsonMappingException, JsonProcessingException {
        String encodeUserString = String.valueOf(Base64.getDecoder().decode(token));
        User user = objectMapper.readValue(encodeUserString, User.class);
        return adapter.findById(user.getId()).isPresent();
    }
}
