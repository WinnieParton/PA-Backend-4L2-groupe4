package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.domain.entities.Game;

import java.util.List;

public interface GameMapper {

    static GameDto toDto(Game entity) {
        return new GameDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getGameFiles(),
                entity.getMiniature(),
                entity.getMinPlayers(),
                entity.getMaxPlayers());
    }

    static Game toDomain(GameDto dto) {
        return null;
    }

    static List<GameDto> toDto(List<Game> entities) {
        return entities.stream()
                .map(GameMapper::toDto)
                .distinct()
                .toList();
    }
}
