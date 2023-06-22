package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Lobby;

public interface MessageMapper {
    static ReceiveMessageInLobbyResponse toReceiveMessageInLobbyResponse(Lobby entity) {
        return new ReceiveMessageInLobbyResponse(
                null,
                null, null);
    }
}
