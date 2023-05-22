package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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
        else throw new TechnicalException("Un compte existe Déjà avec cet email : %s", email);
    }

    public User login(String email, String password) throws TechnicalException, FunctionalException {
        User user = adapter.findByEmail(email).orElseThrow(() -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with email %s :", email));
        if (user.getPassword().equals(password))
            return user;
        else throw new FunctionalException("Incorrect password : %s", password);
    }

    public User getByName(String name) throws TechnicalException {
        return adapter.findByName(name)
            .orElseThrow(
                () -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with following name : %s", name));
    }

    public User getById(UUID id) throws TechnicalException {
        return adapter.findById(id)
            .orElseThrow(
                () -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with following id : %s", id));
    }

    public List<Lobby> getLobbiesByUserId(UUID id) throws TechnicalException {
        User user = adapter.findById(id)
            .orElseThrow(
                () -> new TechnicalException(HttpStatus.NOT_FOUND, "No user found with following id : %s", id));
        return user.getParticipatingLobbies();
    }
}
