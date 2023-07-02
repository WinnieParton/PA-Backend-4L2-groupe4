package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Ranking;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.RankingAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingAdapter rankingAdapter;

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

    public List<Ranking> getLobbyParticipantsRanking(Lobby lobby) {
        return lobby.getParticipants().stream()
            .flatMap(user -> user.getRankings().stream().filter(ranking -> lobby.getGame().equals(ranking.getGame())))
            .toList();
    }

    public List<Ranking> updateRankings(Lobby lobby, User winner, Map<Long, Double> scores) throws TechnicalNotFoundException {
        for (User participant : lobby.getParticipants()) {
            Ranking ranking = getRankingForUserOnGame(participant, lobby.getGame());
            if (participant.equals(winner)) {
                rankingAdapter.save(
                    ranking
                        .withScore(ranking.getScore() + scores.get(winner.getId()))
                        .withGamePlayed(ranking.getGamePlayed() + 1));
            } else {
                rankingAdapter.save(
                    ranking
                        .withScore(ranking.getScore() + scores.get(participant.getId()))
                        .withGamePlayed(ranking.getGamePlayed() + 1));
            }
        }
        return getLobbyParticipantsRanking(lobby);
    }
}
