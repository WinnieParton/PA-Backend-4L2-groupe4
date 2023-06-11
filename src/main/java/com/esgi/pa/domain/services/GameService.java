package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.GameAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;

    public Game getById(Long gameId) throws TechnicalNotFoundException {
        return gameAdapter.findById(gameId)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Cannot find game with id : " + gameId));
    }

    public Game createGame(String name, String description, String gameFiles, String miniature, int minPlayers, int maxPlayers) throws TechnicalFoundException {
        if (gameAdapter.findByName(name))
            throw new TechnicalFoundException("A game using this name already exist : " + name);
        return gameAdapter.save(
                Game.builder()
                        .name(name)
                        .description(description)
                        .gameFiles(gameFiles)
                        .miniature(miniature)
                        .minPlayers(minPlayers)
                        .maxPlayers(maxPlayers)
                        .build());
    }

    public List<Game> findAll() {
        return gameAdapter.findAll();
    }

}
