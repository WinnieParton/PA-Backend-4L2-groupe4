package com.esgi.pa.api.dtos.requests;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record CreateLobbyRequest(
    String name,
    UUID user,
    UUID game,
    boolean isPrivate
) {}