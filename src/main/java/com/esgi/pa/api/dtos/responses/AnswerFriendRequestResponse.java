package com.esgi.pa.api.dtos.responses;

import java.util.UUID;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AnswerFriendRequestResponse(
    UUID id,
    NoFriendsUserResponse friend,
    RequestStatus stauts
) {

}
