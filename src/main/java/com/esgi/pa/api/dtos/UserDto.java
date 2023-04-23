package com.esgi.pa.api.dtos;

import java.util.List;
import java.util.UUID;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UserDto(
    UUID id,
    String name,
    String email,
    String password,
    RoleEnum role,
    List<UserDto> friends
) {}
