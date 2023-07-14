package com.esgi.pa.api.dtos.requests.lobby;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record CreateLobbyRequest(
        @NotBlank(message = "Name is required") String name,
        @NotNull(message = "user Id is required") Long user,
        @NotNull(message = "game Id is required") Long game,
        boolean isPrivate) {
}