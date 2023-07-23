package com.esgi.pa.api.dtos.responses.ranking;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO contenant les informations relative à une ranking
 *
 * @param id     id numérique du ranking
 * @param game   jeu sur lequel est le ranking
 * @param player utilisateur détenant ce rang
 * @param score  score de l'utilisateur
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record RankingDto(
    Long id,
    GameDto game,
    NoFriendsUserResponse player,
    double score) {
}
