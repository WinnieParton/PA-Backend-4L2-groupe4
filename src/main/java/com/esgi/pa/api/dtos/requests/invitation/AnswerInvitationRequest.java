package com.esgi.pa.api.dtos.requests.invitation;

import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * Requête de réponse à une invitation de lobby
 *
 * @param userId   id de l'utilisateur invité
 * @param lobbyId  id du lobby auquel l'utilisateur est invité
 * @param response statut de l'invitation
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerInvitationRequest(Long userId,
                                      Long lobbyId,
                                      RequestStatusEnum response) {
}
