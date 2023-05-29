package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.UUID;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AnswerFriendRequestResponse(
    UUID id,
    NoFriendsUserResponse friend,
    RequestStatus stauts
) {

}
