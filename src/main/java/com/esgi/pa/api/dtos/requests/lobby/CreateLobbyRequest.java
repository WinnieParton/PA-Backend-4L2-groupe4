package com.esgi.pa.api.dtos.requests.lobby;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de création de lobby
 *
 * @param name      nom du lobby
 * @param user      créateur du lobby
 * @param game      jeu joué dans ce lobby
 * @param isPrivate visibilité du lobby
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record CreateLobbyRequest(
    @NotBlank(message = "Name is required") String name,
    @NotNull(message = "user Id is required") Long user,
    @NotNull(message = "game Id is required") Long game,
    boolean isPrivate) {
}