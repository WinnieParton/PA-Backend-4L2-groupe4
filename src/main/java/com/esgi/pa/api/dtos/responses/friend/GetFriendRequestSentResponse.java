package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.RequestStatus;

/**
 * DTO de réponse à une requête de récupération d'une demande d'ami envoyé
 *
 * @param id     id numérique de la relation d'amitié
 * @param user   utilisateur envoyeur
 * @param friend utilisateur receveur
 * @param status statut de la relation
 */
public record GetFriendRequestSentResponse(
    Long id,
    Long user,
    NoFriendsUserResponse friend,
    RequestStatus status) {

}
