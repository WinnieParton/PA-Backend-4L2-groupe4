package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.GamesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GameAdapter implements PersistenceSpi<Game, UUID> {
    
    private final GamesRepository gamesRepository;

    @Override
    public Game save(Game o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Game> saveAll(List<Game> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Game> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<List<Game>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    @Override
    public boolean removeById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeById'");
    }

    @Override
    public boolean removeAll(List<UUID> ids) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'removeAll'");
    }
    
}
