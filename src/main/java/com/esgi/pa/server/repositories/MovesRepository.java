package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MovesRepository extends JpaRepository<Move, Long> {

    List<Move> findAllByLobby(Lobby lobby);
}
