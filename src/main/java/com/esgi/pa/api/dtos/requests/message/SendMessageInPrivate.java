package com.esgi.pa.api.dtos.requests.message;

import com.esgi.pa.domain.enums.StatusMessage;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record SendMessageInPrivate(
  @NotBlank(message = "Sender Id is required") Long senderUser,
  @NotNull(message = "Message Id is required") String message,
  @NotBlank(message = "Sender Name is required") String senderName,
  @NotBlank(message = "Receiver Name is required") String receiverName,
  @NotBlank(message = "Receiver Id is required") Long receiverUser,
  StatusMessage status,
  String currentDate,
  Boolean send
) {}
