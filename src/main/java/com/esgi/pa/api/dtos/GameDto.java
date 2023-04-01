package com.esgi.pa.api.dtos;

import java.util.UUID;

import com.esgi.pa.domain.enums.GameStatusEnum;

import lombok.Builder;

@Builder
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