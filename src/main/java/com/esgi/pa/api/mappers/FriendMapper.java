package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.FriendDto;
import com.esgi.pa.domain.entities.Friend;

public interface FriendMapper {

    static FriendDto toDto(Friend entity) {
        return new FriendDto(
            entity.getId(),
            UserMapper.toDto(entity.getUser()),
            UserMapper.toDto(entity.getFriend()),
            entity.getStatus());
    }

    static List<FriendDto> toDto(List<Friend> entities) {
        return entities.stream()
            .map(entity -> toDto(entity))
            .toList();
    }
    
}
