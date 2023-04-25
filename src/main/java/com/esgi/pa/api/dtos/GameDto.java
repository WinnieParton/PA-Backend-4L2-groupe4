package com.esgi.pa.api.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GameDto(
    UUID id,
    String name,
    String description,
    String gameFiles,
    String miniature,
    int minPlayers,
    int maxPlayers
) {}