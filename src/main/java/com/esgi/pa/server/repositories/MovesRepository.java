package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Interface de persistence pour les étapes de jeu
 */
public interface MovesRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByLobby(Lobby lobby);

    Optional<Move> findFirstByLobbyAndMoveDateAfterOrderByMoveDateDesc(Lobby lobby, LocalDateTime date);
}
