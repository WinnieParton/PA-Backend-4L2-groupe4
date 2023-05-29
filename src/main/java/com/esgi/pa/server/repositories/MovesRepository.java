package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Move;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface MovesRepository extends JpaRepository<Move, UUID> {
    
}
