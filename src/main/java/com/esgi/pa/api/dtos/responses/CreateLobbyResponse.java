package com.esgi.pa.api.dtos.responses;

import java.time.LocalDateTime;

public record CreateLobbyResponse(
        Long id,
        LocalDateTime createdAt) {

}
