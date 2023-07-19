package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Game;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.GameStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.LobbyAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service de gestion des lobbies
 */
@Service
@RequiredArgsConstructor
public class LobbyService {

    private final LobbyAdapter lobbyAdapter;
    private final UserService userService;

    /**
     * Créé un lobby
     *
     * @param name             nom du lobby
     * @param user             créateur du lobby
     * @param game             jeu joué dans le lobby
     * @param isInvitationOnly visibilité du lobby
     * @return le lobby nouvellement créé
     */
    public Lobby create(String name, User user, Game game, boolean isInvitationOnly) {
        return lobbyAdapter.save(
            Lobby.builder()
                .name(name)
                .creator(user)
                .game(game)
                .invitationOnly(isInvitationOnly)
                .participants(List.of(user))
                .status(GameStatusEnum.PENDING)
                .build());
    }

    /**
     * Récupère un lobby par son id
     *
     * @param id id numérique du lobby
     * @return le lobby recherché
     * @throws TechnicalNotFoundException si le lobby n'est pas trouvé
     */
    public Lobby getById(Long id) throws TechnicalNotFoundException {
        return lobbyAdapter.findById(id)
            .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No Lobby found with id : " + id));
    }

    /**
     * Récupère la liste des lobbies disponible
     *
     * @return liste des lobbies
     */
    public List<Lobby> findAll() {
        return lobbyAdapter.findAll();
    }

    /**
     * Persiste le lobby
     *
     * @param lobby lobby à sauvegarder
     * @return le lobby sauvegardé
     */
    public Lobby save(Lobby lobby) {
        return lobbyAdapter.save(lobby);
    }

    /**
     * Persiste le lobby avec un l'état "pause"
     *
     * @param lobby le lobby à pauser
     * @return le nouvel état du lobby
     */
    public Lobby pauseGame(Lobby lobby) {
        return save(lobby.withStatus(GameStatusEnum.PAUSED));
    }

    /**
     * Ajoute un utilisateur à un lobby
     *
     * @param user  utilisateur à ajouter
     * @param lobby lobby auquel l'utilisateur doit être ajouté
     * @return le nouvel état du lobby
     */
    public Lobby addUserToLobby(User user, Lobby lobby) {
        user.getParticipatingLobbies().add(lobby);
        lobby.getParticipants().add(user);
        userService.save(user);
        return save(lobby);
    }
}