package com.esgi.pa.api.dtos.responses;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetFriendRequestReceivedResponse(
        Long id,
        NoFriendsUserResponse user,
        Long friend,
        RequestStatus status) {
}
