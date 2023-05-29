package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.GamesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameAdapter implements PersistenceSpi<Game, Long> {

    private final GamesRepository gamesRepository;

    @Override
    public Game save(Game o) {
        return gamesRepository.save(o);
    }

    @Override
    public List<Game> saveAll(List<Game> oList) {
        for (Game game : oList) {
            gamesRepository.save(game);
        }
        return oList;
    }

    @Override
    public Optional<Game> findById(Long id) {
        return gamesRepository.findById(id);
    }

    @Override
    public List<Game> findAll() {
        return gamesRepository.findAll();
    }

    @Override
    public boolean removeById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

    @Override
    public boolean removeAll(List<Long> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }

    public Boolean findByName(String name) {
        return gamesRepository.findByName(name).isPresent();
    }
}
