package com.esgi.pa.api.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record MoveDto(
    UUID id,
    GameDto game,
    int turn,
    String gameInstructions
) {}
