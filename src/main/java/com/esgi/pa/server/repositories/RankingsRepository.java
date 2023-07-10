package com.esgi.pa.server.repositories;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import com.esgi.pa.domain.entities.Ranking;

import java.util.List;
import java.util.Optional;

public interface RankingsRepository extends JpaRepository<Ranking, Long> {
    Optional<Ranking> findByGameAndPlayer(Game game, User user);
    List<Ranking> findByPlayerOrderByScoreDesc(User user);
    List<Ranking> findByGameOrderByScoreDesc(Game game);
}
