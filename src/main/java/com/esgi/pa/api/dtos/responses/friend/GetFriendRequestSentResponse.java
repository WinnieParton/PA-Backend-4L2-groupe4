package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.RequestStatus;

public record GetFriendRequestSentResponse(
        Long id,
        Long user,
        NoFriendsUserResponse friend,
        RequestStatus status) {

}
