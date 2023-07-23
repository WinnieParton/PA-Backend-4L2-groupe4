package com.esgi.pa.api.dtos.responses.ranking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête de récupération des rankings sur un jeu spécifique
 *
 * @param globalRanking liste de l'ensemble des rankings d'un jeu
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record GlobalRankingResponse(List<NoGameRankingResponse> globalRanking) {
}
