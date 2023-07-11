package com.esgi.pa.api.dtos.requests.move;

import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetLobbyRequest(@NotNull(message = "Lobby Id is required") Long lobby) {
}