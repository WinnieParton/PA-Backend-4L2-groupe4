package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.LobbyAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final LobbyAdapter lobbyAdapter;

    public Lobby create(String name, User user, Game game, boolean isPrivate) {
        return lobbyAdapter.save(
                Lobby.builder()
                        .name(name)
                        .creator(user)
                        .game(game)
                        .isPrivate(isPrivate)
                        .participants(List.of(user))
                        .status(GameStatusEnum.PENDING)
                        .build());
    }

    public Lobby findOne(Long id) throws TechnicalException {
        return lobbyAdapter.findById(id)
                .orElseThrow(() -> new TechnicalException(HttpStatus.NOT_FOUND, "No Lobby found with id : " + id));
    }

    public List<Lobby> findAll() {
        return lobbyAdapter.findAll();
    }
}