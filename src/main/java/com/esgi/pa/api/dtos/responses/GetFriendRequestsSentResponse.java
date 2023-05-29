package com.esgi.pa.api.dtos.responses;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetFriendRequestsSentResponse(List<GetFriendRequestSentResponse> requests) {

}
