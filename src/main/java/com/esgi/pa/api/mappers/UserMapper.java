package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.domain.entities.User;

import java.util.List;

public interface UserMapper {

    static UserDto toDto(User user) {
        return UserDto.builder()
            .id(user.getId())
            .name(user.getName())
            .email(user.getEmail())
            .role(user.getRole())
            .build();
    }

    static List<UserDto> toDto(List<User> entities) {
        return entities.stream()
            .map(UserMapper::toDto)
            .toList();
    }
}
