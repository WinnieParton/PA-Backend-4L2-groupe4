package com.esgi.pa.api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;

import javax.validation.constraints.NotBlank;
import java.util.UUID;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AddFriendRequest(@NotBlank(message = "UUID sender is required") UUID sender) {
}
