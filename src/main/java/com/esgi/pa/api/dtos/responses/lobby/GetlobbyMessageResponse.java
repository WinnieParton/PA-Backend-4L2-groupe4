package com.esgi.pa.api.dtos.responses.lobby;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.time.LocalDateTime;
import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbyMessageResponse(
                Long id,
                List<NoFriendsUserResponse> participants) {
}
