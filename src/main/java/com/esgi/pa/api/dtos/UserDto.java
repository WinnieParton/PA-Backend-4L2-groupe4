package com.esgi.pa.api.dtos;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record UserDto(
    UUID id,
    String name,
    String email,
    RoleEnum role,
    List<NoFriendsUserDto> friends
) {
}
