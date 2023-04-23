package com.esgi.pa.api.mappers;

import java.util.List;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.domain.entities.User;

public interface UserMapper {

    static UserDto toDto(User user) {
        return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .password(user.getPassword())
        .role(user.getRole())
        .friends(UserMapper.toDto(user.getFriends())) // TODO impl friends mapper
        .build();
    }

    static List<UserDto> toDto(List<User> entities) {
        return entities.stream()
            .map(entity -> toDto(entity))
            .toList();
    }

    static User toDomain(UserDto dto) {
        return User.builder()
        .id(dto.id())
        .name(dto.name())
        .email(dto.email())
        .password(dto.password())
        .role(dto.role())
        .friends(null) // TODO impl friends mapper
        .build();
    }
    
}
