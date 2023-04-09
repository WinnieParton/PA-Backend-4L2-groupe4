package com.esgi.pa.api.dtos;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record RankingDto(
    UUID id,
    GameDto game,
    UserDto player,
    double score
) {}
