package com.esgi.pa.api.dtos.requests.message;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record SendMessageInLobbyRequest(
        @NotBlank(message = "Sender Id is required") Long senderUser,
        @NotNull(message = "Lobby Id is required") Long lobby,
        @NotNull(message = "Message Id is required") String message,
        @NotBlank(message = "Sender Name is required") String senderName,
        @NotBlank(message = "Receiver Name is required") String receiverName,
        StatusMessageEnum status) {
}