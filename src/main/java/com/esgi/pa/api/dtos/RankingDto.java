package com.esgi.pa.api.dtos;

import com.esgi.pa.api.dtos.responses.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record RankingDto(
        Long id,
        GameDto game,
        NoFriendsUserResponse player,
        double score) {
}
