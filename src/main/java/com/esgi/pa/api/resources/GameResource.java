package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.game.AddGameRequest;
import com.esgi.pa.api.dtos.requests.move.MoveDto;
import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.game.GetAllGameResponse;
import com.esgi.pa.api.dtos.responses.game.GetFileGameDtoResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.api.mappers.MoveMapper;
import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.Move;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RollbackEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MoveService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * Contient les routes lié aux jeux
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {

    private final GameService gameService;
    private final LobbyService lobbyService;
    private final MoveService moveService;
    private final UserService userService;

    /**
     * Permet le traitement des requêtes de création de jeu
     *
     * @param request contient les informations relative à la création d'un jeu
     * @return les informations relative au jeu
     * @throws TechnicalFoundException si le jeu existe déjà
     * @throws IOException             si problème lors de la sauvegarde des fichiers du jeu
     */
    @PostMapping(value = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(CREATED)
    public GameDto createGame(@Valid AddGameRequest request) throws TechnicalFoundException, IOException {
        return GameMapper.toDto(
            gameService.createGame(
                request.name(),
                request.description(),
                request.gameFiles(),
                request.miniature(),
                request.minPlayers(),
                request.maxPlayers()
            )
        );
    }

    /**
     * Permet la récupération de la liste complète des jeux
     *
     * @return la liste des jeux
     */
    @GetMapping
    public GetAllGameResponse getAllGame() {
        return new GetAllGameResponse(GameMapper.toDto(gameService.findAll()));
    }

    /**
     * Permet la récupération d'un jeu
     *
     * @param id id numérique du jeu
     * @return informations relative au jeu
     * @throws TechnicalNotFoundException si le jeu n'est pas trouvé
     */
    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable Long id)
        throws TechnicalNotFoundException {
        return GameMapper.toDto(gameService.getById(id));
    }

    /**
     * Permet de récupérer la liste des précédents états d'une partie
     *
     * @param id id numérique du lobby
     * @return la liste des états précédent d'une partie
     * @throws TechnicalNotFoundException si le lobby n'est pas trouvé
     */
    @GetMapping("moves/{id}")
    public List<MoveDto> getMovesForGame(@PathVariable Long id)
        throws TechnicalNotFoundException {
        return MoveMapper.toMovesForOneLobby(
            moveService.getAllMovesForLobby(lobbyService.getById(id))
        );
    }

    /**
     * Permet le téléchargement des fichier d'un jeu
     *
     * @param id id numérique du jeu
     * @return fichiers du jeu
     * @throws TechnicalNotFoundException si le jeu n'est pas trouvé
     */
    @GetMapping("/file/{id}")
    public ResponseEntity<?> downloadFile(@PathVariable Long id)
        throws TechnicalNotFoundException {
        Game game = gameService.getById(id);
        try {
            String fileName = game.getGameFiles();
            String language = gameService.getLanguageFromExtension(fileName);
            String fileContent = gameService.getFileContent(fileName);

            return new ResponseEntity<>(
                new GetFileGameDtoResponse(language, fileContent),
                HttpStatus.OK
            );
        } catch (IOException e) {
            throw new RuntimeException("File not found", e);
        }
    }

    /**
     * Récupère le dernier état de la partie
     *
     * @param idlobby id numérique du lobby
     * @return état de la partie
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     * @throws IOException                si problème avec le writer
     */
    @GetMapping("/move/{idlobby}")
    public String processGamePlayer(@PathVariable Long idlobby) throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.getById(idlobby);
        Optional<Move> move = moveService.findLastMoveOutput(lobby);
        if (move.isPresent() && move.get().getEndPart()) {
            moveService.getHistorieMoveInLobby(lobby);
            return move.get().getGameState();
        }
        return "";
    }

    /**
     * Traite les requêtes de rollback à un jeu de lobby
     *
     * @param idUser les informations relative à utilisateur
     * @param idmove les informations relative à utilisateur
     * @param status les informations relative à la gestion du rollback
     */
    @GetMapping("/rollback/{idUser}/idmove/{idmove}/status/{status}")
    @ResponseStatus(OK)
    public void createoranswerRollback(@PathVariable Long idUser, @PathVariable Long idmove, @PathVariable RollbackEnum status) throws TechnicalNotFoundException {
        User user = userService.getById(idUser);
        Move move = moveService.getById(idmove);
        if(status == status.POP)
            moveService.popAccept(move);
        List<Move> listLasMove = moveService.findListLastMoveInput(move.getLobby());
        String output = gameService.runEngineRollback(move.getLobby(), listLasMove);
        moveService.answerRollback(user, status, move, output);
    }
}
