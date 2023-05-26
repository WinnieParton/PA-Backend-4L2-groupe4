package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.domain.entities.User;

import java.util.List;

public interface UserMapper {

    static UserDto toDto(User user) {
        return new UserDto(
            user.getId(),
            user.getName(),
            user.getEmail(),
            user.getRole(),
            user.getFriends().stream().map(NoFriendsUserMapper::toDto).toList());
    }

    static List<UserDto> toDto(List<User> entities) {
        return entities.stream()
            .map(UserMapper::toDto)
            .toList();
    }
}
