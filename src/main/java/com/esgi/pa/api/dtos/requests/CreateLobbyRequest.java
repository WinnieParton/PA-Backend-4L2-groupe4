package com.esgi.pa.api.dtos.requests;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record CreateLobbyRequest(
        @NotBlank(message = "Name is required") String name,
        @NotBlank(message = "user Id is required") Long user,
        @NotBlank(message = "game Id is required") Long game,
        boolean isPrivate) {
}