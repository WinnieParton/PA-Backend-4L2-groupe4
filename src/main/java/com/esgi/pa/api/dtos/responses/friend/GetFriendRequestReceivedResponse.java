package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête de récupération d'une demande d'ami reçu
 *
 * @param id     id numérique de la relation
 * @param user   utilisateur demandeur
 * @param friend utilisateur receveur
 * @param status statut de la demande
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record GetFriendRequestReceivedResponse(
    Long id,
    NoFriendsUserResponse user,
    Long friend,
    RequestStatus status) {
}
