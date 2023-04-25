package com.esgi.pa.api.dtos;

import com.esgi.pa.domain.enums.GameStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record LobbyDto(
    UUID id,
    String name,
    UUID creator,
    UUID game,
    boolean isPrivate,
    GameStatusEnum status,
    LocalDateTime createdAt,
    LocalDateTime updateAt

) {}
