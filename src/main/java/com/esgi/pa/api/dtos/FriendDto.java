package com.esgi.pa.api.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.UUID;

import com.esgi.pa.domain.enums.FriendRequestStatus;

@JsonAutoDetect(fieldVisibility = ANY)
public record FriendDto(
    UUID id,
    UserDto user,
    UserDto friend,
    FriendRequestStatus status
) {}
