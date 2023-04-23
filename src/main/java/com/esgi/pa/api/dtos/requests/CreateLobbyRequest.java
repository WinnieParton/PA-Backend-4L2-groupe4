package com.esgi.pa.api.dtos.requests;

import java.util.UUID;

public record CreateLobbyRequest(
    String name,
    UUID userId,
    UUID gameId,
    boolean isPrivate
) {}