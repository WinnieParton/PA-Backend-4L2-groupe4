package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Ranking;
import com.esgi.pa.server.PersistenceSpi;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class RankingMongoAdapter implements PersistenceSpi<UUID, Ranking> {
    
    @Override
    public UUID save(UUID o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<UUID> saveInBatch(List<UUID> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveInBatch'");
    }

    @Override
    public Optional<UUID> findById(Ranking id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public Optional<List<UUID>> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    
}
