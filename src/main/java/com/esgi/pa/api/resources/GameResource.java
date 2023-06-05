package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.CREATED;

import javax.validation.Valid;

import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.api.dtos.GameDto;
import com.esgi.pa.api.dtos.requests.AddGameRequest;
import com.esgi.pa.api.dtos.responses.GetGamesResponse;
import com.esgi.pa.api.mappers.GameMapper;
import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.exceptions.MethodArgumentNotValidException;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.ErrorFormatService;
import com.esgi.pa.domain.services.GameService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/game")
@Api(tags = "Game API")
public class GameResource {
    private final GameService gameService;
    private final ErrorFormatService errorFormatService;

    @PostMapping()
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
}
