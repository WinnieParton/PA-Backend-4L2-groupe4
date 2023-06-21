package com.esgi.pa.api.dtos;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
        Long id,
        Long user,
        Long lobby,
        boolean accepted) {
}
