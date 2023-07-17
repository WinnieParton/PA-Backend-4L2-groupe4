package com.esgi.pa.api.dtos.requests.friend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.validation.constraints.NotNull;

/**
 * Body de la requête d'ajout d'ami
 *
 * @param sender id du demandeur
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AddFriendRequest(@NotNull(message = "sender Id is required") Long sender) {
}
