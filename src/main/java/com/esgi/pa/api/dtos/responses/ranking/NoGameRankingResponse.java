package com.esgi.pa.api.dtos.responses.ranking;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête de récupération d'un ranking sur un jeu
 *
 * @param id    id numérique du ranking
 * @param user  utilisateur détenant ce ranking
 * @param score score de l'utilisateur sur le jeu
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record NoGameRankingResponse(Long id,
                                    NoFriendsUserResponse user,
                                    double score) {
}
