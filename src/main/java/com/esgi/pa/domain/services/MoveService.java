package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.server.adapter.MoveAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des états de jeu
 */
@Service
@RequiredArgsConstructor
public class MoveService {

    private final MoveAdapter moveAdapter;

    /**
     * Persiste l'état actuel du tableau
     *
     * @param lobby     le lobby sur lequel le jeu se joue
     * @param gameState état du jeu
     * @return l'état enregistrer
     */
    public Move saveGameState(Lobby lobby, String gameState) {
        return moveAdapter.save(
            Move.builder()
                .lobby(lobby)
                .gameState(gameState)
                .endPart(Boolean.FALSE)
                .build());
    }

    /**
     * Récupère le dernier état du jeu d'un lobby
     *
     * @param lobby le lobby dont on veut récupérer l'état du jeu
     * @return Optional du dernier état du jeu
     */
    public Optional<Move> findLastMove(Lobby lobby) {
        return moveAdapter.findByLobby(lobby);
    }

    /**
     * Récupère l'ensemble des états pour un lobby spécifique
     *
     * @param lobby le lobby dont on veut récupérer les états des jeux
     * @return les précédents état d'un jeu
     */
    public List<Move> getAllMovesForLobby(Lobby lobby) {
        return moveAdapter.findAllByLobby(lobby);
    }

    /**
     * Sauvegarde l'état de fin de partie
     *
     * @param move état à sauvegarder
     */
    public void saveEndMove(Move move) {
        moveAdapter.save(move);
    }
}
