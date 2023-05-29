package com.esgi.pa.api.dtos.requests;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import javax.validation.constraints.NotBlank;

import com.esgi.pa.domain.enums.RequestStatus;
import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AnswerFriendRequest(@NotBlank(message = "sender Id is required") Long sender,
        @NotBlank(message = "Status is required") RequestStatus status) {

}
