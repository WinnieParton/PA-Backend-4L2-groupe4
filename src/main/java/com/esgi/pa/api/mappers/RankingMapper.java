package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.ranking.GlobalRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.NoGameRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.RankingDto;
import com.esgi.pa.api.dtos.responses.ranking.UserRankingsResponse;
import com.esgi.pa.domain.entities.Ranking;

import java.util.List;

/**
 * Contient les méthodes pour mapper les entités ranking du domain vers des dtos
 */
public interface RankingMapper {

    static GlobalRankingResponse toGlobalRankingResponse(List<Ranking> rankings) {
        return new GlobalRankingResponse(
            rankings.stream()
                .map(RankingMapper::toNoGameRankingRespsonse)
                .distinct()
                .toList());
    }

    static NoGameRankingResponse toNoGameRankingRespsonse(Ranking ranking) {
        return new NoGameRankingResponse(
            ranking.getId(),
            UserMapper.toNoFriendsUserResponse(ranking.getPlayer()),
            ranking.getScore());
    }

    static List<NoGameRankingResponse> toNoGameRankingRespsonse(List<Ranking> rankingList) {
        return rankingList.stream()
            .map(RankingMapper::toNoGameRankingRespsonse)
            .distinct()
            .toList();
    }

    static UserRankingsResponse toUserRankingsResponse(List<Ranking> rankings) {
        return new UserRankingsResponse(
            rankings.stream()
                .map(RankingMapper::toRankingDto)
                .distinct()
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
