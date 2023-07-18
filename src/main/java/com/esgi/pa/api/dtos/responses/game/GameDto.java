package com.esgi.pa.api.dtos.responses.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO contenant les informations lié à un jeu
 *
 * @param id          id numérique du jeu
 * @param name        nom du jeu
 * @param description description du jeu
 * @param gameFiles   fichiers du jeu
 * @param miniature   image miniature du jeu
 * @param minPlayers  nombre minimum de joueurs
 * @param maxPlayers  nombre maximum de joueurs
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record GameDto(
    Long id,
    String name,
    String description,
    String gameFiles,
    String miniature,
    int minPlayers,
    int maxPlayers) {
}