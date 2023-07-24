package com.esgi.pa.api.dtos.requests.message;

import com.esgi.pa.domain.enums.StatusMessagePrivateEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

/**
 * DTO de requête d'envoi de message privé
 *
 * @param senderUser   id numérique de l'envoyeur
 * @param message      contenu du message
 * @param senderName   nom de l'envoyeur
 * @param receiverName nom du receveur
 * @param receiverUser id numérique du receveur
 * @param status       statut du message
 * @param currentDate  date d'envoi
 * @param send         envoyé
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record SendMessageInPrivate(
    @NotBlank(message = "Sender Id is required") Long senderUser,
    @NotNull(message = "Message Id is required") String message,
    @NotBlank(message = "Sender Name is required") String senderName,
    @NotBlank(message = "Receiver Name is required") String receiverName,
    @NotBlank(message = "Receiver Id is required") Long receiverUser,
    StatusMessagePrivateEnum status,
    String currentDate,
    Boolean send
) {}
