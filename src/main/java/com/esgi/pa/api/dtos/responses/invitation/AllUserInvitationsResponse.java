package com.esgi.pa.api.dtos.responses.invitation;

import com.esgi.pa.api.dtos.responses.invitation.InvitationDto;

import java.util.List;

public record AllUserInvitationsResponse(List<InvitationDto> invitations) {
}
