package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MovesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MoveAdapter implements PersistenceSpi<Move, Long> {

    private final MovesRepository movesRepository;

    @Override
    public Move save(Move o) {
        o.setMoveDate(LocalDateTime.now());
        return movesRepository.save(o);
    }

    @Override
    public List<Move> saveAll(List<Move> oList) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'saveAll'");
    }

    @Override
    public Optional<Move> findById(Long id) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findById'");
    }
    public Optional<Move> findByLobby(Lobby lobby) {
        LocalDateTime oneMinuteAgo = LocalDateTime.now().minus(1, ChronoUnit.MINUTES);
        return movesRepository.findFirstByLobbyAndMoveDateAfterOrderByMoveDateDesc(lobby, oneMinuteAgo);
    }
    public Optional<Move> findByLobbyMove(Lobby lobby) {
        return movesRepository.findFirstByLobbyOrderByMoveDateDesc(lobby);
    }
    @Override
    public List<Move> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }

    public List<Move> findAllByLobby(Lobby lobby) {
        return movesRepository.findAllByLobby(lobby);
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

}
