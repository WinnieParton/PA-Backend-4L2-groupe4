package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.RankingDto;
import com.esgi.pa.api.dtos.responses.GlobalRankingResponse;
import com.esgi.pa.api.dtos.responses.NoGameRankingResponse;
import com.esgi.pa.api.dtos.responses.UserRankingsResponse;
import com.esgi.pa.domain.entities.Ranking;

import java.util.List;

public interface RankingMapper {

    static GlobalRankingResponse toGlobalRankingResponse(List<Ranking> rankings) {
        return new GlobalRankingResponse(
                rankings.stream()
                        .map(RankingMapper::toNoGameRankingRespsonse)
                        .toList());
    }

    static NoGameRankingResponse toNoGameRankingRespsonse(Ranking ranking) {
        return new NoGameRankingResponse(
                ranking.getId(),
                UserMapper.toNoFriendsUserResponse(ranking.getPlayer()),
                ranking.getScore());
    }

    static UserRankingsResponse toUserRankingsResponse(List<Ranking> rankings) {
        return new UserRankingsResponse(
                rankings.stream()
                        .map(RankingMapper::toRankingDto)
                        .toList());
    }

    static RankingDto toRankingDto(Ranking ranking) {
        return new RankingDto(
                ranking.getId(),
                GameMapper.toDto(ranking.getGame()),
                UserMapper.toNoFriendsUserResponse(ranking.getPlayer()),
                ranking.getScore());
    }
}
