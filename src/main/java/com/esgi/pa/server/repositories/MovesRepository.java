package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface de persistence pour les étapes de jeu
 */
public interface MovesRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByLobby(Lobby lobby);
    Optional<Move> findFirstByLobbyAndActionEnumOrderByIdDesc(Lobby lobby, ActionEnum actionEnum);
    List<Move> findByLobbyAndEndPartFalseAndActionEnumOrderByIdAsc(Lobby lobby, ActionEnum actionEnum);
    List<Move> findByLobbyAndEndPartFalseOrderByMoveDateDesc(Lobby lobby);
}
