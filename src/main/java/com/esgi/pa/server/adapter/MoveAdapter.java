package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MovesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

/**
 * Adapter de persistence pour les étapes de jeu
 */
@Service
@RequiredArgsConstructor
public class MoveAdapter implements PersistenceSpi<Move, Long> {

    private final MovesRepository movesRepository;

    @Override
    public Move save(Move o) {
        return movesRepository.save(o.withMoveDate(LocalDateTime.now()));
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

    public Optional<Move> findByLobbyActionOutput(Lobby lobby) {
        return movesRepository.findFirstByLobbyAndActionEnumOrderByIdDesc(lobby, ActionEnum.OUTPUT);
    }

    public List<Move> findByListLobbyActionInput(Lobby lobby) {
        return movesRepository.findByLobbyAndEndPartFalseAndActionEnumOrderByIdAsc(lobby, ActionEnum.INPUT);
    }

    public List<Move> findListLastMoveALobby(Lobby lobby) {
        return movesRepository.findByLobbyAndEndPartFalseOrderByMoveDateDesc(lobby);
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
