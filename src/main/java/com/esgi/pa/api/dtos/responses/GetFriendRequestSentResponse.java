package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;

import java.util.UUID;

public record GetFriendRequestSentResponse(
    UUID id,
    UUID user,
    NoFriendsUserResponse friend,
    RequestStatus status) {

}
