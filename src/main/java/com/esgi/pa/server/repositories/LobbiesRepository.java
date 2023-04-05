package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Lobby;

public interface LobbiesRepository extends JpaRepository<Lobby, UUID> {
    
}
