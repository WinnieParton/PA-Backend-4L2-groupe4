package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.CreateLobbyRequest;
import com.esgi.pa.api.dtos.responses.CreateLobbyResponse;
import com.esgi.pa.api.dtos.responses.GetlobbiesResponse;
import com.esgi.pa.api.dtos.responses.GetlobbyResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
public class LobbyResource {
    
    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;

    @GetMapping("{id}")
    public ResponseEntity<GetlobbyResponse> getOne(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            LobbyMapper.toGetlobbyResponse(lobbyService.findOne(id)));
    }

    @GetMapping
    public ResponseEntity<GetlobbiesResponse> getAll() {
        return ResponseEntity.ok(
            new GetlobbiesResponse(
                LobbyMapper.toGetlobbyResponse(
                    lobbyService.findAll())));
    }

    @PostMapping
    public ResponseEntity<CreateLobbyResponse> create(@RequestBody CreateLobbyRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            LobbyMapper.toCreateLobbyResponse(
                lobbyService.create(
                    request.name(),
                    userService.getById(request.user()),
                    gameService.getById(request.game()),
                    request.isPrivate())));
    }
}
