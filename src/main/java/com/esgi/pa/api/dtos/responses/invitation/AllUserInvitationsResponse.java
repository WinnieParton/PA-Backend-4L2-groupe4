package com.esgi.pa.api.dtos.responses.invitation;

import java.util.List;

/**
 * DTO de réponse à une requête de récupération des invitations
 *
 * @param invitations liste des invitations
 */
public record AllUserInvitationsResponse(List<InvitationDto> invitations) {
}
