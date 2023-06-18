package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RoleEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;
    private final PasswordEncoder passwordEncoder;

    public User create(String name, String email, String password, RoleEnum role) throws TechnicalFoundException {
        if (userAdapter.findByEmail(email).isEmpty())
            return userAdapter.save(
                    User.builder()
                            .name(name)
                            .email(email)
                            .password(passwordEncoder.encode(password))
                            .role(role)
                            .build());
        else
            throw new TechnicalFoundException("Un compte existe Déjà avec cet email :" + email);
    }

    public User login(String email, String password) throws TechnicalNotFoundException {
        User user = userAdapter.findByEmail(email)
                .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with email :" + email));
        if (user.getPassword().equals(password))
            return user;
        else
            throw new TechnicalNotFoundException(HttpStatus.BAD_REQUEST, "Incorrect password : " + password);
    }

    public List<User> getByName(Long id, String name) {
        return userAdapter.findByName(id, name);
    }

    public User getById(Long id) throws TechnicalNotFoundException {
        return userAdapter.findById(id)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No user found with following id : " + id));
    }

    public List<Lobby> getLobbiesByUser(User user) {
        return user.getParticipatingLobbies();
    }

    public User save(User user) {
        return userAdapter.save(user);
    }
}
