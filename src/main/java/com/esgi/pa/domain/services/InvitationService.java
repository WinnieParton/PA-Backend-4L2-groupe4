package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Invitation;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatus;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.InvitationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationAdapter invitationAdapter;
    private final LobbyService lobbyService;

    public Invitation getById(Long id) throws TechnicalNotFoundException {
        return invitationAdapter.findById(id)
                .orElseThrow(() -> new TechnicalNotFoundException(String.format("Could not find invitation with id : %s", id)));
    }

    public Invitation inviteFriendToLobby(User receiver, Lobby lobby) throws TechnicalFoundException {
        if (!receiver.equals(lobby.getCreator())) {
            return invitationAdapter.save(Invitation.builder()
                    .user(receiver)
                    .lobby(lobby)
                    .build());
        } else
            throw new TechnicalFoundException(String.format("Cannot invite the creator %s to it's own lobby.", receiver.getId()));
    }

    public Invitation handleResponse(Invitation invitation, RequestStatus requestStatus) throws TechnicalNotFoundException {
        return switch (requestStatus) {
            case ACCEPTED -> acceptInvitation(invitation.getUser(), invitation.getLobby());
            case REJECTED -> rejectInvitation(invitation.getUser(), invitation.getLobby());
            default ->
                    throw new TechnicalNotFoundException(String.format("Unexpected error with invitation request status : %s", requestStatus));
        };
    }

    private Invitation rejectInvitation(User user, Lobby lobby) throws TechnicalNotFoundException {
        Invitation invitation = invitationAdapter.getInvitationByUserAndLobby(user, lobby)
                .orElseThrow(() -> new TechnicalNotFoundException(String.format("Cannot find invitation for user id : %s and lobby id : %s", user.getId(), lobby.getId())));
        return invitationAdapter.save(invitation.withAccepted(RequestStatus.REJECTED));
    }

    private Invitation acceptInvitation(User user, Lobby lobby) throws TechnicalNotFoundException {
        Invitation invitation = invitationAdapter.getInvitationByUserAndLobby(user, lobby)
                .orElseThrow(() -> new TechnicalNotFoundException(String.format("Cannot find invitation for user id : %s and lobby id : %s", user.getId(), lobby.getId())));
        lobbyService.addUserToLobby(user, lobby);
        return invitationAdapter.save(invitation.withAccepted(RequestStatus.ACCEPTED));
    }

    public List<Invitation> getAllByReceiver(User user) {
        return invitationAdapter.findAllByUser(user)
                .stream()
                .filter(invitation -> RequestStatus.PENDING.equals(invitation.getAccepted()))
                .toList();
    }
}
