package com.esgi.pa.api.mappers;

import java.time.LocalDateTime;
import java.util.List;

import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Message;

public interface MessageMapper {
    static List<ReceiveMessageInLobbyResponse> toGetmessageResponse(List<Message> entities) {
        return entities.stream()
                .map(MessageMapper::toGetmessageResponse)
                .distinct()
                .toList();
    }

    static ReceiveMessageInLobbyResponse toGetmessageResponse(Message message) {
        LocalDateTime sentAt = message.getSentAt();
        String sentAtString = (sentAt != null) ? sentAt.toString() : "";

        return new ReceiveMessageInLobbyResponse(
                message.getCreator().getId(),
                message.getContent(),
                sentAtString);
    }
}
