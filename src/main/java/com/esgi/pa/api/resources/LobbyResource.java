package com.esgi.pa.api.resources;

import java.util.UUID;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.CreateLobbyRequest;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.domain.exceptions.FunctionalException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
public class LobbyResource {
    
    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;

    @GetMapping("{id}")
    public ResponseEntity<Object> getOne(@PathVariable UUID id) throws FunctionalException {
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
    public ResponseEntity<Object> create(@RequestBody CreateLobbyRequest request) throws FunctionalException {
        return ResponseEntity.ok(
            LobbyMapper.toDto(
                lobbyService.create(
                    request.name(), 
                    userService.getById(request.userId()), 
                    gameService.getById(request.gameId()), 
                    request.isPrivate())));
    }
}
