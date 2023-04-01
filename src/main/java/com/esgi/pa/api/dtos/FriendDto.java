package com.esgi.pa.api.dtos;

import lombok.Builder;

@Builder
public record FriendDto(
    UserDto user,
    boolean accepted
) {}
