package com.esgi.pa.api.resources;

import com.esgi.pa.api.dtos.InvitationDto;
import com.esgi.pa.api.dtos.requests.AnswerInvitationRequest;
import com.esgi.pa.api.dtos.requests.InviteFriendToLobbyRequest;
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

@RestController
@RequiredArgsConstructor
@RequestMapping("/invitation")
@Api(tags = "Invitation API")
public class InvitationResource {

    private final InvitationService invitationService;
    private final UserService userService;
    private final LobbyService lobbyService;

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

    @PostMapping("{id}")
    @ResponseStatus(OK)
    public InvitationDto answerInvitation(@PathVariable Long id, @RequestBody AnswerInvitationRequest answerInvitationRequest) throws TechnicalNotFoundException {
        return InvitationMapper.toDto(
                invitationService.handleResponse(
                        invitationService.getById(id),
                        answerInvitationRequest.response()));
    }

}
