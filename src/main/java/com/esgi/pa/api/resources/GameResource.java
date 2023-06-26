package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.requests.game.AddGameRequest;
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
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {

    private final GameService gameService;
    private final LobbyService lobbyService;

    @PostMapping(value  = "/create", consumes = MediaType.MULTIPART_FORM_DATA_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
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

    // TODO GROSSE REFACTO => PAS DE LOGIQUE DANS LES RESOURCES
    @PatchMapping("/{id}/lobby/{idlobby}")
    @ResponseBody
    public ResponseEntity<String> redirectPost(@PathVariable Long id,
                                               @PathVariable Long idlobby, @RequestBody String requestBody) throws TechnicalNotFoundException, IOException, InterruptedException {
        Game game = gameService.getById(id);
        Lobby lobby = lobbyService.getById(idlobby);
        HttpClient httpClient = HttpClient.newHttpClient();
        URI springBootUrl = UriComponentsBuilder.fromUriString(game.getGameFiles()).build().toUri();
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(springBootUrl)
                .header("Content-Type", "application/json")
                .method("PATCH", HttpRequest.BodyPublishers.ofString(requestBody, StandardCharsets.UTF_8))
                .build();
        HttpResponse<String> httpResponse = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
        ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonNode = objectMapper.readTree(httpResponse.body());
        lobby.setStatus(GameStatusEnum.valueOf(jsonNode.get("statusGame").asText()));
        lobbyService.save(lobby);
        return ResponseEntity.status(httpResponse.statusCode()).body(httpResponse.body());
    }


}
