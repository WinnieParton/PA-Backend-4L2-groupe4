package com.esgi.pa.api.dtos.responses.move;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO de réponse à une requête de récupération des moves en cours
 *
 * @param moves liste des lobbies
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GetMoveResponse( Long id, String gameState){}