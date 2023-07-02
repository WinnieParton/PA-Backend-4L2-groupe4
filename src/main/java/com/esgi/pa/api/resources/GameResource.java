package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.MoveDto;
import com.esgi.pa.api.dtos.requests.game.AddGameRequest;
import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.game.GetAllGameResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.api.mappers.MoveMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MoveService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {

    private final GameService gameService;
    private final LobbyService lobbyService;
    private final MoveService moveService;

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
                request.maxPlayers()));
    }

    @GetMapping()
    public GetAllGameResponse getAllGame() {
        return new GetAllGameResponse(
            GameMapper.toDto(
                gameService.findAll()));
    }

    @GetMapping("/{id}")
    public GameDto getGame(@PathVariable Long id) throws TechnicalNotFoundException {
        return GameMapper.toDto(gameService.getById(id));
    }

    @GetMapping("moves/{id}")
    public List<MoveDto> getMovesForGame(@PathVariable Long id) throws TechnicalNotFoundException {
        return MoveMapper.toMovesForOneLobby(
            moveService.getAllMovesForLobby(
                lobbyService.getById(id)));
    }
}
