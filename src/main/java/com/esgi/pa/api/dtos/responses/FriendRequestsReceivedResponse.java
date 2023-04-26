package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record FriendRequestsReceivedResponse(UUID id,
                                             UserDto user,
                                             UUID friend,
                                             RequestStatus status) {
}
