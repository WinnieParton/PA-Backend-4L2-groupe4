package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.UserAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter adapter;

    public User create(String name, String email, String password, RoleEnum role) throws TechnicalFoundException {
        if (adapter.findByEmail(email).isEmpty())
            return adapter.save(
                    User.builder()
                            .name(name)
                            .email(email)
                            .password(password)
                            .role(role)
                            .build());
        else
            throw new TechnicalFoundException( "Un compte existe Déjà avec cet email :" + email);
    }

    public User login(String email, String password) throws TechnicalNotFoundException {
        User user = adapter.findByEmail(email)
                .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with email :" + email));
        if (user.getPassword().equals(password))
            return user;
        else
            throw new TechnicalNotFoundException(HttpStatus.BAD_REQUEST, "Incorrect password : " + password);
    }

    public List<User> getByName(Long id, String name) {
        return adapter.findByName(id, name);
    }

    public User getById(Long id) throws TechnicalNotFoundException {
        return adapter.findById(id)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
    }

    public List<Lobby> getLobbiesByUserId(Long id) throws TechnicalNotFoundException {
        User user = adapter.findById(id)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
        return user.getParticipatingLobbies();
    }
}
