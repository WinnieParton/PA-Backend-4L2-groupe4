package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter adapter;

    public User create(String name, String email, String password, RoleEnum role) throws TechnicalException {
        if (adapter.findByEmail(email).isEmpty())
            return adapter.save(
                    User.builder()
                            .name(name)
                            .email(email)
                            .password(password)
                            .role(role)
                            .build());
        else
            throw new TechnicalException(HttpStatus.FOUND, "Un compte existe Déjà avec cet email :" + email);
    }

    public User login(String email, String password) throws TechnicalException {
        User user = adapter.findByEmail(email)
                .orElseThrow(() -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with email :" + email));
        if (user.getPassword().equals(password))
            return user;
        else
            throw new TechnicalException(HttpStatus.BAD_REQUEST, "Incorrect password : " + password);
    }

    public List<User> getByName(String name) {
        return adapter.findByName(name);
    }

    public User getById(Long id) throws TechnicalException {
        return adapter.findById(id)
                .orElseThrow(
                        () -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
    }

    public List<Lobby> getLobbiesByUserId(Long id) throws TechnicalException {
        User user = adapter.findById(id)
                .orElseThrow(
                        () -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
        return user.getParticipatingLobbies();
    }
}
