package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.GameDto;
import com.esgi.pa.domain.entities.Game;

public interface GameMapper {

    static GameDto toDto(Game entity) {
        return null;
    }

    static Game toDomain(GameDto dto) {
        return null;
    }
    
}
