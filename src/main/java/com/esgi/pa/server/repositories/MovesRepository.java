package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.enums.RollbackEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/**
 * Interface de persistence pour les étapes de jeu
 */
public interface MovesRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByLobby(Lobby lobby);
    Optional<Move> findFirstByLobbyAndActionEnumAndRollbackNotOrderByIdDesc(Lobby lobby, ActionEnum actionEnum, RollbackEnum rollbackEnum);
    List<Move> findByLobbyAndEndPartFalseAndActionEnumAndRollbackNotOrderByIdAsc(Lobby lobby, ActionEnum actionEnum, RollbackEnum rollbackEnum);
    List<Move> findByLobbyAndEndPartFalseOrderByMoveDateDesc(Lobby lobby);
    List<Move> findByRollback(RollbackEnum rollback);
    List<Move> findByIdGreaterThanOrderByIdAsc(Long moveId);
}
