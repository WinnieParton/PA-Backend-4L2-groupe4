package com.esgi.pa.api.dtos.responses.message;

import com.esgi.pa.domain.enums.StatusMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de réponse à une requête de récupération des messages d'un lobby
 *
 * @param senderUser   id numérique de l'envoyeur
 * @param message      contenu du message
 * @param senderName   nom de l'envoyeur
 * @param receiverName nom du receveur
 * @param receiverUser id numérique du receveur
 * @param name         nom du lobby
 * @param status       statut du message
 * @param currentDate  date d'envoi du message
 * @param send
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record ListMessageInPrivateResponse(
    @NotBlank(message = "Sender Id is required") Long senderUser,
    @NotNull(message = "Message Id is required") String message,
    @NotBlank(message = "Sender Name is required") String senderName,
    @NotBlank(message = "Receiver Name is required") String receiverName,
    @NotBlank(message = "Receiver Id is required") Long receiverUser,
    @NotBlank(message = "Name Name is required") String name,
    StatusMessage status,
    String currentDate,
    Boolean send) {
}