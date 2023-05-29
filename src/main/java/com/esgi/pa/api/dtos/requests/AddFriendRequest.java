package com.esgi.pa.api.dtos.requests;

import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AddFriendRequest(@NotBlank(message = "sender Id is required") Long sender) {
}
