package com.esgi.pa.api.dtos.responses.message;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête
 *
 * @param senderName  nom de l'envoyeur
 * @param message     contenu du message
 * @param currentDate date d'envoi
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record ReceiveMessageInLobbyResponse(
    @NotBlank(message = "Sender Id is required") Long senderName,
    @NotNull(message = "Message Id is required") String message,
    String currentDate) {
}