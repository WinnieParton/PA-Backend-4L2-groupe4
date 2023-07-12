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

@Service
@RequiredArgsConstructor
public class LobbyService {

    private final LobbyAdapter lobbyAdapter;
    private final UserService userService;
    private final GameService gameService;
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

    public Lobby getById(Long id) throws TechnicalNotFoundException {
        return lobbyAdapter.findById(id)
                .orElseThrow(() -> new TechnicalNotFoundException(HttpStatus.NOT_FOUND, "No Lobby found with id : " + id));
    }

    public List<Lobby> findAll() {
        return lobbyAdapter.findAll();
    }

    public Lobby save(Lobby lobby) {
        return lobbyAdapter.save(lobby);
    }

    public Lobby pauseGame(Lobby lobby) {
        return save(lobby.withStatus(GameStatusEnum.PAUSED));
    }

    public Lobby addUserToLobby(User user, Lobby lobby) {
        user.getParticipatingLobbies().add(lobby);
        lobby.getParticipants().add(user);
        userService.save(user);
        return save(lobby);
    }
}