package com.esgi.pa.api.dtos.requests.friend;

import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête de réponse à une demande d'amitié
 *
 * @param sender id du demandeur
 * @param status statut de la réponse
 */
@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerFriendRequest(@NotNull(message = "sender Id is required") Long sender,
                                  @NotNull(message = "Status is required") RequestStatusEnum status) {

}
