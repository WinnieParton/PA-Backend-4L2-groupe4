package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.LobbyDto;
import com.esgi.pa.domain.entities.Lobby;

import java.util.List;

public interface LobbyMapper {

    static LobbyDto toDto(Lobby entity) {
        return new LobbyDto(
            entity.getId(),
            entity.getName(),
            entity.getCreator().getId(),
            entity.getGame().getId(),
            entity.isPrivate(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdateAt());
    }

    static List<LobbyDto> toDto(List<Lobby> lobbies) {
        return lobbies.stream()
            .map(LobbyMapper::toDto)
            .toList();
    }
    
}
