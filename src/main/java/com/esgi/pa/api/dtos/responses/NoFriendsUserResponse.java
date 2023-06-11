package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RoleEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record NoFriendsUserResponse(UUID id,
                               String name,
                               String email,
                               RoleEnum role) {
}
