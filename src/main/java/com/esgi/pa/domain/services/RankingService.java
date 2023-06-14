package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Ranking;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RankingService {

    public List<Ranking> getGameGlobalRanking(Game game) {
        return game.getRankings();
    }

    public List<Ranking> getAllUserRankings(User user) {
        return user.getRankings();
    }

    public Ranking getRankingForUserOnGame(User user, Game game) throws TechnicalNotFoundException {
        return user.getRankings().stream()
                .filter(ranking -> ranking.getGame().equals(game))
                .findFirst()
                .orElseThrow(() -> new TechnicalNotFoundException(String.format("No ranking found for user %s on game %s.", user.getId(), game.getId())));
    }
}
