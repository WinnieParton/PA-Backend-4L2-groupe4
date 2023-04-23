package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.LobbyDto;
import com.esgi.pa.domain.entities.Lobby;

public interface LobbyMapper {

    static LobbyDto toDto(Lobby entity) {
        return new LobbyDto(
            entity.getId(), 
            entity.getName(), 
            UserMapper.toDto(entity.getCreator()),
            GameMapper.toDto(entity.getGame()),
            entity.isPrivate(),
            entity.getStatus(),
            entity.getCreatedAt(),
            entity.getUpdateAt());
    }

    static Lobby toDomain(LobbyDto dto) {
        return Lobby.builder()
            .id(dto.id())
            .name(dto.name())
            .creator(UserMapper.toDomain(dto.creator()))
            .game(GameMapper.toDomain(dto.game()))
            .isPrivate(dto.isPrivate())
            .status(dto.status())
            .createdAt(dto.createdAt())
            .updateAt(dto.updateAt())
            .build();
    }

    static List<LobbyDto> toDto(List<Lobby> lobbies) {
        return lobbies.stream()
            .map(lobby -> toDto(lobby))
            .toList();
    }
    
}
