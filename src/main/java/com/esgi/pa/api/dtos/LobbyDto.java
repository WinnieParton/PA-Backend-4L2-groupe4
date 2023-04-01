package com.esgi.pa.api.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import lombok.Builder;

@Builder
public record LobbyDto(
    UUID id,
    String name,
    UserDto creator,
    GameDto game,
    boolean privacySetting,
    boolean status,
    LocalDateTime createdAt,
    LocalDateTime updateAt

) {}
