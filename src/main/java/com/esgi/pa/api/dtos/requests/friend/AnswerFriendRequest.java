package com.esgi.pa.api.dtos.requests.friend;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerFriendRequest(@NotNull(message = "sender Id is required") Long sender,
        @NotNull(message = "Status is required") RequestStatus status) {

}