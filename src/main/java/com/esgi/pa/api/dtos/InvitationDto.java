package com.esgi.pa.api.dtos;

import java.util.UUID;

import lombok.Builder;

@Builder
public record InvitationDto(
    UUID id,
    UserDto user,
    LobbyDto lobby,
    boolean accepted
) {}
