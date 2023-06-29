package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.lobby.CreateLobbyResponse;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyMessageResponse;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyResponse;
import com.esgi.pa.api.dtos.responses.invitation.LobbyInvitationResponse;
import com.esgi.pa.domain.entities.Lobby;

import java.util.List;

public interface LobbyMapper {

    static GetlobbyResponse toGetlobbyResponse(Lobby entity) {
        return new GetlobbyResponse(
                entity.getId(),
                entity.getName(),
                UserMapper.toNoFriendsUserResponse(entity.getCreator()),
                GameMapper.toDto(entity.getGame()),
                entity.isInvitationOnly(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdateAt(),
                UserMapper.toNoFriendsUserResponse(entity.getParticipants()));
    }

    static List<GetlobbyResponse> toGetlobbyResponse(List<Lobby> entities) {
        return entities.stream()
                .map(LobbyMapper::toGetlobbyResponse)
                .distinct()
                .toList();
    }

    static CreateLobbyResponse toCreateLobbyResponse(Lobby entity) {
        return new CreateLobbyResponse(
                entity.getId(),
                entity.getCreatedAt());
    }

    static LobbyInvitationResponse toLobbyInvitationResponse(Lobby lobby) {
        return new LobbyInvitationResponse(
                lobby.getId(),
                lobby.getName(),
                UserMapper.toNoFriendsUserResponse(lobby.getCreator()),
                GameMapper.toDto(lobby.getGame()));
    }

    static GetlobbyMessageResponse toGetlobbyMessageResponse(Lobby entity) {
        return new GetlobbyMessageResponse(
                entity.getId(),
                UserMapper.toNoFriendsUserResponse(entity.getParticipants()));
    }
}
