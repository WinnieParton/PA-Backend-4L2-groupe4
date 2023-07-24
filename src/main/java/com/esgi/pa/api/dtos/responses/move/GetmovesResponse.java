package com.esgi.pa.api.dtos.responses.move;

import com.esgi.pa.api.dtos.requests.move.MoveDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des moves en cours
 *
 * @param moves liste des lobbies
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GetmovesResponse(List<GetMoveResponse> moves) {
}
