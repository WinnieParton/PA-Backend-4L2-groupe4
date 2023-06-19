package com.esgi.pa.api.dtos.requests;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

import static com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility.ANY;

@JsonAutoDetect(fieldVisibility = ANY)
public record InviteFriendToLobbyRequest(Long friendId) {
}