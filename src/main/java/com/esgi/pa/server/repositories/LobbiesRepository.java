package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Lobby;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface LobbiesRepository extends JpaRepository<Lobby, UUID> {
    
}
