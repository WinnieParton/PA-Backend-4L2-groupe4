package com.esgi.pa.api.dtos;

import com.esgi.pa.api.dtos.responses.lobby.GetlobbyResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record MoveDto(
    Long id,
    GetlobbyResponse lobby,
    String gameState) {
}
