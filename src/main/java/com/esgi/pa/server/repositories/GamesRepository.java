package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Game;

public interface GamesRepository extends JpaRepository<Game, UUID> {
    
}
