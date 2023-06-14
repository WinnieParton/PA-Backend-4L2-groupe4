package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.InvitationDto;
import com.esgi.pa.domain.entities.Invitation;
import com.esgi.pa.domain.enums.RequestStatus;

public interface InvitationMapper {

    static InvitationDto toDto(Invitation invitation) {
        return new InvitationDto(
                invitation.getId(),
                LobbyMapper.toLobbyInvitationResponse(invitation.getLobby()),
                invitation.getAccepted().equals(RequestStatus.ACCEPTED));
    }
}
