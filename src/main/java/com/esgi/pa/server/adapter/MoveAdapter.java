package com.esgi.pa.server.adapter;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.enums.RollbackEnum;
import com.esgi.pa.server.PersistenceSpi;
import com.esgi.pa.server.repositories.MovesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
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
        return  movesRepository.findById(id);
    }

    public Optional<Move> findByLobbyActionOutput(Lobby lobby) {
        return movesRepository.findFirstByLobbyAndActionEnumAndRollbackNotOrderByIdDesc(lobby, ActionEnum.OUTPUT, RollbackEnum.POP);
    }

    public List<Move> findByListLobbyActionInput(Lobby lobby) {
        return movesRepository.findByLobbyAndEndPartFalseAndActionEnumAndRollbackNotOrderByIdAsc(lobby, ActionEnum.INPUT, RollbackEnum.POP);
    }
    public List<Move> listMovePop(Long lobby) {
        return movesRepository.findByIdGreaterThanOrderByIdAsc(lobby);
    }
    public List<Move> findListLastMoveALobby(Lobby lobby) {
        return movesRepository.findByLobbyAndEndPartFalseOrderByMoveDateDesc(lobby);
    }
    @Override
    public List<Move> findAll() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'findAll'");
    }
    public List<Move> findByListLobbyLastActionInput(Lobby lobby) {
        return movesRepository.findByLobbyAndEndPartFalseAndActionEnumAndRollbackNotOrderByIdAsc(lobby, ActionEnum.INPUT, RollbackEnum.POP);
    }
    public List<Move> findAllByLobby(Lobby lobby) {
        return movesRepository.findAllByLobby(lobby);
    }
    public List<Move> listMoveRollBackPending() {
        return movesRepository.findByRollback(RollbackEnum.PENDING);
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
