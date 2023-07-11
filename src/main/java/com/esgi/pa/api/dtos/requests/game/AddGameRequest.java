package com.esgi.pa.api.dtos.requests.game;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AddGameRequest(
        @NotBlank(message = "Name is required") String name,
        String description,
        @NotNull(message = "File game is required") MultipartFile gameFiles,
        @NotBlank(message = "URL image is required") String miniature,
        @Min(1) int minPlayers,
        int maxPlayers) {
}