package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

/**
 * DTO de réponse à une requête de réponse à une demande d'ami
 *
 * @param id     id numérique de la relation d'amitié
 * @param friend utilisateur répondant à la demande
 * @param status statut de la demande
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AnswerFriendRequestResponse(
    Long id,
    NoFriendsUserResponse friend,
    RequestStatusEnum status) {

}
