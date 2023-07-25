package com.esgi.pa.api.dtos.responses.lobby;

import java.time.LocalDateTime;
import java.util.List;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * DTO de réponse à une requête d'informations concernant un lobby
 *
 * @param id           id numérique du lobby
 * @param name         nom du lobby
 * @param creator      nom du créateur du lobby
 * @param game         jeu du lobby
 * @param isPrivate    visibilité du lobby
 * @param status       statut du lobby
 * @param createdAt    date de création du lobby
 * @param updateAt     date de modification du lobby
 * @param participants participants au lobby
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbyResponse(
    Long id,
    String name,
    NoFriendsUserResponse creator,
    GameDto game,
    boolean isPrivate,
    LocalDateTime createdAt,
    LocalDateTime updateAt,
    List<NoFriendsUserResponse> participants) {
}
