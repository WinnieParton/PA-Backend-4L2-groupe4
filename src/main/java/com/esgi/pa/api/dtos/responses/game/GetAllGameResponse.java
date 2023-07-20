package com.esgi.pa.api.dtos.responses.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des jeux disponible
 *
 * @param games liste des jeux disponible
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetAllGameResponse(List<GameDto> games) {
}
