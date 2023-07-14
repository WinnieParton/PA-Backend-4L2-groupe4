package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record GetFriendRequestReceivedResponse(
        Long id,
        NoFriendsUserResponse user,
        Long friend,
        RequestStatus status) {
}
