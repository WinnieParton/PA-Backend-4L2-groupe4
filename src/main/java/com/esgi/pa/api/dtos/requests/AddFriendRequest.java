package com.esgi.pa.api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import lombok.Builder;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

import java.util.UUID;


@Builder
@JsonAutoDetect(fieldVisibility = ANY)
public record AddFriendRequest(UUID receiver) {}
