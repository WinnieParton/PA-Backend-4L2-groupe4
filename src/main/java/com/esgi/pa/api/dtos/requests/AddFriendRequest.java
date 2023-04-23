package com.esgi.pa.api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.UUID;

import lombok.Builder;

@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AddFriendRequest(UUID receiverId) {}
