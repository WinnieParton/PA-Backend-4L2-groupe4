package com.esgi.pa.api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record CreateLobbyRequest(
    @NotBlank(message = "Name is required")
    String name,
    @NotBlank(message = "UUID user is required")
    UUID user,
    @NotBlank(message = "UUID game is required")
    UUID game,
    boolean isPrivate
) {}