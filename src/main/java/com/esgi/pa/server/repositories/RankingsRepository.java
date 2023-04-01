package com.esgi.pa.server.repositories;

import java.util.UUID;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.esgi.pa.domain.entities.Ranking;

public interface RankingsRepository extends MongoRepository<UUID, Ranking> {
    
}
