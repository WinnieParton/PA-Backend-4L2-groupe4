package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GamesRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);
}
