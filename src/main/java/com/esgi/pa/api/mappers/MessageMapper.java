package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Message;

/**
 * Contient les méthodes pour mapper les entités message du domain vers des dtos
 */
public interface MessageMapper {
    static List<ReceiveMessageInLobbyResponse> toGetmessageResponse(List<Message> entities) {
        return entities.stream()
            .map(MessageMapper::toGetmessageResponse)
            .distinct()
            .toList();
    }

    static ReceiveMessageInLobbyResponse toGetmessageResponse(Message message) {
        return new ReceiveMessageInLobbyResponse(
            message.getCreator().getId(),
            message.getContent(),
                message.getSentAt());
    }
}
