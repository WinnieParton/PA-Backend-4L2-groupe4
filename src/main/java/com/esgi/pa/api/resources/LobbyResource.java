package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.lobby.CreateLobbyRequest;
import com.esgi.pa.api.dtos.responses.lobby.CreateLobbyResponse;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbiesResponse;
import com.esgi.pa.api.dtos.responses.lobby.GetlobbyResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

import static org.springframework.http.HttpStatus.*;

/**
 * Contient les routes lié aux lobbies
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
@Api(tags = "Lobby API")
public class LobbyResource {

    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;

    /**
     * Récupère un lobby
     *
     * @param id id numérique d'un lobby
     * @return informations relative au lobby
     * @throws TechnicalNotFoundException si le lobby n'est pas trouvé
     */
    @GetMapping("{id}")
    @ResponseStatus(OK)
    public GetlobbyResponse getOne(@PathVariable Long id) throws TechnicalNotFoundException {
        return LobbyMapper.toGetlobbyResponse(lobbyService.getById(id));
    }

    /**
     * Récupère la liste de l'ensemble des lobbies existant
     *
     * @return liste de lobbies
     */
    @GetMapping
    @ResponseStatus(OK)
    public GetlobbiesResponse getAll() {
        return new GetlobbiesResponse(
            LobbyMapper.toGetlobbyResponse(
                lobbyService.findAll()));
    }

    /**
     * Récupère les lobbies d'un utilisateur
     *
     * @param id id numérique de l'utilisateur
     * @return la liste des lobbies
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @GetMapping("user/{id}")
    @ResponseStatus(OK)
    public GetlobbiesResponse getLobbiesByUser(@PathVariable Long id) throws TechnicalNotFoundException {
        return new GetlobbiesResponse(
            LobbyMapper.toGetlobbyResponse(
                userService.getLobbiesByUser(
                    userService.getById(id))));
    }

    /**
     * Traite les requêtes de création de lobby
     *
     * @param request les informations relative à la création du lobby
     * @return le lobby nouvellement créer
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @PostMapping
    @ResponseStatus(CREATED)
    public CreateLobbyResponse create(@Valid @RequestBody CreateLobbyRequest request) throws TechnicalNotFoundException {
        return LobbyMapper.toCreateLobbyResponse(
            lobbyService.create(
                request.name(),
                userService.getById(request.user()),
                gameService.getById(request.game()),
                request.isPrivate()));
    }

    /**
     * Permet de changer le statut d'une partie
     *
     * @param idlobby id numérique du lobby
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     * @throws IOException                si problème avec le writer
     */
    @PatchMapping("/{idlobby}")
    @ResponseStatus(NO_CONTENT)
    public void pauseGame(@PathVariable Long idlobby) throws TechnicalNotFoundException, IOException {
        gameService.closeWriter();
        lobbyService.pauseGame(
            lobbyService.getById(idlobby));
    }

}
