package com.esgi.pa.api.mappers;

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
    
}
