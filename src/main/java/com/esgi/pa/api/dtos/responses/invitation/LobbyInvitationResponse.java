package com.esgi.pa.api.dtos.responses.invitation;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;

/**
 * DTO de réponse à une requête d'invitation à un lobby
 *
 * @param id      id numérique de de l'invitation
 * @param name    nom du lobby
 * @param creator utilisateur créateur du lobby
 * @param game    jeu sur lequel joue le lobby
 */
public record LobbyInvitationResponse(Long id,
                                      String name,
                                      NoFriendsUserResponse creator,
                                      GameDto game) {
}
