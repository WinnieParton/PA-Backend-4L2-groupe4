package com.esgi.pa.api.dtos.responses.game;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO de réponse à une requête de récupération des fichiers de jeu
 *
 * @param language    langage de programmation du jeu
 * @param fileContent fichiers du jeu
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GetFileGameDtoResponse(String language, String fileContent ) {
}
