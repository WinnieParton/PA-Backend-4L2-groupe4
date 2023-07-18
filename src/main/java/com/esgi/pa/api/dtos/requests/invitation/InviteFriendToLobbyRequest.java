package com.esgi.pa.api.dtos.requests.invitation;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête d'invitation d'un ami à un lobby
 *
 * @param friendId id numérique de l'ami
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record InviteFriendToLobbyRequest(Long friendId) {
}
