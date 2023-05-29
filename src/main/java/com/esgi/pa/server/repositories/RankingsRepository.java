package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Ranking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface RankingsRepository extends JpaRepository<Ranking, UUID> {
    
}
