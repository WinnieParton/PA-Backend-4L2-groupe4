package com.esgi.pa.api.dtos.responses.invitation;

import com.esgi.pa.api.dtos.responses.game.GameDto;
import com.esgi.pa.api.dtos.responses.user.NoFriendsUserResponse;

public record LobbyInvitationResponse(Long id,
                                      String name,
                                      NoFriendsUserResponse creator,
                                      GameDto game) {
}
