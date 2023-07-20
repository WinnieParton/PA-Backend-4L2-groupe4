package com.esgi.pa.api.dtos.requests.move;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de récupération d'un lobby
 *
 * @param lobby id numérique du lobby
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record GetLobbyRequest(@NotNull(message = "Lobby Id is required") Long lobby) {
}