package com.esgi.pa.domain.services;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {
    
    private final UserAdapter adapter;

    public User create(String name, String email, String password, RoleEnum role) throws FunctionalException {
        if(!adapter.findByEmail(email).isPresent())
            return adapter.save(
                User.builder()
                .name(name)
                .email(email)
                .password(password)
                .role(role)
                .build());
        else throw new FunctionalException("Un compte existe Déjà avec cet email : %s", email);
    }

    public User login(String email, String password) throws FunctionalException, TechnicalException {
        User user = adapter.findByEmail(email).orElseThrow(() -> new TechnicalException("No user found with email %s :", email));
        if (user.getPassword().equals(password))
            return user;
        else throw new FunctionalException("Incorrect password : %s", password);
    }
}
