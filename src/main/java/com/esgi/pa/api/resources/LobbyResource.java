package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

import javax.validation.Valid;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilder;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.enums.GameStatusEnum;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import com.esgi.pa.api.dtos.requests.AddFriendInLobbyRequest;
import com.esgi.pa.api.dtos.requests.CreateLobbyRequest;
import com.esgi.pa.api.dtos.responses.CreateLobbyResponse;
import com.esgi.pa.api.dtos.responses.GetlobbiesResponse;
import com.esgi.pa.api.dtos.responses.GetlobbyResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.domain.exceptions.MethodArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ErrorFormatService;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
@Api(tags = "Lobby API")
public class LobbyResource {

    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;
    private final ErrorFormatService errorFormatService;

    @GetMapping("{id}")
    public GetlobbyResponse getOne(@PathVariable Long id) throws TechnicalNotFoundException {
        return LobbyMapper.toGetlobbyResponse(lobbyService.findOne(id));
    }

    @GetMapping
    public GetlobbiesResponse getAll() {
        return new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                        lobbyService.findAll()));
    }
    @GetMapping("user/{id}")
    public GetlobbiesResponse getLobbiesByUser(@PathVariable Long id) throws TechnicalNotFoundException {

        return new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                        lobbyService.getLobbiesByUserId(id)));
    }
    @PostMapping
    @ResponseStatus(CREATED)
    public CreateLobbyResponse create(@Valid @RequestBody CreateLobbyRequest request, BindingResult bindingResult)
            throws TechnicalNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        return LobbyMapper.toCreateLobbyResponse(
                lobbyService.create(
                        request.name(),
                        userService.getById(request.user()),
                        gameService.getById(request.game()),
                        request.isPrivate()));
    }

    @PatchMapping("{id}/user/{idUser}")
    @ResponseStatus(OK)
    public GetlobbyResponse addUserInLobby(@Valid @RequestBody AddFriendInLobbyRequest request, @PathVariable Long id,
            @PathVariable Long idUser, BindingResult bindingResult) throws TechnicalNotFoundException {
        if (bindingResult.hasErrors()) {
            throw new MethodArgumentNotValidException(
                    errorFormatService.ErrorFormatExceptionHandle(bindingResult.getAllErrors()));
        }
        lobbyService.addUserInLobby(request.arrayUser(), idUser, id);
        return LobbyMapper.toGetlobbyResponse(lobbyService.findOne(id));
    }

    @PatchMapping("/{idlobby}")
    public GetlobbyResponse redirectOnLobby(@PathVariable Long idlobby) throws TechnicalNotFoundException {
        Lobby lobby = lobbyService.findOne(idlobby);
        lobby.setStatus(GameStatusEnum.PAUSED);
        lobbyService.save(lobby);
        return LobbyMapper.toGetlobbyResponse(lobbyService.findOne(idlobby));
    }
}
