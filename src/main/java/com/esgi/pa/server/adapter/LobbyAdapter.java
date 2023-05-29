package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.LobbiesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class LobbyAdapter implements PersistenceSpi<Lobby, UUID> {
    
    private final LobbiesRepository lobbiesRepository;
    
    @Override
    public Lobby save(Lobby o) {
        return lobbiesRepository.save(o);
    }

    @Override
    public List<Lobby> saveAll(List<Lobby> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Lobby> findById(UUID id) {
        return lobbiesRepository.findById(id);
    }

    @Override
    public List<Lobby> findAll() {
        return lobbiesRepository.findAll();
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
