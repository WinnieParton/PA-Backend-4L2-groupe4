package com.esgi.pa.api.dtos.responses;

import com.esgi.pa.api.dtos.GameDto;

public record LobbyInvitationResponse(Long id,
                                      String name,
                                      NoFriendsUserResponse creator,
                                      GameDto game) {
}
