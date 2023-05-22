package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.CreateLobbyRequest;
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
    public ResponseEntity<Object> getOne(@PathVariable UUID id) throws TechnicalException {
        return ResponseEntity.ok(
            LobbyMapper.toDto(lobbyService.findOne(id)));
    }

    @GetMapping
    public ResponseEntity<Object> getAll() {
        return ResponseEntity.ok(
            LobbyMapper.toDto(
                lobbyService.findAll()));
    }

    @PostMapping
    public ResponseEntity<Object> create(@RequestBody CreateLobbyRequest request) throws TechnicalException {
        return ResponseEntity.ok(
            LobbyMapper.toDto(
                lobbyService.create(
                    request.name(),
                    userService.getById(request.user()),
                    gameService.getById(request.game()),
                    request.isPrivate())));
    }
}
