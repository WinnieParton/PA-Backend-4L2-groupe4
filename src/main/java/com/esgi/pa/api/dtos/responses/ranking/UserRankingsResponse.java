package com.esgi.pa.api.dtos.responses.ranking;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * @param rankings
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record UserRankingsResponse(List<RankingDto> rankings) {
}
