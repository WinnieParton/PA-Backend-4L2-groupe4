package com.esgi.pa.api.dtos.responses.lobby;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des messages d'un lobby
 *
 * @param id           id numérique du lobby
 * @param participants participants du lobby
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbyMessageResponse(
    Long id,
    List<NoFriendsUserResponse> participants) {
}
