package com.esgi.pa.server.repositories;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Game;

public interface GamesRepository extends JpaRepository<Game, Long> {
    Optional<Game> findByName(String name);
}
