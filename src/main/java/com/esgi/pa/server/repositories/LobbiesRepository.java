package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Interface de persistence pour les lobbies
 */
public interface LobbiesRepository extends JpaRepository<Lobby, Long> {
    List<Lobby> findByCreator(User user);
}
