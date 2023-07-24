package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.move.AnswerMoveRequest;
import com.esgi.pa.api.dtos.responses.move.GetmovesResponse;
import com.esgi.pa.api.mappers.MoveMapper;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MoveService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Contient les routes permettant la communication avec les moteurs de jeux
 */
@RestController
@RequestMapping("/move")
@RequiredArgsConstructor
@Api(tags = "Move Template API")
public class MoveRessource {

    private final MoveService moveService;
    private final UserService userService;

    /**
     * Traite les requêtes de rollback à un jeu de lobby
     *
     * @param idUser les informations relative à utilisateur
     * @param answerMoveRequest les informations relative à la gestion du rollback
     */
    @PostMapping("/rollback/{idUser}")
    @ResponseStatus(OK)
    public void createoranswerRollback(@PathVariable Long idUser, AnswerMoveRequest answerMoveRequest) throws TechnicalNotFoundException {
        User user = userService.getById(idUser);
        moveService.answerRollback(user, answerMoveRequest.status(), answerMoveRequest.move());
    }
}
