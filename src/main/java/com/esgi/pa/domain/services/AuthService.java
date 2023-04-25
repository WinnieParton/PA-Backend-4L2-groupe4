package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.Base64;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final ObjectMapper objectMapper;
    private final UserAdapter adapter;

    public String createBase64Token(User user) throws TechnicalException {
        try {
            return Base64.getEncoder().encodeToString(objectMapper.writeValueAsBytes(user));
        } catch (JsonProcessingException exception) {
            throw new TechnicalException("Json Parsing exception : %s", exception.getMessage());
        }
    }
    
    public boolean validateBase64Token(String token) throws TechnicalException {
        try {
            String encodeUserString = Arrays.toString(Base64.getDecoder().decode(token));
            User user = objectMapper.readValue(encodeUserString, User.class);
            return adapter.findById(user.getId()).isPresent();
        } catch (JsonProcessingException exception) {
            throw new TechnicalException("Json Processing exception", exception.getMessage());
        }
    }
}
