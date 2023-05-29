package com.esgi.pa.api.dtos.requests;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import lombok.Builder;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerFriendRequest(@NotBlank(message = "UUID sender is required")UUID sender, @NotBlank(message = "Status is required") RequestStatus status) {

}
