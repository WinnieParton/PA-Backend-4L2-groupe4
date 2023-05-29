package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.adapter.GameAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameAdapter gameAdapter;

    public Game getById(UUID gameId) throws TechnicalException {
        return gameAdapter.findById(gameId)
            .orElseThrow(
                () -> new TechnicalException(HttpStatus.NOT_FOUND, "Cannot find game with id : "+ gameId));
    }

}
