package com.esgi.pa.domain.services;

import static java.lang.String.format;

import com.esgi.pa.domain.entities.*;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.RankingAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * Service de gestion des rankings
 */
@Service
@RequiredArgsConstructor
public class RankingService {

  private final RankingAdapter rankingAdapter;
  private final MoveService moveService;

  /**
   * Récupère l'ensemble des rankings pour un jeu donné
   *
   * @param game jeu dont on veut récupérer les rankings
   * @return liste des rankings
   */
  public List<Ranking> getGameGlobalRanking(Game game) {
    return rankingAdapter.findRankingGame(game);
  }

  /**
   * Récupère l'ensemble des rankings d'un utilisateur
   *
   * @param user utilisateur dont on veut récupérer les rankings
   * @return liste des rankings de l'utilisateur
   */
  public List<Ranking> getAllUserRankings(User user) {
    return rankingAdapter.findRankingPlayer(user);
  }

  /**
   * Récupère le ranking d'un utilisateur sur un jeu spécifique
   *
   * @param user utilisateur dont on veut le rang
   * @param game jeu sur lequel on veut le rang
   * @return rang de l'utilisateur sur le jeu
   * @throws TechnicalNotFoundException si un élément n'est pas trouvé
   */
  public Ranking getRankingForUserOnGame(User user, Game game)
    throws TechnicalNotFoundException {
    return rankingAdapter
      .findByGameAndPlayer(game, user)
      .orElseThrow(() ->
        new TechnicalNotFoundException(
          format(
            "No ranking found for user %s on game %s.",
            user.getId(),
            game.getId()
          )
        )
      );
  }

  /**
   * Récupère la liste des participants à un lobby
   *
   * @param lobby le lobby dont on veut les participants
   * @return liste des participants au lobby
   */
  public List<Ranking> getLobbyParticipantsRanking(Lobby lobby) {
    return lobby
      .getParticipants()
      .stream()
      .flatMap(user ->
        user
          .getRankings()
          .stream()
          .filter(ranking -> lobby.getGame().equals(ranking.getGame()))
      )
      .toList();
  }

  /**
   * Met à jour les rangs des utilisateur connecté au lobby
   *
   * @param lobby  lobby auquel appartiennent les joueur
   * @param winner le gagnant de la partie
   * @param scoresByPlayersRequest les scores des joueurs
   * @return la liste des nouveaux rankings des joueurs connecté au lobby
   */
  @Async
  public CompletableFuture<List<Ranking>> updateRankingsAsync(
    Lobby lobby,
    User winner,
    String scoresByPlayersRequest
  ) {
    List<Move> moves = moveService.findListLastMove(lobby);
    for (Move move : moves) {
      move.setEndPart(Boolean.TRUE);
      moveService.saveEndMove(move);
    }
    try {
      Map<Long, Double> scores = calculateScoresByPlayers(
        scoresByPlayersRequest
      )
        .get();
      lobby
        .getParticipants()
        .stream()
        .toList()
        .forEach(participant -> {
          if (participant.equals(winner)) {
            Optional<Ranking> ranking = rankingAdapter.findByGameAndPlayer(
              lobby.getGame(),
              participant
            );
            if (ranking.isEmpty()) {
              ranking = Optional.of(new Ranking(lobby.getGame(), participant));
            }
            rankingAdapter.save(
              ranking
                .get()
                .withScore(
                  ranking.get().getScore() + scores.get(winner.getId())
                )
                .withGamePlayed(ranking.get().getGamePlayed() + 1)
            );
          } else {
            Optional<Ranking> ranking = rankingAdapter.findByGameAndPlayer(
              lobby.getGame(),
              participant
            );
            if (ranking.isEmpty()) {
              ranking = Optional.of(new Ranking(lobby.getGame(), participant));
            }
            rankingAdapter.save(
              ranking
                .get()
                .withScore(
                  ranking.get().getScore() + scores.get(participant.getId())
                )
                .withGamePlayed(ranking.get().getGamePlayed() + 1)
            );
          }
        });
    } catch (
      JsonProcessingException | ExecutionException | InterruptedException e
    ) {
      throw new RuntimeException(e);
    }

    List<Ranking> rankings = getLobbyParticipantsRanking(lobby);
    return CompletableFuture.completedFuture(rankings);
  }

  public List<Ranking> updateRankings(
    Lobby lobby,
    User user,
    String scoresByPlayersRequest
  ) throws ExecutionException, InterruptedException {
    return updateRankingsAsync(lobby, user, scoresByPlayersRequest).get();
  }

  /**
   * Calcule les scores de chaque player
   *
   * @param scoresByPlayersRequest JSON des scores de chaques joueurs
   * @return les scores de chaque joueur sous forme d'une map <idjoueur, score>
   * @throws JsonProcessingException si problème avec la lecture du JSON
   */
  @Async
  public CompletableFuture<Map<Long, Double>> calculateScoresByPlayers(
    String scoresByPlayersRequest
  ) throws JsonProcessingException {
    ObjectMapper objectMapper = new ObjectMapper();
    TypeReference<List<List<Double>>> typeReference = new TypeReference<>() {};
    List<List<Double>> dataList = objectMapper.readValue(
      scoresByPlayersRequest,
      typeReference
    );
    Map<Long, Double> scoresByPlayers = new LinkedHashMap<>();
    for (List<Double> entry : dataList) {
      Long key = entry.get(0).longValue();
      Double value = entry.get(1);
      scoresByPlayers.put(key, value);
    }

    return CompletableFuture.completedFuture(scoresByPlayers);
  }
}
