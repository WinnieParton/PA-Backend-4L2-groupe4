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

@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
@Api(tags = "Lobby API")
public class LobbyResource {

    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;

    @GetMapping("{id}")
    @ResponseStatus(OK)
    public GetlobbyResponse getOne(@PathVariable Long id) throws TechnicalNotFoundException {
        return LobbyMapper.toGetlobbyResponse(lobbyService.getById(id));
    }

    @GetMapping
    @ResponseStatus(OK)
    public GetlobbiesResponse getAll() {
        return new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                        lobbyService.findAll()));
    }

    @GetMapping("user/{id}")
    @ResponseStatus(OK)
    public GetlobbiesResponse getLobbiesByUser(@PathVariable Long id) throws TechnicalNotFoundException {
        return new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                        userService.getLobbiesByUser(
                                userService.getById(id))));
    }

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

    @PatchMapping("/{idlobby}")
    @ResponseStatus(NO_CONTENT)
    public void pauseGame(@PathVariable Long idlobby) throws TechnicalNotFoundException, IOException {
        gameService.closeWriter();
        lobbyService.pauseGame(
                lobbyService.getById(idlobby));
    }

}
