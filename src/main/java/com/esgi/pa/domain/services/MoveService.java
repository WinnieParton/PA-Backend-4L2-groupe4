package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.esgi.pa.server.adapter.MoveAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor

public class MoveService {

    private final MoveAdapter moveAdapter;

    public Move saveGameState(Lobby lobby, String gameState) {
        return moveAdapter.save(
            Move.builder()
                .lobby(lobby)
                .gameState(gameState)
                    .endPart(Boolean.FALSE)
                .build());
    }
    public Optional<Move> findLastMove(Lobby lobby) {
        return moveAdapter.findByLobby(lobby);
    }
    public List<Move> getAllMovesForLobby(Lobby lobby) {
        return moveAdapter.findAllByLobby(lobby);
    }
    public void saveEndMove(Move move){
        moveAdapter.save(move);
    }
}
