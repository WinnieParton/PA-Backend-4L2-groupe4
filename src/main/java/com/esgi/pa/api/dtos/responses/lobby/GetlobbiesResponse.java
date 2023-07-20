package com.esgi.pa.api.dtos.responses.lobby;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des lobbies en cours
 *
 * @param lobbies liste des lobbies
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbiesResponse(List<GetlobbyResponse> lobbies) {
}
