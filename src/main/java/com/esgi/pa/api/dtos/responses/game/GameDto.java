package com.esgi.pa.api.dtos.responses.game;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

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