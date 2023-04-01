package com.esgi.pa.api.dtos;

import java.util.UUID;

import lombok.Builder;

@Builder
public record RankingDto(
    UUID id,
    GameDto game,
    UserDto player,
    double score
) {}
