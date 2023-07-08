package com.esgi.pa.api.resources;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;

import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/engine")
@RequiredArgsConstructor
@Api(tags = "Engine Template API")
public class EngineRessource {

  private final LobbyService lobbyService;
  private final GameService gameService;

  @PostMapping(
    value = "/{idlobby}",
    consumes = MediaType.APPLICATION_JSON_VALUE,
    produces = MediaType.APPLICATION_JSON_VALUE
  )
  public ResponseEntity<String> runEngine(
    @PathVariable Long idlobby,
    @RequestBody String jsonData
  ) throws TechnicalNotFoundException {
    Lobby lobby = lobbyService.getById(idlobby);
    // Construire les en-têtes de la réponse avec le type de contenu JSON
    HttpHeaders headers = new HttpHeaders();
    headers.setContentType(MediaType.APPLICATION_JSON);
    String output = gameService.runEngine(lobby, jsonData);
    // Retourner la réponse JSON renvoyée par le engine Python avec les en-têtes appropriés
    return new ResponseEntity<>(output, headers, HttpStatus.OK);
  }
}
