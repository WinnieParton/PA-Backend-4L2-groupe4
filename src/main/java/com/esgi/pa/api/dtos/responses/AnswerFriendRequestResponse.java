package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AnswerFriendRequestResponse(
        Long id,
        NoFriendsUserResponse friend,
        RequestStatus status) {

}
