package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;

public record GetFriendRequestSentResponse(
        Long id,
        Long user,
        NoFriendsUserResponse friend,
        RequestStatus status) {

}
