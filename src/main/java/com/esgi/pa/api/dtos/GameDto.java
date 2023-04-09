package com.esgi.pa.api.dtos;

import java.util.UUID;

import com.esgi.pa.domain.enums.GameStatusEnum;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GameDto(
    UUID id,
    String name,
    String description,
    String gameFiles,
    String miniature,
    int minPlayers,
    int maxPlayers,
    GameStatusEnum status
) {}