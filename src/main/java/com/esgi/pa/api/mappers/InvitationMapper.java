package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.AllUserInvitationsResponse;
import com.esgi.pa.api.dtos.responses.invitation.InvitationDto;
import com.esgi.pa.domain.entities.Invitation;

import java.util.List;

public interface InvitationMapper {

    static InvitationDto toDto(Invitation invitation) {
        return new InvitationDto(
                invitation.getId(),
                LobbyMapper.toLobbyInvitationResponse(invitation.getLobby()),
                invitation.getAccepted());
    }

    static AllUserInvitationsResponse toAllUserInvitationsResponse(List<Invitation> invitationList) {
        return new AllUserInvitationsResponse(
                invitationList.stream()
                        .map(InvitationMapper::toDto)
                        .toList());
    }
}
