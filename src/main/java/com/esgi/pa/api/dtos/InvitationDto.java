package com.esgi.pa.api.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
    UUID id,
    UserDto user,
    LobbyDto lobby,
    boolean accepted
) {}
