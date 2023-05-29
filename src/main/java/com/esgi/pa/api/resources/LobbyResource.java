package com.esgi.pa.api.resources;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.requests.CreateLobbyRequest;
import com.esgi.pa.api.dtos.responses.GetlobbiesResponse;
import com.esgi.pa.api.mappers.LobbyMapper;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/lobby")
@Api(tags = "Lobby API")
public class LobbyResource {

    private final LobbyService lobbyService;
    private final UserService userService;
    private final GameService gameService;

    @GetMapping("{id}")
    public ResponseEntity<?> getOne(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(
                    LobbyMapper.toGetlobbyResponse(lobbyService.findOne(id)));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @GetMapping
    public ResponseEntity<GetlobbiesResponse> getAll() {
        return ResponseEntity.ok(
                new GetlobbiesResponse(
                        LobbyMapper.toGetlobbyResponse(
                                lobbyService.findAll())));
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody CreateLobbyRequest request) {
        try {
            return ResponseEntity.ok(
                    LobbyMapper.toCreateLobbyResponse(
                            lobbyService.create(
                                    request.name(),
                                    userService.getById(request.user()),
                                    gameService.getById(request.game()),
                                    request.isPrivate())));
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }
}
