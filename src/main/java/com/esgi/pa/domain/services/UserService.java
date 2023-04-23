package com.esgi.pa.domain.services;

import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.FunctionalException;
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

    public User login(String email, String password) throws FunctionalException {
        User user = adapter.findByEmail(email).orElseThrow(() -> new FunctionalException("No user found with email %s :", email));
        if (user.getPassword().equals(password))
            return user;
        else throw new FunctionalException("Incorrect password : %s", password);
    }

    public User getByName(String name) throws FunctionalException {
        return adapter.findByName(name)
            .orElseThrow(
                () -> new FunctionalException("No user found with following name : %s", name));
    }

    public User getById(UUID id) throws FunctionalException {
        return adapter.findById(id)
        .orElseThrow(
            () -> new FunctionalException("No user found with following id : %s", id));
    }
}
