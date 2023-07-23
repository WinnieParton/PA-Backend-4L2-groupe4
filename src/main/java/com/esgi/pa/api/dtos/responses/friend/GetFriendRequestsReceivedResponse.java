package com.esgi.pa.api.dtos.responses.friend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des demandes d'amis reçu
 *
 * @param requests liste des demandes d'amis reçu
 */
@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetFriendRequestsReceivedResponse(List<GetFriendRequestReceivedResponse> requests) {

}
