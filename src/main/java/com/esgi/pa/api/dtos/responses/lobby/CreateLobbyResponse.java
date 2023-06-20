package com.esgi.pa.api.dtos.responses.lobby;

import java.time.LocalDateTime;

public record CreateLobbyResponse(
        Long id,
        LocalDateTime createdAt) {

}
