package com.esgi.pa.domain.services;

import com.esgi.pa.domain.entities.Invitation;
import com.esgi.pa.domain.entities.Lobby;
import com.esgi.pa.domain.entities.User;
import com.esgi.pa.domain.enums.RequestStatusEnum;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.server.adapter.InvitationAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service de gestion des invitations aux lobbies
 */
@Service
@RequiredArgsConstructor
public class InvitationService {

    private final InvitationAdapter invitationAdapter;
    private final LobbyService lobbyService;

    /**
     * Récupère une invitation par son id
     *
     * @param id id numérique de l'invitation
     * @return les informations relatives
     * @throws TechnicalNotFoundException si l'invitation n'est pas trouvé
     */
    public Invitation getById(Long id) throws TechnicalNotFoundException {
        return invitationAdapter.findById(id)
            .orElseThrow(() -> new TechnicalNotFoundException(String.format("Could not find invitation with id : %s", id)));
    }

    /**
     * Invite un ami à un lobby
     *
     * @param receiver utilisateur receveur
     * @param lobby    lobby où l'utilisateur est envoyé
     * @return l'invitation nouvellement créer
     * @throws TechnicalFoundException si l'invitation existe déjà
     */
    public Invitation inviteFriendToLobby(User receiver, Lobby lobby) throws TechnicalFoundException {
        if (!receiver.equals(lobby.getCreator())) {
            Optional<Invitation> invitation = invitationAdapter.getInvitationByUserAndLobby(receiver, lobby);
            return invitation.orElseGet(() -> invitationAdapter.save(Invitation.builder()
                .user(receiver)
                .lobby(lobby)
                .build()));
        } else
            throw new TechnicalFoundException(String.format("Cannot invite the creator %s to it's own lobby.", receiver.getId()));
    }

    /**
     * Dispatch la réponse vers la méthode adapté
     *
     * @param invitation    l'invitation à traité
     * @param requestStatus statut de la réponse
     * @return le nouvel état de l'invitation
     * @throws TechnicalNotFoundException si l'invitation n'est pas trouvé
     */
    public Invitation handleResponse(Invitation invitation, RequestStatusEnum requestStatus) throws TechnicalNotFoundException {
        return switch (requestStatus) {
            case ACCEPTED -> acceptInvitation(invitation.getUser(), invitation.getLobby());
            case REJECTED -> rejectInvitation(invitation.getUser(), invitation.getLobby());
            default ->
                throw new TechnicalNotFoundException(String.format("Unexpected error with invitation request status : %s", requestStatus));
        };
    }

    /**
     * Refuse l'invitation reçu
     *
     * @param user  utilisateur receveur
     * @param lobby lobby auquel l'utilisateur est invité
     * @return le nouvel état de l'invitation
     * @throws TechnicalNotFoundException si l'invitation n'est pas trouvé
     */
    private Invitation rejectInvitation(User user, Lobby lobby) throws TechnicalNotFoundException {
        Invitation invitation = invitationAdapter.getInvitationByUserAndLobby(user, lobby)
            .orElseThrow(() -> new TechnicalNotFoundException(String.format("Cannot find invitation for user id : %s and lobby id : %s", user.getId(), lobby.getId())));
        return invitationAdapter.save(invitation.withAccepted(RequestStatusEnum.REJECTED));
    }

    /**
     * Accepte l'invitation au lobby
     *
     * @param user  utilisateur invité
     * @param lobby lobby auquel l'utilisateur est invité
     * @return le nouvel état de l'invitation
     * @throws TechnicalNotFoundException si l'invitation n'est pas trouvé
     */
    private Invitation acceptInvitation(User user, Lobby lobby) throws TechnicalNotFoundException {
        Invitation invitation = invitationAdapter.getInvitationByUserAndLobby(user, lobby)
            .orElseThrow(() -> new TechnicalNotFoundException(String.format("Cannot find invitation for user id : %s and lobby id : %s", user.getId(), lobby.getId())));
        lobbyService.addUserToLobby(user, lobby);
        return invitationAdapter.save(invitation.withAccepted(RequestStatusEnum.ACCEPTED));
    }

    /**
     * Récupère toutes les invitations qu'un utilisateur à reçu
     *
     * @param user utilisateur receveur
     * @return la liste des invitations
     */
    public List<Invitation> getAllByReceiver(User user) {
        return invitationAdapter.findAllByUser(user)
            .stream()
            .filter(invitation -> RequestStatusEnum.PENDING.equals(invitation.getAccepted()))
            .distinct()
            .toList();
    }
}
