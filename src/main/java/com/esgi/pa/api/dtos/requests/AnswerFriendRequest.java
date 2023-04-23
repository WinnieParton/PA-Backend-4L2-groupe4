package com.esgi.pa.api.dtos.requests;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.UUID;

import com.esgi.pa.domain.enums.FriendRequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerFriendRequest(UUID receiverId, FriendRequestStatus status) {

}
