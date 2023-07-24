package com.esgi.pa.api.dtos.responses.move;

import com.esgi.pa.api.dtos.requests.move.MoveDto;
import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO de réponse à une requête de récupération des moves en cours
 *
 * @param moves liste des lobbies
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY)
public record GetMoveResponse( Long id, String gameState){}