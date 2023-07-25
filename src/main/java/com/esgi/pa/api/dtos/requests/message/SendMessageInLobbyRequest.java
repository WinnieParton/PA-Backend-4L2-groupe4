package com.esgi.pa.api.dtos.requests.message;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.StatusMessageEnum;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * DTO de requête de message dans un lobby
 *
 * @param senderUser   id numérique de l'envoyeur
 * @param lobby        id numérique du lobby
 * @param message      contenu du message envoyé
 * @param senderName   nom de l'envoyeur
 * @param receiverName nom du receveur
 * @param status       statut du message
 * @param currentDate  date d'envoi du message
 */
@JsonAutoDetect(fieldVisibility = ANY)
public record SendMessageInLobbyRequest(
    @NotBlank(message = "Sender Id is required") Long senderUser,
    @NotNull(message = "Lobby Id is required") Long lobby,
    @NotNull(message = "Message Id is required") String message,
    @NotBlank(message = "Sender Name is required") String senderName,
    @NotBlank(message = "Receiver Name is required") String receiverName,
    StatusMessageEnum status,
    String currentDate) {
}