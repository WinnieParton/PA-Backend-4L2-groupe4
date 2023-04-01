package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.esgi.pa.domain.entities.Game;

public interface GamesRepository extends MongoRepository<UUID, Game> {
    
}
