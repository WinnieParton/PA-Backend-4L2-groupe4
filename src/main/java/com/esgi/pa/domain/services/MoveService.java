package com.esgi.pa.domain.services;

import com.esgi.pa.api.dtos.responses.move.AnswerRollback;
import com.esgi.pa.api.dtos.responses.move.GetmovesResponse;
import com.esgi.pa.api.mappers.MoveMapper;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.ActionEnum;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.enums.RollbackEnum;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.MoveAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Service de gestion des états de jeu
 */
@Service
@RequiredArgsConstructor
public class MoveService {

    private final MoveAdapter moveAdapter;
    private final LobbyService lobbyService;
    private final SimpMessagingTemplate simpMessagingTemplate;

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
                .rollback(RollbackEnum.UNPOP)
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

    public List<Move> findListLastMoveInPut(Lobby lobby) {
        return moveAdapter.findByListLobbyLastActionInput(lobby);
    }

    /**
     * Récupère la liste du dernier état du jeu d'un lobby effectué par un utilisateur
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
    /**
     * Récupère un move par son id
     *
     * @param id id numérique du move
     * @return le move recherché
     * @throws TechnicalNotFoundException si le move n'est pas trouvé
     */
    public Move getById(Long id) throws TechnicalNotFoundException {
        return moveAdapter.findById(id)
                .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No Move found with id : " + id));
    }
    /**
     * Permet de envoyer ou demander un rollback
     * @throws TechnicalNotFoundException si le move n'est pas trouvé
     */
    public void answerRollback(User user, RollbackEnum status, Long id) throws TechnicalNotFoundException {
        Move move = getById(id);
        if(status == RollbackEnum.POP){
            move.setRollback(RollbackEnum.UNPOP);
            List<Move> moveToPop = moveAdapter.listMovePop(id);
            moveToPop.forEach(movepop ->{
                movepop.setRollback(RollbackEnum.POP);
                moveAdapter.save(movepop);
            });
        }else{
            move.setRollback(status);
        }
        if(status == RollbackEnum.PENDING)
            move.setRollBackDate(LocalDateTime.now());
        move = moveAdapter.save(move);

        AnswerRollback answerMoveRequest = new  AnswerRollback(move.getId(), move.getGameState(),status, move.getRollBackDate().toString());
        move.getLobby().getParticipants().forEach(participant -> {
            if (!Objects.equals(participant.getId(), user.getId())) {
                simpMessagingTemplate.convertAndSendToUser(participant.getName(), "/private/rollback", answerMoveRequest);
            }
        });
    }
    /**
     * Permet de verifié si une demande de roolback en attente ne depasse pas 1min de reponse
     */
    public boolean isMoveDateGreaterThanOrEqualToOneMinute(Move move) {
        if (move.getMoveDate() == null) {
            return false;
        }
        Duration duration = Duration.between(move.getMoveDate(), LocalDateTime.now());
        return duration.toSeconds() >= 60;
    }

    /**
     * Permet de verifié si une demande de roolback en attente ne depasse pas 1min de reponse
     */
    public void verifyRollback() {
         List<Move> rollback = moveAdapter.listMoveRollBackPending();
        rollback.forEach(move->{
            if(isMoveDateGreaterThanOrEqualToOneMinute(move)){
                move.setRollback(RollbackEnum.UNPOP);
                moveAdapter.save(move);
            }
        });
    }
    public void getHistorieMoveInLobby(Lobby lobby) {
        GetmovesResponse getmovesResponse = new GetmovesResponse(MoveMapper.toHistoryMovesForOneLobby(
                findListLastMoveInPut(lobby)));
        lobby.getParticipants().forEach(participant -> {
            simpMessagingTemplate.convertAndSendToUser(participant.getName(), "/private/historic/move", getmovesResponse);
        });
    }
}
