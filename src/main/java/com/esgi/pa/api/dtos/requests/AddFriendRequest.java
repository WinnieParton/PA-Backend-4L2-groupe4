package com.esgi.pa.api.dtos.requests;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonAutoDetect(fieldVisibility = Visibility.ANY)
public record AddFriendRequest(UUID receiver) {
    
    @JsonCreator
    public AddFriendRequest(@JsonProperty("receiver") UUID receiver) { this.receiver = receiver; }
}
