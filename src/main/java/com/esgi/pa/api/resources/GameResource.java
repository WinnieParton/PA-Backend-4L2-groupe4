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
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MoveService;
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
    public String processGamePlayer(@PathVariable Long idlobby) throws TechnicalNotFoundException, IOException {
        Lobby lobby = lobbyService.getById(idlobby);
        Optional<Move> move = moveService.findLastMove(lobby);
        if (move.isPresent()) {
            if (move.get().getEndPart())
                return move.get().getGameState();
            gameService.closeWriter();
        }
        return "";
    }
}
