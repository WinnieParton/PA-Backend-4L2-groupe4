package com.esgi.pa.api.dtos;

import java.util.UUID;

import lombok.Builder;

@Builder
public record MoveDto(
    UUID id,
    GameDto game,
    int turn,
    String gameInstructions
) {}
