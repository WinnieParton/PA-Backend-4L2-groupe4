package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.*;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.RankingAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RankingService {

    private final RankingAdapter rankingAdapter;
    private final MoveService moveService;
    public List<Ranking> getGameGlobalRanking(Game game) {
        return rankingAdapter.findRankingGame(game);
    }

    public List<Ranking> getAllUserRankings(User user) {
        return rankingAdapter.findRankingPlayer(user);
    }

    public Ranking getRankingForUserOnGame(User user, Game game) throws TechnicalNotFoundException {
        return rankingAdapter.findByGameAndPlayer(game, user).orElseThrow(() -> new TechnicalNotFoundException(String.format("No ranking found for user %s on game %s.", user.getId(), game.getId())));

        /*user.getRankings().stream()
            .filter(ranking -> ranking.getGame().equals(game))
            .findFirst()
            .orElseThrow(() -> new TechnicalNotFoundException(String.format("No ranking found for user %s on game %s.", user.getId(), game.getId())));
   */ }

    public Optional<Ranking> getRankingForUserOnGameEnoughException(User user, Game game) {
        return rankingAdapter.findByGameAndPlayer(game, user);
    }

    public List<Ranking> getLobbyParticipantsRanking(Lobby lobby) {
        return lobby.getParticipants().stream()
            .flatMap(user -> user.getRankings().stream().filter(ranking -> lobby.getGame().equals(ranking.getGame())))
            .toList();
    }

    public List<Ranking> updateRankings(Lobby lobby, User winner, Map<Long, Double> scores) {
        for (User participant : lobby.getParticipants()) {
            Optional<Ranking> ranking = getRankingForUserOnGameEnoughException(participant, lobby.getGame());
            if(ranking.isEmpty())
                ranking = Optional.of(new Ranking(lobby.getGame(), participant));
            if (participant.equals(winner)) {
                rankingAdapter.save(
                    ranking.get()
                        .withScore(ranking.get().getScore() + scores.get(winner.getId()))
                        .withGamePlayed(ranking.get().getGamePlayed() + 1));
            } else {
                rankingAdapter.save(
                    ranking.get()
                        .withScore(ranking.get().getScore() + scores.get(participant.getId()))
                        .withGamePlayed(ranking.get().getGamePlayed() + 1));
            }
        }
        Optional<Move> move = moveService.findLastMove(lobby);
        move.get().setEndPart(Boolean.TRUE);
        moveService.saveEndMove(move.get());

        return getLobbyParticipantsRanking(lobby);
    }

    public Map<Long, Double> calculateScoresByPlayers(String scoresByPlayersRequest) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        TypeReference<List<List<Double>>> typeReference = new TypeReference<>() {
        };
        List<List<Double>> dataList = objectMapper.readValue(scoresByPlayersRequest, typeReference);

        Map<Long, Double> scoresByPlayers = new LinkedHashMap<>();
        for (List<Double> entry : dataList) {
            Long key = entry.get(0).longValue();
            Double value = entry.get(1);
            scoresByPlayers.put(key, value);
        }

        return scoresByPlayers;
    }
}
