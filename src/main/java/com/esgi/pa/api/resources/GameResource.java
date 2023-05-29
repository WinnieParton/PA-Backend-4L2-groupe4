package com.esgi.pa.api.resources;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.GameDto;
import com.esgi.pa.api.dtos.requests.AddGameRequest;
import com.esgi.pa.api.dtos.responses.GetGamesResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.TechnicalException;
import com.esgi.pa.domain.services.GameService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {
    private final GameService gameService;

    @PostMapping()
    public ResponseEntity<?> saveGame(@RequestBody @Valid AddGameRequest request, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<String> errorMessages = bindingResult.getAllErrors()
                    .stream()
                    .map(ObjectError::getDefaultMessage)
                    .collect(Collectors.toList());
            return ResponseEntity.badRequest().body(errorMessages);
        }
        try {
            Game game = gameService.createGame(new Game(request.name(), request.description(), request.gameFiles(),
                    request.miniature(), request.minPlayers(), request.maxPlayers()));

            GameDto response = new GameDto(game.getId(), game.getName(),
                    game.getDescription(), game.getGameFiles(), game.getMiniature(), game.getMinPlayers(),
                    game.getMaxPlayers());

            return ResponseEntity.ok(response);
        } catch (TechnicalException e) {
            return ResponseEntity.status(e.getStatus()).body(e.getMap());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }

    @GetMapping()
    public ResponseEntity<?> getGames() throws TechnicalException {
        try {
            return ResponseEntity.ok(
                    new GetGamesResponse(
                            GameMapper.toDto(
                                    gameService.findAll())));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN)
                    .body("An error occurred while processing the request.");
        }
    }
}
