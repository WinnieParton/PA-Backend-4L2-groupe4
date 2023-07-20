package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.enums.ActionEnum;
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
    public Move saveGameState(Lobby lobby, String gameState, ActionEnum actionEnum) {
        return moveAdapter.save(
            Move.builder()
                .lobby(lobby)
                .gameState(gameState)
                .endPart(Boolean.FALSE)
                .actionEnum(actionEnum)
                .build());
    }

    /**
     * Récupère le dernier état du jeu d'un lobby
     *
     * @param lobby le lobby dont on veut récupérer l'état du jeu
     * @return Optional du dernier état du jeu
     */
    public Optional<Move> findLastMoveOutput(Lobby lobby) {
        return moveAdapter.findByLobbyActionOutput(lobby);
    }

    /**
     * Récupère la liste des dernier état du jeu d'un lobby
     *
     * @param lobby le lobby dont on veut récupérer l'état du jeu
     * @return List du dernier état du jeu
     */
    public List<Move> findListLastMove(Lobby lobby) {
        return moveAdapter.findListLastMoveALobby(lobby);
    }

    /**
     * Récupère la liste des dernier état du jeu d'un lobby effectué par un utilisateur
     *
     * @param lobby le lobby dont on veut récupérer l'état du jeu
     * @return List du dernier état du jeu
     */
    public List<Move> findListLastMoveInput(Lobby lobby) {
        return moveAdapter.findByListLobbyActionInput(lobby);
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
