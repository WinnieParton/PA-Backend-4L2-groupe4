package com.esgi.pa.api.dtos;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record FriendDto(
    UUID id,
    UUID user,
    UserDto friend,
    RequestStatus status
) {}
