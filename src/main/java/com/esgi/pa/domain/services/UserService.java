package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class UserService {
    
    private final UserAdapter adapter;

    public User create(String name, String email, String password, RoleEnum role) {
        return adapter.save(
            User.builder()
            .name(name)
            .email(email)
            .password(password)
            .role(role)
            .build()
        );
    }
}
