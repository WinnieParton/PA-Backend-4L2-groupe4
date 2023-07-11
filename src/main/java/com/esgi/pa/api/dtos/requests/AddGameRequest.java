package com.esgi.pa.api.dtos.requests;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AddGameRequest(
                @NotBlank(message = "Name is required") String name,
                String description,
                String gameFiles,
                @NotBlank(message = "Url image is required") String miniature,
                @Min(1) int minPlayers,
                int maxPlayers) {
}