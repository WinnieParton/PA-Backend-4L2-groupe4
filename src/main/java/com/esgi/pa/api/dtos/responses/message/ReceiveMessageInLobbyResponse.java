package com.esgi.pa.api.dtos.responses.message;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

@JsonAutoDetect(fieldVisibility = ANY)
public record ReceiveMessageInLobbyResponse(
        @NotBlank(message = "Sender Id is required") Long senderName,
        @NotNull(message = "Message Id is required") String message,
        String currentDate) {
}