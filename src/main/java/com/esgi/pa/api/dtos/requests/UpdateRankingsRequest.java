package com.esgi.pa.api.dtos.requests;

import java.util.Map;

public record UpdateRankingsRequest(Long winnerId, String scoresByPlayers) {
}
