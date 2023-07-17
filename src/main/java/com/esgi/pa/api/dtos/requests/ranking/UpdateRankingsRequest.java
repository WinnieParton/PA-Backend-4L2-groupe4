package com.esgi.pa.api.dtos.requests.ranking;

public record UpdateRankingsRequest(Long winnerId, String scoresByPlayers) {
}
