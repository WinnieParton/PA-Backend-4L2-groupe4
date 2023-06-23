package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.requests.message.SendMessageInLobbyRequest;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyResponse;
import com.esgi.pa.api.dtos.responses.message.ReceiveMessageInLobbyResponse;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Message;
import com.esgi.pa.domain.enums.StatusMessageEnum;

import java.time.LocalDateTime;
import java.util.List;

public interface MessageMapper {
    static List<ReceiveMessageInLobbyResponse> toGetmessageResponse(List<Message> entities) {
        return entities.stream()
                .map(MessageMapper::toGetmessageResponse)
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
