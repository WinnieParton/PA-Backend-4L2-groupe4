package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.responses.FriendRequestsReceivedResponse;
import com.esgi.pa.domain.entities.Friend;

import java.util.List;

public interface FriendRequestsReceivedResponseMapper {

    static FriendRequestsReceivedResponse toDto(Friend entity) {
        return new FriendRequestsReceivedResponse(
            entity.getId(),
            UserMapper.toDto(entity.getUser1()),
            entity.getUser2().getId(),
            entity.getStatus());
    }

    static List<FriendRequestsReceivedResponse> toDto(List<Friend> entities) {
        return entities.stream()
            .map(FriendRequestsReceivedResponseMapper::toDto)
            .toList();
    }
}
