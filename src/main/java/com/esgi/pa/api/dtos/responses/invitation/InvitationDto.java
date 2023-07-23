package com.esgi.pa.api.dtos.responses.invitation;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO contenant les informations relative à une invitation à un lobby
 *
 * @param id       id numérique de l'invitation
 * @param lobby    informations sur le lobby
 * @param accepted statut de l'invitation
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
    Long id,
    LobbyInvitationResponse lobby,
    RequestStatus accepted) {
}
