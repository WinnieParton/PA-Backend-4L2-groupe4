package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.NoFriendsUserDto;
import com.esgi.pa.domain.entities.User;

public interface NoFriendsUserMapper {

    static NoFriendsUserDto toDto(User user) {
        return new NoFriendsUserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole());
    }
}
