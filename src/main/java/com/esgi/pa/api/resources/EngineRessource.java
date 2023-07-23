package com.esgi.pa.api.resources;

import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.GameService;
import com.esgi.pa.domain.services.LobbyService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.IOException;

/**
 * Contient les routes permettant la communication avec les moteurs de jeux
 */
@RestController
@RequestMapping("/engine")
@RequiredArgsConstructor
@Api(tags = "Engine Template API")
public class EngineRessource {

    private final LobbyService lobbyService;
    private final GameService gameService;
    private final SimpMessagingTemplate simpMessagingTemplate;

    /**
     * Traite les requêtes de lancement d'un jeu
     *
     * @param idlobby  id numérique du lobby
     * @param jsonData informations sur l'état du lobby à transmettre au moteur de jeu
     * @return ResponseEntity contenant l'état du jeu après le processing du moteur
     * @throws TechnicalNotFoundException si un élément n'est pas trouvé
     * @throws IOException                si erreur dans l'execution du moteur
     */
    @PostMapping(value = "/{idlobby}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> runEngine(@PathVariable Long idlobby, @Valid @RequestBody String jsonData) throws TechnicalNotFoundException, IOException {
        Lobby lobby = lobbyService.getById(idlobby);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String output = gameService.runEngine(lobby, jsonData);
        lobby.getParticipants()
            .forEach(participant -> {
                simpMessagingTemplate.convertAndSendToUser(
                    participant.getName(), "/game", output);
            });
        return new ResponseEntity<>(output, headers, HttpStatus.OK);
    }
}
