package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.requests.invitation.AnswerInvitationRequest;
import com.esgi.pa.api.dtos.requests.invitation.InviteFriendToLobbyRequest;
import com.esgi.pa.api.dtos.responses.invitation.AllUserInvitationsResponse;
import com.esgi.pa.api.dtos.responses.invitation.InvitationDto;
import com.esgi.pa.api.mappers.InvitationMapper;
import com.esgi.pa.domain.exceptions.TechnicalFoundException;
import com.esgi.pa.domain.exceptions.TechnicalNotFoundException;
import com.esgi.pa.domain.services.InvitationService;
import com.esgi.pa.domain.services.LobbyService;
import com.esgi.pa.domain.services.UserService;
import io.swagger.annotations.Api;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.OK;

/**
 * Contient les routes concernant les invitations aux lobbies
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
@Api(tags = "Invitation API")
public class InvitationResource {

    private final InvitationService invitationService;
    private final UserService userService;
    private final LobbyService lobbyService;

    /**
     * Invite un ami à un lobby
     *
     * @param id                         id du lobby
     * @param inviteFriendToLobbyRequest contient l'id de l'ami invité
     * @return InvitationDto - la réponse immédiate de la création de l'invitation
     * @throws TechnicalNotFoundException si un id n'est pas trouvé
     * @throws TechnicalFoundException    si l'invité est le créateur du lobby
     */
    @PostMapping("lobby/{id}")
    @ResponseStatus(OK)
    public InvitationDto inviteFriendToLobby(@PathVariable Long id, @RequestBody InviteFriendToLobbyRequest inviteFriendToLobbyRequest) throws TechnicalNotFoundException, TechnicalFoundException {
        return InvitationMapper.toDto(
            invitationService.inviteFriendToLobby(
                userService.getById(inviteFriendToLobbyRequest.friendId()),
                lobbyService.getById(id)
            )
        );
    }

    /**
     * Réponds à une invitation à un lobby
     * @param id id de l'invitation
     * @param answerInvitationRequest body de la requête contient l'id user, l'id lobby et la réponse à l'invitation
     * @return contient le retour de la réponse à l'invitation
     * @throws TechnicalNotFoundException si un des ids contenu n'existe pas
     */
    @PatchMapping("{id}")
    @ResponseStatus(OK)
    public InvitationDto answerInvitation(@PathVariable Long id, @RequestBody AnswerInvitationRequest answerInvitationRequest) throws TechnicalNotFoundException {
        return InvitationMapper.toDto(
            invitationService.handleResponse(
                invitationService.getById(id),
                answerInvitationRequest.response()));
    }

    /**
     * Récupère l'ensemble des invitations d'un utilisateur qui n'ont pas eu de réponses
     * @param userId id de l'utilisateur
     * @return List de l'ensemble invitations qu'il a reçu
     * @throws TechnicalNotFoundException si l'utilisateur n'éxiste pas
     */
    @GetMapping("{userId}")
    @ResponseStatus(OK)
    public AllUserInvitationsResponse getAllInvitations(@PathVariable Long userId) throws TechnicalNotFoundException {
        return InvitationMapper.toAllUserInvitationsResponse(
            invitationService.getAllByReceiver(
                userService.getById(userId)));
    }
}
