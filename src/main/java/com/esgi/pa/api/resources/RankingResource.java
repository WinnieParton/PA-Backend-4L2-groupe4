package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.ranking.UpdateRankingsRequest;
import com.esgi.pa.api.dtos.responses.ranking.GlobalRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.NoGameRankingResponse;
import com.esgi.pa.api.dtos.responses.ranking.UserRankingsResponse;
import com.esgi.pa.api.mappers.RankingMapper;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.RankingService;
import com.esgi.pa.domain.services.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.OK;

/**
 * Contient les routes concernant les rankings
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/ranking")
@Api(tags = "Ranking API")
public class RankingResource {

    private final RankingService rankingService;
    private final GameService gameService;
    private final UserService userService;
    private final LobbyService lobbyService;

    /**
     * Récupère le ranking de tous les joueurs sur un jeu en particulier
     *
     * @param id id numérique du jeu
     * @return la liste des rankings sur ce jeu
     * @throws TechnicalNotFoundException si le jeu n'est pas trouvé
     */
    @GetMapping("game/{id}")
    @ResponseStatus(OK)
    public GlobalRankingResponse getGlobalRanking(@PathVariable Long id) throws TechnicalNotFoundException {
        return RankingMapper.toGlobalRankingResponse(
            rankingService.getGameGlobalRanking(
                gameService.getById(id)));
    }

    /**
     * Permet de récupérer la liste des rankings d'un joueur sur tous ses jeux
     *
     * @param id id numérique du joueur
     * @return la liste des rankings
     * @throws TechnicalNotFoundException si le joueur n'est pas trouvé
     */
    @GetMapping("user/{id}")
    @ResponseStatus(OK)
    public UserRankingsResponse getUserRankings(@PathVariable Long id) throws TechnicalNotFoundException {
        return RankingMapper.toUserRankingsResponse(
            rankingService.getAllUserRankings(userService.getById(id)));
    }

    /**
     * Permet de récupérer le ranking d'un joueur sur un jeu spécifique
     *
     * @param idUser id numérique du joueur
     * @param idGame id numérique du jeu
     * @return le ranking
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     */
    @GetMapping("user/{idUser}/game/{idGame}")
    public NoGameRankingResponse getUserRankingOnGame(@PathVariable("idUser") Long idUser, @PathVariable("idGame") Long idGame) throws TechnicalNotFoundException {
        return RankingMapper.toNoGameRankingRespsonse(
            rankingService.getRankingForUserOnGame(
                userService.getById(idUser),
                gameService.getById(idGame)));
    }

    /**
     * Permet de mettre à jour le ranking des joueurs
     *
     * @param idLobby               id numérique du lobby
     * @param updateRankingsRequest id du joueur gagnant et les scores par joueurs
     * @return Le nouveau ranking du gagnant
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     * @throws JsonProcessingException    si il y a un problème lors du traitement des scores
     */
    @PostMapping("/lobby/{idLobby}/endgame")
    public List<NoGameRankingResponse> updateParticipantsRanking(@PathVariable Long idLobby, @RequestBody UpdateRankingsRequest updateRankingsRequest) throws TechnicalNotFoundException, JsonProcessingException {
        return RankingMapper.toNoGameRankingRespsonse(
            rankingService.updateRankings(
                lobbyService.getById(idLobby),
                userService.getById(
                    updateRankingsRequest.winnerId()),
                rankingService.calculateScoresByPlayers(updateRankingsRequest.scoresByPlayers())));
    }
}
