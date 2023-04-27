package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.FriendDto;
import com.esgi.pa.domain.entities.Friend;

import java.util.List;

public interface FriendMapper {

    static FriendDto toDto(Friend entity) {
        return new FriendDto(
            entity.getId(),
            entity.getUser1().getId(),
            NoFriendsUserMapper.toDto(entity.getUser2()),
            entity.getStatus());
    }

    static List<FriendDto> toDto(List<Friend> entities) {
        return entities.stream()
            .map(FriendMapper::toDto)
            .toList();
    }
    
}
