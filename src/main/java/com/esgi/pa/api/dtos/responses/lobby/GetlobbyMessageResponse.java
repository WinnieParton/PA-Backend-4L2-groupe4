package com.esgi.pa.api.dtos.responses.lobby;

import java.util.List;

import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbyMessageResponse(
                Long id,
                List<NoFriendsUserResponse> participants) {
}
