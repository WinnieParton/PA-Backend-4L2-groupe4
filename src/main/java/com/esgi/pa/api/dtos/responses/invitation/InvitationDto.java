package com.esgi.pa.api.dtos.responses.invitation;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
        Long id,
        LobbyInvitationResponse lobby,
        RequestStatus accepted) {
}
