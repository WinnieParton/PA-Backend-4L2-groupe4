package com.esgi.pa.api.dtos;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record InvitationDto(
    UUID id,
    UUID user,
    UUID lobby,
    boolean accepted
) {
}
