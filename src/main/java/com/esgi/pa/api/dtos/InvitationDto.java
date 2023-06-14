package com.esgi.pa.api.dtos;

import com.esgi.pa.api.dtos.responses.LobbyInvitationResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
        Long id,
        LobbyInvitationResponse lobby,
        boolean accepted) {
}
