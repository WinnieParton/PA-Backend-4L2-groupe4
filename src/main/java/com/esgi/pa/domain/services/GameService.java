package com.esgi.pa.domain.services;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.GameAdapter;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;

    public Game getById(Long gameId) throws TechnicalNotFoundException {
        return gameAdapter.findById(gameId)
                .orElseThrow(
                        () -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "Cannot find game with id : " + gameId));
    }

    public Game createGame(Game game) throws TechnicalFoundException {
        if (gameAdapter.findByName(game.getName()))
            throw new TechnicalFoundException("A game using this name already exist : " + game.getName());
        return gameAdapter.save(game);
    }

    public List<Game> findAll() {
        return gameAdapter.findAll();
    }

}
