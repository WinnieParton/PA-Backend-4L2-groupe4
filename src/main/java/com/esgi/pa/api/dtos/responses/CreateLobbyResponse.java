package com.esgi.pa.api.dtos.responses;

import java.time.LocalDateTime;
import java.util.UUID;

public record CreateLobbyResponse(
    UUID id,
    LocalDateTime createdAt
    ) {

}
