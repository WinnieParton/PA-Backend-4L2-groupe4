package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.UserAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserAdapter userAdapter;

    public List<User> getByNameAndId(Long id, String name) {
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
