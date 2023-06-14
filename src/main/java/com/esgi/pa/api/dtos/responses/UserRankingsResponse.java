package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.api.dtos.RankingDto;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import java.util.List;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record UserRankingsResponse(List<RankingDto> rankings) {
}
