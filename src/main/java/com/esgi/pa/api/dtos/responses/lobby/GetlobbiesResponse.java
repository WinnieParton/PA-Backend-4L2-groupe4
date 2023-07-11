package com.esgi.pa.api.dtos.responses.lobby;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbiesResponse(List<GetlobbyResponse> lobbies) {
}
