package com.esgi.pa.api.dtos.responses.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record ReceiveMessageInLobbyResponse(
        @NotBlank(message = "Sender Id is required") Long senderName,
        @NotNull(message = "Message Id is required") String message,
        String currentDate) {
}