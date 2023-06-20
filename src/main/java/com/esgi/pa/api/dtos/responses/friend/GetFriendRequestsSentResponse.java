package com.esgi.pa.api.dtos.responses.friend;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetFriendRequestsSentResponse(List<GetFriendRequestSentResponse> requests) {

}
