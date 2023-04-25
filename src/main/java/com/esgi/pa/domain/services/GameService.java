package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.server.adapter.GameAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;

    public Game getById(UUID gameId) throws FunctionalException {
        return gameAdapter.findById(gameId)
            .orElseThrow(
                () -> new FunctionalException("Cannot find game with id : %s", gameId));
    }

}
