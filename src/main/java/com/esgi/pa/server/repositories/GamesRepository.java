package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GamesRepository extends JpaRepository<Game, UUID> {
    
}
