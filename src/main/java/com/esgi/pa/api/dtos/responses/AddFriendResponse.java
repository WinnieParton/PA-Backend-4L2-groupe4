package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;

public record AddFriendResponse(
        Long id,
        RequestStatus status) {

}
