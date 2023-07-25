package com.esgi.pa.api.resources;

import static org.springframework.http.HttpStatus.OK;

import com.esgi.pa.api.dtos.responses.move.GetmovesResponse;
import com.esgi.pa.api.mappers.MoveMapper;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.MoveService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

/**
 * Contient les routes lié aux moves
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/move")
@Api(tags = "Move API")
public class MoveResource {

  private final LobbyService lobbyService;
  private final MoveService moveService;

  /**
   * Récupère history
   *
   * @param id id numérique d'un lobby
   * @return informations relative au history
   * @throws TechnicalNotFoundException si le lobby n'est pas trouvé
   */
  @GetMapping("{id}")
  @ResponseStatus(OK)
  public GetmovesResponse getHistroy(@PathVariable Long id)
    throws TechnicalNotFoundException {
    Lobby lobby = lobbyService.getById(id);
    return new GetmovesResponse(
      MoveMapper.toHistoryMovesForOneLobby(
        moveService.findListLastMoveInPut(lobby)
      )
    );
  }
}
