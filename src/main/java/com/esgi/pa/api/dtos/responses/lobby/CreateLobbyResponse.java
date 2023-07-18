package com.esgi.pa.api.dtos.responses.lobby;

import java.time.LocalDateTime;

/**
 * DTO de réponse à une requête de création de lobby
 *
 * @param id        id du lobby
 * @param createdAt date de création du lobby
 */
public record CreateLobbyResponse(
    Long id,
    LocalDateTime createdAt) {

}
