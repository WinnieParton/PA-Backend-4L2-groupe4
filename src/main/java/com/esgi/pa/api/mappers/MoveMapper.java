package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.MoveDto;
import com.esgi.pa.domain.entities.Move;

import java.util.List;

public interface MoveMapper {

    static MoveDto toDto(Move move) {
        return new MoveDto(
            move.getId(),
            LobbyMapper.toGetlobbyResponse(move.getLobby()),
            move.getGameState());
    }

    static List<MoveDto> toMovesForOneLobby(List<Move> moves) {
        return moves.stream()
            .map(MoveMapper::toDto)
            .distinct()
            .toList();
    }
}
