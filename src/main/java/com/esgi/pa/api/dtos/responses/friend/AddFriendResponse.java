package com.esgi.pa.api.dtos.responses.friend;

import com.esgi.pa.domain.enums.RequestStatus;

public record AddFriendResponse(
        Long id,
        RequestStatus status) {

}
