package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.GameDto;
import com.esgi.pa.api.dtos.requests.AddGameRequest;
import com.esgi.pa.api.dtos.responses.GetGamesResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.esgi.pa.domain.exceptions.MethodArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ErrorFormatService;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RequestCallback;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.RestTemplate;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {
    private final GameService gameService;
    private final LobbyService lobbyService;
    private final ErrorFormatService errorFormatService;

    @PostMapping("/save")
    @ResponseStatus(CREATED)
    public GameDto saveGame(@RequestBody @Valid AddGameRequest request, BindingResult bindingResult)
            throws TechnicalNotFoundException, TechnicalFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        Game game = gameService.createGame(new Game(request.name(), request.description(), request.gameFiles(),
                request.miniature(), request.minPlayers(), request.maxPlayers()));

        return new GameDto(game.getId(), game.getName(),
                game.getDescription(), game.getGameFiles(), game.getMiniature(), game.getMinPlayers(),
                game.getMaxPlayers());
    }

    @GetMapping()
    public GetGamesResponse getGames() {

        return new GetGamesResponse(
                GameMapper.toDto(
                        gameService.findAll()));
    }

    @PatchMapping("/{id}/lobby/{idlobby}/status/{status}")
    @ResponseBody
    public ResponseEntity<String> redirectPost(@PathVariable Long id, @PathVariable Boolean status,
                                               @PathVariable Long idlobby, @RequestBody String requestBody)
            throws TechnicalNotFoundException, IOException, InterruptedException {
        Game game = gameService.getById(id);
        Lobby lobby = lobbyService.findOne(idlobby);

        lobby.setStatus(status?GameStatusEnum.STARTED:GameStatusEnum.PAUSED);
        lobbyService.save(lobby);

        HttpClient httpClient = HttpClient.newHttpClient();
        URI springBootUrl = UriComponentsBuilder.fromUriString(game.getGameFiles()).build().toUri();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(springBootUrl)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());

        return ResponseEntity.status(httpResponse.statusCode()).body(httpResponse.body());
    }


}
