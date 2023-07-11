package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.GameStatusEnum;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface MovesRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByLobby(Lobby lobby);
    Optional<Move> findFirstByLobbyAndMoveDateAfterOrderByMoveDateDesc(Lobby lobby, LocalDateTime date);}
