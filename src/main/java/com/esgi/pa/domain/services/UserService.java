package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

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
        else throw new FunctionalException();
    }

    public User login(String email, String password) throws FunctionalException, TechnicalException {
        User user = adapter.findByEmail(email).orElseThrow(() -> new TechnicalException());
        if (user.getPassword().equals(password))
            return user;
        else throw new FunctionalException();
    }
}
