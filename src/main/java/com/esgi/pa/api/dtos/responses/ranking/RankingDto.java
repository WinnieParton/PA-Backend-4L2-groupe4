package com.esgi.pa.api.dtos.responses.ranking;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record RankingDto(
        Long id,
        GameDto game,
        NoFriendsUserResponse player,
        double score) {
}
