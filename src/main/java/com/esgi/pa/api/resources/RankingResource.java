package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.responses.GlobalRankingResponse;
import com.esgi.pa.api.dtos.responses.NoGameRankingResponse;
import com.esgi.pa.api.dtos.responses.UserRankingsResponse;
import com.esgi.pa.api.mappers.RankingMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.RankingService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = "Ranking API")
public class RankingResource {

    private final RankingService rankingService;
    private final GameService gameService;
    private final UserService userService;

    @GetMapping("game/{id}")
    @ResponseStatus(OK)
    public GlobalRankingResponse getGlobalRanking(@PathVariable Long id) throws TechnicalNotFoundException {
        return RankingMapper.toGlobalRankingResponse(
                rankingService.getGameGlobalRanking(
                        gameService.getById(id)));
    }

    @GetMapping("user/{id}")
    @ResponseStatus(OK)
    public UserRankingsResponse getUserRankings(@PathVariable Long id) throws TechnicalNotFoundException {
        return RankingMapper.toUserRankingsResponse(
                rankingService.getAllUserRankings(userService.getById(id)));
    }

    @GetMapping("user/{idUser}/game/{idGame}")
    public NoGameRankingResponse getUserRankingOnGame(@PathVariable("idUser") Long idUser, @PathVariable("idGame") Long idGame) throws TechnicalNotFoundException {
        return RankingMapper.toNoGameRankingRespsonse(
                rankingService.getRankingForUserOnGame(
                        userService.getById(idUser),
                        gameService.getById(idGame)));
    }
}
