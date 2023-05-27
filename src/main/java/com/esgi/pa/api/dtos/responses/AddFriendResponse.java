package com.esgi.pa.api.dtos.responses;

import java.util.UUID;

import com.esgi.pa.domain.enums.RequestStatus;

public record AddFriendResponse(
    UUID id,
    RequestStatus stauts
) {

}
