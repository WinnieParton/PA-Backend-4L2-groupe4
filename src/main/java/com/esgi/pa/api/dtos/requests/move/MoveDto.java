package com.esgi.pa.api.dtos.requests.move;

import com.esgi.pa.api.dtos.responses.lobby.GetlobbyResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de transfère de données relative à l'état d'un jeu
 *
 * @param id        id numérique de l'état
 * @param lobby     id numérique du lobby
 * @param gameState état du jeu sous as JSON string
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record MoveDto(
    Long id,
    GetlobbyResponse lobby,
    String gameState) {
}
