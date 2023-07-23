package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.domain.enums.RequestStatus;

/**
 * DTO de réponse d'une demande d'ajout d'ami
 *
 * @param id     id numérique de la relation d'amitié
 * @param status statut de la demande
 */
public record AddFriendResponse(
    Long id,
    RequestStatus status) {

}
