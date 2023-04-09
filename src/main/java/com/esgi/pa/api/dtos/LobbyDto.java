package com.esgi.pa.api.dtos;

import java.time.LocalDateTime;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
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
