package com.esgi.pa.api.dtos;

import java.util.List;
import java.util.UUID;

import com.esgi.pa.domain.enums.RoleEnum;

import lombok.Builder;

@Builder
public record UserDto(
    UUID id,
    String name,
    String email,
    String password,
    RoleEnum role,
    List<FriendDto> friends
) {}
