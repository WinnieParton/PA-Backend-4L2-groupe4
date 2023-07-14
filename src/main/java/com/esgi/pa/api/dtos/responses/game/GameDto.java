package com.esgi.pa.api.dtos.responses.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GameDto(
        Long id,
        String name,
        String description,
        String gameFiles,
        String miniature,
        int minPlayers,
        int maxPlayers) {
}