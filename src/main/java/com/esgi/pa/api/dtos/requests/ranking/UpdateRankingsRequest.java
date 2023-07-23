package com.esgi.pa.api.dtos.requests.ranking;

/**
 * DTO de requête de mise à jour des scores
 *
 * @param winnerId        id du gagnant de la partie
 * @param scoresByPlayers scores des joueurs
 */
public record UpdateRankingsRequest(Long winnerId, String scoresByPlayers) {
}
