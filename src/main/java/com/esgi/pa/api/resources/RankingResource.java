package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.UpdateRankingsRequest;
import com.esgi.pa.api.dtos.responses.ranking.GlobalRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.NoGameRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.UserRankingsResponse;
import com.esgi.pa.api.mappers.RankingMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.RankingService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = "Ranking API")
public class RankingResource {

    private final RankingService rankingService;
    private final GameService gameService;
    private final UserService userService;
    private final LobbyService lobbyService;

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

    @PostMapping("lobby/{idLobby}/endgame")
    public List<NoGameRankingResponse> updateParticipantsRanking(@PathVariable Long idLobby, @RequestBody UpdateRankingsRequest updateRankingsRequest) throws TechnicalNotFoundException {
        return RankingMapper.toNoGameRankingRespsonse(
            rankingService.updateRankings(
                lobbyService.getById(idLobby),
                userService.getById(updateRankingsRequest.winnerId()),
                updateRankingsRequest.scoresByPlayers()));
    }
}
