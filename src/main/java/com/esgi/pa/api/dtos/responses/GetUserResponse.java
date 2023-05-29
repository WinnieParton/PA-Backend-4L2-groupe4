package com.esgi.pa.api.dtos.responses;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.List;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetUserResponse(
        Long id,
        String name,
        String email,
        RoleEnum role,
        List<NoFriendsUserResponse> friends) {
}
