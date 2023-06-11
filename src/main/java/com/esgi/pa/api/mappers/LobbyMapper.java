package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.CreateLobbyResponse;
import com.esgi.pa.api.dtos.responses.GetlobbyResponse;
import com.esgi.pa.domain.entities.Lobby;

import java.util.List;

public interface LobbyMapper {

    static GetlobbyResponse toGetlobbyResponse(Lobby entity) {
        return new GetlobbyResponse(
                entity.getId(),
                entity.getName(),
                UserMapper.toNoFriendsUserResponse(entity.getCreator()),
                GameMapper.toDto(entity.getGame()),
                entity.isPrivate(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getUpdateAt(),
                UserMapper.toNoFriendsUserResponse(entity.getParticipants()));
    }   
    
    static List<GetlobbyResponse> toGetlobbyResponse(List<Lobby> entities) {
        return entities.stream()
            .map(LobbyMapper::toGetlobbyResponse)
            .toList();
    }

    static CreateLobbyResponse toCreateLobbyResponse(Lobby entity) {
        return new CreateLobbyResponse(
            entity.getId(),
            entity.getCreatedAt());
    }

}
