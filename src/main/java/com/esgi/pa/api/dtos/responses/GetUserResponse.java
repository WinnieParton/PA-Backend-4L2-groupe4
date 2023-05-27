package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetUserResponse(
    UUID id,
    String name,
    String email,
    RoleEnum role,
    List<NoFriendsUserResponse> friends
) {
}
