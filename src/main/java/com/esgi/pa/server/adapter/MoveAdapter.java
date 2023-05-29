package com.esgi.pa.server.adapter;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MovesRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class MoveAdapter implements PersistenceSpi<Move, UUID> {

    private final MovesRepository movesRepository;

    @Override
    public Move save(Move o) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'save'");
    }

    @Override
    public List<Move> saveAll(List<Move> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Move> findById(UUID id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }

    @Override
    public List<Move> findAll() {
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
