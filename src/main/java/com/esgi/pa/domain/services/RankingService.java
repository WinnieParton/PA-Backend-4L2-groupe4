package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.*;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.RankingAdapter;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static java.lang.String.format;

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
    public Ranking getRankingForUserOnGame(User user, Game game) throws TechnicalNotFoundException {
        return rankingAdapter.findByGameAndPlayer(game, user)
            .orElseThrow(() -> new TechnicalNotFoundException(format("No ranking found for user %s on game %s.", user.getId(), game.getId())));
    }

    /**
     * Récupère un Optional du rang d'un utilisateur sur un jeu
     *
     * @param user utilisateur dont on veut le rang
     * @param game jeu dont on veut le rang
     * @return un Optional du rang recherché
     */
    public Optional<Ranking> getRankingForUserOnGameEnoughException(User user, Game game) {
        return rankingAdapter.findByGameAndPlayer(game, user);
    }

    /**
     * Récupère la liste des participants à un lobby
     *
     * @param lobby le lobby dont on veut les participants
     * @return liste des participants au lobby
     */
    public List<Ranking> getLobbyParticipantsRanking(Lobby lobby) {
        return lobby.getParticipants().stream()
            .flatMap(user -> user.getRankings().stream().filter(ranking -> lobby.getGame().equals(ranking.getGame())))
            .toList();
    }

    /**
     * Met à jour les rangs des utilisateur connecté au lobby
     *
     * @param lobby  lobby auquel appartiennent les joueur
     * @param winner le gagnant de la partie
     * @param scores les scores des joueurs
     * @return la liste des nouveaux rankings des joueurs connecté au lobby
     */
    public List<Ranking> updateRankings(Lobby lobby, User winner, Map<Long, Double> scores) throws TechnicalNotFoundException {
        for (User participant : lobby.getParticipants()) {
            Optional<Ranking> ranking = getRankingForUserOnGameEnoughException(participant, lobby.getGame());
            if (ranking.isEmpty())
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
        Move move = moveService.findLastMove(lobby)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, format("move not found with lobby id: %s", lobby.getId())));
        moveService.saveEndMove(move.withEndPart(Boolean.TRUE));

        return getLobbyParticipantsRanking(lobby);
    }

    /**
     * Calcule les scores de chaque player
     *
     * @param scoresByPlayersRequest JSON des scores de chaques joueurs
     * @return les scores de chaque joueur sous forme d'une map <idjoueur, score>
     * @throws JsonProcessingException si problème avec la lecture du JSON
     */
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
