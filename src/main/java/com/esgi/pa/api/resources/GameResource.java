package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import javax.validation.Valid;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import com.esgi.pa.api.dtos.requests.game.AddGameRequest;
import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.game.GetAllGameResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {

    private final GameService gameService;
    private final LobbyService lobbyService;

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

}
