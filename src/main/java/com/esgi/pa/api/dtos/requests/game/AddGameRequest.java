package com.esgi.pa.api.dtos.requests.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête d'ajout de jeu
 *
 * @param name        nom du jeu
 * @param description description du jeu
 * @param gameFiles   fichiers du jeu
 * @param miniature   image miniature du jeu
 * @param minPlayers  nombre minimum de joueurs
 * @param maxPlayers  nombre maximum de joueurs
 */
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