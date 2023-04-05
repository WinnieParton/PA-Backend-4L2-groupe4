package com.esgi.pa.api.mappers;

import com.esgi.pa.api.dtos.UserDto;
import com.esgi.pa.domain.entities.User;

public interface UserMapper {

    static UserDto toDto(User user) {
        return UserDto.builder()
        .id(user.getId())
        .name(user.getName())
        .email(user.getEmail())
        .role(user.getRole())
        .friends(null) // TODO impl friends mapper
        .build();
    }

    static User toDomain(UserDto dto) { // PASSWORD NEEDED ?
        return User.builder()
        .id(dto.id())
        .name(dto.name())
        .email(dto.email())
        .role(dto.role())
        .friends(null) // TODO impl friends mapper
        .build();
    }
    
}
