package com.esgi.pa.api.dtos.requests;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerInvitationRequest(Long userId,
                                      Long lobbyId,
                                      RequestStatus response) {
}
