package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.api.dtos.GameDto;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import java.time.LocalDateTime;
import java.util.List;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record GetlobbyResponse(
                Long id,
                String name,
                NoFriendsUserResponse creator,
                GameDto game,
                boolean isPrivate,
                GameStatusEnum status,
                LocalDateTime createdAt,
                LocalDateTime updateAt,
                List<NoFriendsUserResponse> participants) {
}
