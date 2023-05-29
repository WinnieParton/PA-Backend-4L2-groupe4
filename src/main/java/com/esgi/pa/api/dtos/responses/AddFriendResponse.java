package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.domain.enums.RequestStatus;

import java.util.UUID;

public record AddFriendResponse(
    UUID id,
    RequestStatus stauts
) {

}
